package be.seveningful.sevsrush.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeam {

	public static boolean hasTeam(Player p) {
		if (getTeam(p) != null) {
			return true;
		}
		return false;
	}

	public static Team getTeam(Player p) {
		Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();

		return s.getPlayerTeam(p);

	}

	public static String getPlayerName(Player p) {

		if (hasTeam(p)) {
			return getTeam(p).getPrefix() + p.getName()
					+ getTeam(p).getSuffix();
		}

		return p.getName();
	}

}
