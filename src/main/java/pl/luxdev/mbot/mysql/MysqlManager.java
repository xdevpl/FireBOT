package pl.luxdev.mbot.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import pl.luxdev.mbot.basic.DatabasedClient;
import pl.luxdev.mbot.basic.util.DatabaseClientUtil;

public class MysqlManager {

	private static MysqlManager inst;

	public MysqlManager() {
		inst = this;
	}

	public void load() {
		Mysql db = Mysql.getInst();
		if (db.openConnection() == null) {
			System.out.println("Cannot connect to database!");
			return;
		}
		DatabasedClient.table();
		System.out.println("Loading data from Mysql...");
		ResultSet users = Mysql.getInst().executeQuery("SELECT * FROM  mbot_users");
		try {
			while (users.next()) {
				DatabasedClient user = DatabasedClient.deserialize(users);
				if (user != null)
					user.changed();
			}
			System.out.println("Loaded users:" + DatabaseClientUtil.getDatabaseClients().size());
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			db.closeConnection();
		}
	}

	public void save(boolean all) throws ClassNotFoundException, SQLException {
		Mysql db = Mysql.getInst();
		db.openConnection();
		System.out.println("Saving data to mysql..");
		for (DatabasedClient user : DatabaseClientUtil.getDatabasedClientAsList()) {
			if (!all && !user.changed())
				continue;
			try {
				user.insert(db);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		System.out.println("Saved users:" + DatabaseClientUtil.getDatabaseClients().size());
		db.closeConnection();
	}

	public static MysqlManager getInst() {
		if (inst != null)
			return inst;
		return new MysqlManager();
	}

}
