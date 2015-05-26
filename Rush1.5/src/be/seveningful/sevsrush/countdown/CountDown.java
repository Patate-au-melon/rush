package be.seveningful.sevsrush.countdown;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import be.seveningful.sevsrush.Main;
import be.seveningful.sevsrush.util.Effects;
import be.seveningful.sevsrush.util.Messages;
import be.seveningful.sevsrush.util.Title;



public class CountDown {
	static boolean countdownIsStart;
	@SuppressWarnings("unused")
	public static void countdown(final String name, final Main plugin) {
		
		if (!countdownIsStart) {
			countdownIsStart = true;
			BukkitTask localBukkitTask = new BukkitRunnable() {
				int seconds = 36;

				public void run() {
					if (Bukkit.getOnlinePlayers().size() >= plugin.getConfig().getInt("min-players")) {
						this.seconds -= 1;

						String message = ChatColor.DARK_GRAY +

						"[" + ChatColor.GREEN + name + ChatColor.DARK_GRAY
								+ "] " + ChatColor.WHITE + this.seconds
								+ " sec avant le début de la partie !";
						String message2 = ChatColor.DARK_GRAY + "["
								+ ChatColor.GREEN + name + ChatColor.DARK_GRAY
								+ "] " + ChatColor.WHITE + this.seconds
								+ " sec pour que la partie débute !";
						if (this.seconds <= 30) {
							if (this.seconds == 30) {
								Messages.BroadcastMessage(message);
								Effects.partyeffects(Sound.ORB_PICKUP,
										this.seconds);
							} else if (this.seconds == 0) {
								Effects.level(this.seconds);

								Random random = new Random();
								
								plugin.rush.start();

								cancel();
							} else if (this.seconds <= 5) {
								Messages.BroadcastMessage(message2);
								for(Player p : Bukkit.getOnlinePlayers()){
									Title title = new Title(p, "§6Début de la partie dans ", "§c " + seconds + " §6 sec");
								}
								Effects.partyeffects(Sound.SUCCESSFUL_HIT,
										this.seconds);
							}
						}
						Effects.level(this.seconds);
					}
				}
			}.runTaskTimer(plugin, 0L, 20L);
		}
	}

	static void xpSound() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.0F);
		}
	}

	public void ClearDrops(String world) {
		World w = Bukkit.getServer().getWorld(world);
		if (w == null) {
			return;
		}
		for (Entity e : w.getEntities()) {
			if (e.getType() == EntityType.DROPPED_ITEM) {
				e.remove();
			}
		}
	}
}
