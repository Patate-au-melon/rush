package be.seveningful.sevsrush.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {

	public static void hitSound(Player p){
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10, 10);
	}
	
}
