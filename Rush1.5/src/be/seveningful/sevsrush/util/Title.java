 package be.seveningful.sevsrush.util;
 

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
 
 public class Title
 {
   public Title(Player p, String title, String subtitle)
   {
     IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + title + "\"}]}");
     IChatBaseComponent subTitle = ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + subtitle + "\"}]}");
     PacketPlayOutTitle titles = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle, 1, 2, 0);
     PacketPlayOutTitle subtitles = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subTitle);
     PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
     connection.sendPacket(subtitles);
     connection.sendPacket(titles);
   }
 }

