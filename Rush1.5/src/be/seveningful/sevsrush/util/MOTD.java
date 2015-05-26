package be.seveningful.sevsrush.util;

import net.minecraft.server.v1_8_R3.DedicatedServer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public class MOTD {
	public static void setMOTD(String motd) {
		DedicatedServer ss = (((CraftServer) Bukkit.getServer()).getHandle()
				.getServer());

		ss.setMotd(motd);
	}
	
	
}
