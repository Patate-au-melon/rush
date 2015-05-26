package be.seveningful.sevsrush.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Effects {

	public static void level(int level) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setLevel(level);
		}
	}

	public static void partyeffects(Sound sound, int Level) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), sound, 10.0F, 10.0F);
			p.setLevel(Level);
		}
	}
}
