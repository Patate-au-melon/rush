package be.seveningful.sevsrush;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.messages.PlayerTeam;
import be.seveningful.sevsrush.util.MOTD;
import be.seveningful.sevsrush.util.Title;

public class Game {
	static Main plugin;

	Game(Main main) {
		Game.plugin = main;
	}

	public static void resetScoreboards() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}

	public static void clearInv(Player player) {
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().clear();
		player.closeInventory();
	}

	public static void teleportTeams() {

		for (Player noteam : Bukkit.getOnlinePlayers()) {
			Title title = new Title(noteam, "§6Début de la partie !", "");
			for (Team teams : plugin.s.getTeams()) {

				if (teams.getPlayers().size() < plugin.getConfig().getInt(
						"players-per-team")
						&& !PlayerTeam.hasTeam(noteam)) {
					teams.addPlayer(noteam);

					noteam.sendMessage(ChatColor.GREEN
							+ "Vous avez rejoint une équipe aléatoire !");
					

				}

			}
			

		}
		for (Team teams : plugin.s.getTeams()) {
			if (teams.getPlayers().size() == 0) {
				teams.unregister();
				EventsClass.teamsalive.remove(teams);
			}
		}
		for (Player online : Bukkit.getOnlinePlayers()) {

			teleportplayer(online);
			clearInv(online);
			online.setGameMode(GameMode.SURVIVAL);
		}
	}

	private static void teleportplayer(Player noteam) {
		Team team = PlayerTeam.getTeam(noteam);
		noteam.teleport(new Location(noteam.getWorld(), plugin.getConfig()
				.getInt(team.getName() + ".x"), plugin.getConfig().getInt(
				team.getName() + ".y"), plugin.getConfig().getInt(
				team.getName() + ".z")));

	}

	public static boolean rushisstarted = false;

	public static void start() {
		
		for (Team teams : plugin.s.getTeams()) {
			EventsClass.teamsalive.add(teams);
		}
		resetScoreboards();
		teleportTeams();
		GameLoop gameLoop = new GameLoop(plugin);
		rushisstarted = true;

		MOTD.setMOTD("§6 En jeu ");

	}
}
