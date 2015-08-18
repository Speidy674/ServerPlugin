package de.neon.serverplugin;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_8_R3.ChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Util {

	public static Map<Player, Player> inviteList = new HashMap<Player, Player>();
	public static Map<Player, Player> duelList = new HashMap<Player, Player>();
	
	public static final double XP_SCALE = 1.8;
	public static final double XP_FACTOR = 1.4;
	
	public static void sendConsole(CommandSender s) {
		s.sendMessage("[ServerPlugin] Du kannst das Plugin nicht von der Konsole aus benutzen.");
	}
	
	public static void displayTitleBar(Player p, String title, String subTitle, int fadeIn, int ticks, int fadeOut) {
		IChatBaseComponent titleComponent = ChatBaseComponent.ChatSerializer.a("{\"text\": \""+title+"\"}");
		IChatBaseComponent subTitleComponent = ChatBaseComponent.ChatSerializer.a("{\"text\": \""+subTitle+"\"}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent, fadeIn, ticks, fadeOut);
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleComponent, fadeIn, ticks, fadeOut);
		CraftPlayer cPlayer = (CraftPlayer) p;
		cPlayer.getHandle().playerConnection.sendPacket(titlePacket);
		cPlayer.getHandle().playerConnection.sendPacket(subTitlePacket);
	}

	public static boolean isInDuelWithEachOther(Player p1, Player p2) {
		return duelList.get(p1) == p2 || duelList.get(p2) == p1;
	}

	public static int experienceRequied(int level) {
		return (int) Math.pow(XP_FACTOR + level, XP_SCALE);
	}
	
	public static float getXPPercentage(Player p) {
		return (100 * DataUtil.getf(p, "xp")) / experienceRequied(DataUtil.geti(p, "level")+1);
	}
	
	public static String formatFloat(float f, int amount) {
		String s = String.valueOf(f);
		char[] c = s.toCharArray();
		int sub = 0;
		for(int i = 0; i < c.length; i++) {
			if(c[i] == '.') {
				sub = i;
			}
		}
		return s.substring(0, sub+amount+1);
	}
}