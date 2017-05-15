package pl.luxdev.mbot.threads;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.mysql.MysqlManager;

public class IDTDThread extends Thread{
	
	public IDTDThread(){
		this.setName("IDTD-Thread");
	}
	
	@Override
	public void run(){
		try {
			while(true){
				MysqlManager.getInst().save(true);
				Thread.sleep(60000 * 5);//5 minut
			}
		} catch (InterruptedException | ClassNotFoundException | SQLException e) {
			e.printStackTrace(System.out);
		}
	}

}
