package pl.luxdev.mbot.threads;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

import pl.luxdev.mbot.managers.Config;
import pl.luxdev.mbot.managers.ConfigManager;

public class DynamicBannerThread extends Thread{
	
	private TS3Query query;
	private TS3Api api;
	private final TS3Config config;
	
	public DynamicBannerThread() {
		super("DynamicBannerThread");
		this.config = new TS3Config();
	}
	
	@Override
	public void run(){
		try{
			while(true){
				config.setHost("localhost");
				config.setDebugLevel(Level.OFF);
				config.setQueryPort(10011);
				config.setReconnectStrategy(ReconnectStrategy.constantBackoff());
				this.query = new TS3Query(this.config);
				query.connect();
				this.api = this.query.getApi();
				api.selectVirtualServerById(1);
				api.login("serveradmin", "");
				api.setNickname("FireBOT (DynamicBanner)");
				final BufferedImage image = ImageIO.read(new URL("http://5.196.100.120/banner/banner.png"));
				Graphics g = image.getGraphics();
				g.setFont(g.getFont().deriveFont(38f));
				g.setColor(Color.CYAN);
				g.drawString("Online: " + this.api.getClients().size(), 80, 260);
				g.dispose();
				ImageIO.write(image, "png", new File("/var/www/html/banner/dbanner.png"));
				query.exit();
				Thread.sleep(60000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}	
