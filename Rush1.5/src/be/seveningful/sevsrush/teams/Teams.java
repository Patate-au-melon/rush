package be.seveningful.sevsrush.teams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.Main;

public class Teams {
	public static Main plugin;
	
	public Teams(Main main){
		this.plugin = main;
	}

	public static Team getTeambyName(String string) {

		return Bukkit.getScoreboardManager().getMainScoreboard()
				.getTeam(string);
	}

	public static void setspawn(Team teambyName, Location loc) {
		Team team = teambyName;
		
		plugin.getConfig().set(team.getName()+ ".x", loc.getX());
		plugin.getConfig().set(team.getName()+ ".y", loc.getY());
		plugin.getConfig().set(team.getName()+ ".z", loc.getZ());
		plugin.saveConfig();
		
	}

}
