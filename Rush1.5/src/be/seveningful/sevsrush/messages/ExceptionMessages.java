package be.seveningful.sevsrush.messages;

import org.bukkit.ChatColor;

import be.seveningful.sevsrush.util.Prefix;


public class ExceptionMessages {

	public static String noPermission(){
		return Prefix.getPrefix() + ChatColor.RED + " Vous n'avez pas la permission d'éxecuter cette commande !";
	}
	
}
