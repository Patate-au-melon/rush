package be.seveningful.sevsrush.util;

import org.bukkit.Location;

import be.seveningful.sevsrush.Main;

public class Lobby {
	
	public static void setLobby(Main main, Location l){
		main.getConfig().set("lobby.X", l.getX());
		main.getConfig().set("lobby.Y", l.getY());
		main.getConfig().set("lobby.Z", l.getZ());
		main.saveConfig();
	}
	
}
