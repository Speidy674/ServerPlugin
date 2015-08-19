package de.neon.serverplugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.neon.serverplugin.town.Town;

public class TownUtil {

	public static boolean isMemberInTown(Player p) {
		return DataUtil.gets(p, "town") != "null";
	}
	
	public static boolean ownsTown(Player p) {
		return DataUtil.getb(p, "ownsTown");
	}
	
	public static boolean isInTown(Player p) {
		return ownsTown(p) || isMemberInTown(p);
	}
	
	public static Town getTown(File file) {
		if(!file.exists()) {
			return null;
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		String name = config.getString("name");
		String owner = config.getString("owner");
		double minX = config.getDouble("minX");
		double maxX = config.getDouble("maxX");
		double minZ = config.getDouble("minZ");
		double maxZ = config.getDouble("maxZ");
		ArrayList<String> members = new ArrayList<String>();
		for(int i = 0; i < Town.MAX_MEMBERS; i++) {
			if(!config.getString("member"+i).equals("")) {
				members.add(config.getString("member"+i));
			}
		}
		return new Town(owner, name, minX, maxX, minZ, maxZ, members);
	}
	
	public static ArrayList<Town> getTowns() {
		ArrayList<Town> towns = new ArrayList<Town>();
		File[] files = ServerPlugin.town.listFiles();
		for(File file : files) {
			towns.add(getTown(file));
		}
		return towns;
	}
	
	public static boolean exists(String name) {
		return getTown(new File(ServerPlugin.town+"/"+name+".yml")) != null;
	}
	
	public static boolean createTown(String owner, String name, double minX, double minZ, double maxX, double maxZ) {
		File file = new File(ServerPlugin.towns+"/"+name+".yml");
		if(file.exists()) {
			return false;
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.options().copyDefaults(true);
		config.options().header("Do not change any value here unless you know, what you are doing.");
		config.addDefault("name", name);
		config.addDefault("owner", owner);
		config.addDefault("minX", minX);
		config.addDefault("minZ", minZ);
		config.addDefault("maxX", maxX);
		config.addDefault("maxZ", maxZ);
		for(int i = 0; i < Town.MAX_MEMBERS; i++) {
			config.addDefault("member"+i, "");
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}