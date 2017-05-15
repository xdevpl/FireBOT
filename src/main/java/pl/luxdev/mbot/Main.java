package pl.luxdev.mbot;

import pl.luxdev.mbot.instances.FirstInstance;
import pl.luxdev.mbot.instances.SecondInstance;
import pl.luxdev.mbot.managers.ConfigManager;
import pl.luxdev.mbot.mysql.MysqlManager;
import pl.luxdev.mbot.threads.DynamicBannerThread;
import pl.luxdev.mbot.threads.IDTDThread;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("==================== [MBOT] v0.2.6 ====================");
			System.out.println("Dozwolone komendy: mbot start all");
			return;
		}
		if(args[1].equalsIgnoreCase("all")){
			System.out.println("==================== [MBOT] v0.2.6 ====================");
			System.out.println("Wykonywanie zadania.");
			System.out.println("Trwa uruchamianie wszystkich instancji.");
			ConfigManager configManager = new ConfigManager();
			MysqlManager.getInst().load();
			FirstInstance bot = new FirstInstance();
			bot.start();
			SecondInstance sinst = new SecondInstance();
			sinst.start();
			new IDTDThread().start();
			new DynamicBannerThread().start();
		}
		System.out.println("Wykonano zadanie, zostan w konsoli aby wyswietlac informacje.");
		System.out.println("==================== [MBOT] v0.2.6 ====================");
	}
}
