package de.neon.serverplugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DataUtil {

	private static FileConfiguration exampleConfig;
	
	public static void init() {
		File file = new File(ServerPlugin.players+"/example.yml");
		if(file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exampleConfig = YamlConfiguration.loadConfiguration(file);
		exampleConfig.options().copyDefaults(true);
		exampleConfig.addDefault("strength", 10);
		exampleConfig.addDefault("defensive", 10);
		exampleConfig.addDefault("dexterity", 1);
		exampleConfig.addDefault("vitality", 1);
		exampleConfig.addDefault("xp", 0);
		exampleConfig.addDefault("level", 1);
		exampleConfig.addDefault("skillpoints", 0);
		exampleConfig.addDefault("wins", 0);
		exampleConfig.addDefault("loses", 0);
		exampleConfig.addDefault("ownsTown", false);
		exampleConfig.addDefault("town", "null");
		try {
			exampleConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createNewData(Player p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.options().header("Do not change any value here unless you know, what you are doing.");
		config.options().copyDefaults(true);
		Map<String, Object> index = exampleConfig.getValues(true);
		for(String string : index.keySet()) {
			config.addDefault(string, index.get(string));
		}
		savePlayerData(config, p);
	}
	
	public static FileConfiguration getPlayerData(OfflinePlayer p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		return YamlConfiguration.loadConfiguration(file);
	}

	public static void savePlayerData(FileConfiguration config, OfflinePlayer p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileConfiguration getPlayerData(Player p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		return YamlConfiguration.loadConfiguration(file);
	}

	public static void savePlayerData(FileConfiguration config, Player p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasAccount(Player p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		return file.exists();
	}
	
	public static boolean hasNewestAccount(Player p) {
		FileConfiguration config = getPlayerData(p);
		Map<String, Object> index1 = exampleConfig.getValues(false);
		Map<String, Object> index2 = config.getValues(false);
		for(String s : index1.keySet()) {
			if(!index2.containsKey(s)) {
				return false;
			}
		}
		return true;
	}

	public static void correctAccount(Player p) {
		FileConfiguration config = getPlayerData(p);
		config.options().copyDefaults(true);
		Map<String, Object> index1 = exampleConfig.getValues(false);
		Map<String, Object> index2 = config.getValues(false);
		for(String s : index1.keySet()) {
			if(!index2.containsKey(s)) {
				config.addDefault(s, index2.get(s));
			}
		}
		savePlayerData(config, p);
	}
	
	public static void removeAccount(Player p) {
		File file = new File(ServerPlugin.players + "/"+p.getUniqueId()+".yml");
		file.delete();
	}
	
	public static String gets(Player p, String value) {
		return getPlayerData(p).getString(value);
	}
	
	public static int geti(Player p, String value) {
		return getPlayerData(p).getInt(value);
	}

	public static double getd(Player p, String value) {
		return getPlayerData(p).getDouble(value);
	}

	public static float getf(Player p, String value) {
		return (float) getPlayerData(p).getDouble(value);
	}

	public static boolean getb(Player p, String value) {
		return getPlayerData(p).getBoolean(value);
	}
	
	public static void set(Player p, String source, Object value) {
		FileConfiguration config = getPlayerData(p);
		config.set(source, value);
		savePlayerData(config, p);
	}
	
	public static void set(OfflinePlayer p, String source, Object value) {
		FileConfiguration config = getPlayerData(p);
		config.set(source, value);
		savePlayerData(config, p);
	}
	
	public static void increasei(Player p, String source, int i) {
		set(p, source, geti(p, source)+i);
	}
	
	public static void increasef(Player p, String source, float i) {
		set(p, source, getf(p, source)+i);
	}
	
	public static void increased(Player p, String source, int i) {
		set(p, source, getd(p, source)+i);
	}
	
	public static void decreasei(Player p, String source, int i) {
		set(p, source, geti(p, source)-i);
	}
	
	public static void decreased(Player p, String source, float i) {
		set(p, source, getf(p, source)-i);
	}
	
	public static void decreasef(Player p, String source, int i) {
		set(p, source, getd(p, source)-i);
	}
}