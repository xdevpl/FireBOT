package pl.luxdev.mbot.basic;

import java.sql.ResultSet;

import pl.luxdev.mbot.basic.util.DatabaseClientUtil;
import pl.luxdev.mbot.mysql.Mysql;

public class DatabasedClient {

	private String nick;
	private String uid;

	private int dbClientId;

	private long timeSpent;
	private long lastDisconnect;
	private long lastConnect;

	private boolean isDisconnected;
	private boolean changes;


	public DatabasedClient(String nick, String uid, long timeSpent, long lastDisconnect, long lastConnect, boolean isDisconnected) {
		this.nick = nick;
		this.uid = uid;
		this.timeSpent = timeSpent;
		this.lastDisconnect = lastDisconnect;
		this.lastConnect = lastConnect;
		this.isDisconnected = isDisconnected;
	}
	public DatabasedClient(int clientDBId){
		this.dbClientId = clientDBId;
		DatabaseClientUtil.addToDatabase(this, clientDBId);
	}
	// -- Tutaj gettery/setery..--

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getTimeSpent() {
		if (this.isDisconnected) {
			return this.lastDisconnect - this.lastConnect;
		}else{
			long timeSpent = this.lastConnect - System.currentTimeMillis();
			this.timeSpent = timeSpent;
			return timeSpent;
		}
	}

	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
		changes();
	}

	public long getLastDisconnect() {
		return lastDisconnect;
	}

	public void setLastDisconnect(long lastDisconnect) {
		this.lastDisconnect = lastDisconnect;
	}

	public long getLastConnect() {
		return lastConnect;
	}
	public void setLastConnect(long lastConnect) {
		this.lastConnect = lastConnect;
	}

	public boolean isDisconnected() {
		return isDisconnected;
	}

	public void setDisconnected(boolean isDisconnected) {
		this.isDisconnected = isDisconnected;
	}

	public int getClientDBId() {
		return this.dbClientId;
	}

	public void setClientDBId(int cDBid) {
		this.dbClientId = cDBid;
	}

	public static DatabasedClient get(int clientDBId) {
		for (DatabasedClient u : DatabaseClientUtil.getDatabasedClientAsList()) {
			if (clientDBId == u.getClientDBId()) {
				return u;
			}
		}
		return new DatabasedClient(clientDBId);
	}

	// ======================================================
	public static void table() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists mbot_users(");
		sb.append("uid varchar(100) not null,");
		sb.append("timeSpent long not null,");
		sb.append("disconnected int not null,");
		sb.append("lastDisconnect long not null,");
		sb.append("lastConnected long not null,");
		sb.append("clientDBId int not null,");
		sb.append("nick varchar(100) not null,");
		sb.append("primary key (clientDBId));");
		Mysql db = Mysql.getInst();
		db.openConnection();
		db.executeUpdate(sb.toString());
		db.closeConnection();
	}

	public static DatabasedClient deserialize(ResultSet rs) {
		if (rs == null)
			return null;
		try {
			DatabasedClient user = DatabasedClient.get(rs.getInt("clientDBId"));
			user.setNick(rs.getString("nick"));
			user.setDisconnected(rs.getInt("disconnected") == 1 ? true : false);
			user.setTimeSpent(rs.getLong("timeSpent"));
			user.setLastDisconnect(rs.getLong("lastDisconnect"));
			user.setLastConnect(rs.getLong("lastConnected"));
			user.setUid(rs.getString("uid"));
			System.out.println("Created User: " + user.getNick() + " disconnected: " + user.isDisconnected + " clientId " + user.getClientDBId());
			return user;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	public void insert(Mysql db) {
		if (this.uid == null)
			return;
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO mbot_users (uid, timeSpent, disconnected, lastDisconnect, lastConnected, clientDBId, nick) VALUES (");
        String nickname = this.nick.replace("'","''");
		sb.append("'" + this.uid + "',");
		sb.append("'" + this.timeSpent + "',");
		sb.append("'" + (this.isDisconnected() ? 1 : 0) + "',");
		sb.append("'" + this.lastDisconnect + "',");
		sb.append("'" + this.lastConnect + "',");
		sb.append("'" + this.dbClientId + "',");
		sb.append("'" + this.nick + "'");
		sb.append(") ON DUPLICATE KEY UPDATE ");
		sb.append("uid='" + this.uid + "',");
		sb.append("timeSpent='" + this.timeSpent + "',");
		sb.append("disconnected='" + (this.isDisconnected ? 1 : 0) + "',");
		sb.append("lastDisconnect='" + lastDisconnect + "',");
		sb.append("lastConnected='" + lastConnect + "',");
		sb.append("clientDBId='" + this.dbClientId + "',");
		sb.append("nick='" + nickname + "'");
		db.executeUpdate(sb.toString());
	}

	// =====================================================
	public boolean changed() {
		boolean c = changes;
		changes = false;
		return c;
	}

	public void changes() {
		changes = true;
	}
	public boolean isOnline(){
		return this.isDisconnected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.uid == null) ? 0 : this.uid.hashCode());
		return result;
	}
}
