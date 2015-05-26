package be.seveningful.sevsrush.bungeecord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.EventsClass;
import be.seveningful.sevsrush.Main;
import be.seveningful.sevsrush.util.MOTD;


public class FinishGame {
	static Main plugin;
	public static void summonFireWork(Player p) {
		 Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withTrail();
        builder.withFlicker();
        builder.withFade(Color.RED);
        builder.withColor(Color.GRAY);
        builder.withColor(Color.SILVER);
        builder.with(FireworkEffect.Type.BALL_LARGE);
        fwm.addEffects(builder.build());
        fwm.setPower(2);
        fw.setFireworkMeta(fwm);
		
	}
	@SuppressWarnings("unused")
	public static void finalfireworks(Main main){
		final BukkitTask fireworks = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Team team : EventsClass.teamsalive){
					for(OfflinePlayer players : EventsClass.teamsalive.get(0).getPlayers())
					summonFireWork(players.getPlayer());
				}
				
			}
		}.runTaskTimer(main, 0, 4);
BukkitTask fireworks2 = new BukkitRunnable() {
			
			@Override
			public void run() {
				fireworks.cancel();
				
			}
		}.runTaskLater(main, 20*6);
		
		
	}
	
	public static void finish(final Main main){
		finalfireworks(main);
		MOTD.setMOTD(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Reboot");
		BukkitTask task = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					main.teleportServer(p, main.getConfig().getString("fallback-server"));
					Bukkit.shutdown();
				}
				
			}
		}.runTaskLater(main, 20*10);
		
	}
}
