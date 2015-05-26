package be.seveningful.sevsrush;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.bungeecord.FinishGame;
import be.seveningful.sevsrush.util.Prefix;

public class GameLoop {

	public GameLoop(final Main main) {
		BukkitTask task = new BukkitRunnable() {

			@Override
			public void run() {
				for (Team teams : main.s.getTeams()) {
					if (teams.getPlayers().size() == 0) {
						teams.unregister();
						EventsClass.teamsalive.remove(teams);
					}
				}
				if (main.s.getTeams().size() == 1) {

					Team winner = EventsClass.teamsalive.get(0);
					Bukkit.broadcastMessage(Prefix.getPrefix()
							+ ChatColor.RESET + " L'équipe "
							+ winner.getPrefix() + ChatColor.BOLD.toString()
							+ winner.getDisplayName() + ChatColor.RESET
							+ " a gagné !");
					for (OfflinePlayer p : winner.getPlayers()) {
						((Player) p).sendMessage(ChatColor.GRAY
								+ "Gain de Coins :" + ChatColor.GOLD
								+ " 20.0 (Rush gagné)");
					}
					FinishGame.finish(main);
					cancel();
				}

			}
		}.runTaskTimer(main, 0, 0);
	}

}
