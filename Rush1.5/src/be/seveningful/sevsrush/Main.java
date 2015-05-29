package be.seveningful.sevsrush;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.messages.CommandMessages;
import be.seveningful.sevsrush.messages.ExceptionMessages;
import be.seveningful.sevsrush.teams.Teams;
import be.seveningful.sevsrush.util.Lobby;
import be.seveningful.sevsrush.util.MOTD;
import be.seveningful.sevsrush.util.Prefix;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin {
	Teams teams;
	/*
	 * @author Seveningful
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	public Game rush;
	Scoreboard s;
	Team rouge;
	Team verte;
	Team jaune;
	Team bleue;

	@Override
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this,
				"BungeeCord");
		getLogger().info("Plugin développé par Seveningful");

		Bukkit.getPluginManager().registerEvents(new EventsClass(this), this);
		this.teams = new Teams(this);
		s = Bukkit.getScoreboardManager().getMainScoreboard();
		for (Team allteams : s.getTeams()) {
			allteams.unregister();
		}
		jaune = s.registerNewTeam("Jaune");
		jaune.setSuffix(ChatColor.WHITE.toString());
		jaune.setPrefix(ChatColor.YELLOW.toString());
		rouge = s.registerNewTeam("Rouge");
		rouge.setPrefix(ChatColor.RED.toString());
		rouge.setSuffix(ChatColor.WHITE.toString());
		if(getConfig().getInt("teams") == 3){
		
		}else if(getConfig().getInt("teams") == 4){
			verte = s.registerNewTeam("Verte");
			verte.setPrefix(ChatColor.GREEN.toString());
			verte.setSuffix(ChatColor.WHITE.toString());
		bleue = s.registerNewTeam("Bleue");
		bleue.setPrefix(ChatColor.BLUE.toString());
		bleue.setSuffix(ChatColor.WHITE.toString());
		}
		

		
		
		
		

		getConfig().addDefault("teams", 4);
		getConfig().addDefault("Bleue.x", 0);
		getConfig().addDefault("Bleue.y", 0);
		getConfig().addDefault("Bleue.z", 0);
		getConfig().addDefault("Rouge.x", 0);
		getConfig().addDefault("Rouge.y", 0);
		getConfig().addDefault("Rouge.z", 0);
		getConfig().addDefault("Jaune.x", 0);
		getConfig().addDefault("Jaune.y", 0);
		getConfig().addDefault("Jaune.z", 0);
		getConfig().addDefault("Verte.x", 0);
		getConfig().addDefault("Verte.y", 0);
		getConfig().addDefault("Verte.z", 0);
		getConfig().addDefault("min-players", 1);
		getConfig().addDefault("players-per-team", 1);
		getConfig().addDefault("fallback-server", "survival");
		getConfig().addDefault("lobby.X", 0);
		getConfig().addDefault("lobby.Y", 0);
		getConfig().addDefault("lobby.Z", 0);
		getConfig().options().copyDefaults(true);
		saveConfig();
		MOTD.setMOTD(ChatColor.GREEN + "✔ Rejoindre✔");
		rush = new Game(this);

	}

	public void teleportServer(Player p, String server) {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(this, "BungeeCord", out.toByteArray());

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("rush")) {
			if (args[0].equalsIgnoreCase("setspawn")) {
				if (args.length == 1) {
					Lobby.setLobby(this, p.getLocation());
					p.sendMessage(CommandMessages.SpawnMessage());
				} else {
					Teams.setspawn(Teams.getTeambyName(args[1]),
							p.getLocation());
					p.sendMessage(CommandMessages.teamSpawnMessage());
				}
				return true;
			} else if (args[0].equalsIgnoreCase("stop")){
				if (p.isOp()) {
					Bukkit.broadcastMessage(Prefix.getPrefix() + ChatColor.RED
							+ " Le jeu a été arrêté par un administarteur !");
					Bukkit.shutdown();
					return true;
				} else {
					p.sendMessage(ExceptionMessages.noPermission());
				}
			}

		}
		return super.onCommand(sender, command, label, args);
	}

}
