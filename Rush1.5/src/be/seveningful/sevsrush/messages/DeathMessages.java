package be.seveningful.sevsrush.messages;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeathMessages {
	PlayerTeam playerName;

	public static void normalDeath(Player p){
		Bukkit.broadcastMessage(ChatColor.BOLD + PlayerTeam.getPlayerName(p) + ChatColor.GREEN + " est mort !" );
	}
	
	public static void finalDeath(Player p){
		Bukkit.broadcastMessage(ChatColor.BOLD + PlayerTeam.getPlayerName(p) + ChatColor.GREEN + " est mort " + ChatColor.RED + "et son lit n'est plus là " + ChatColor.GREEN + "!" );
	}
	
}
