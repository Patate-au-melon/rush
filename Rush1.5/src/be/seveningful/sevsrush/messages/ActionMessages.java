package be.seveningful.sevsrush.messages;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionMessages {

	public static String joinMessage(Player p) {
		return ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "RUSH"
				+ ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW
				+ PlayerTeam.getPlayerName(p) + " a rejoint la partie "
				+ ChatColor.GRAY + "[" + ChatColor.BLUE
				+ Bukkit.getOnlinePlayers().size() + "/"
				+ Bukkit.getMaxPlayers() + ChatColor.GRAY + "]";
	}

	public static String leaveMessage(Player player) {

		return null;
	}

}
