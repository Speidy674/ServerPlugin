package de.neon.serverplugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigUtil {

	private static FileConfiguration exampleConfig;
	
	public static void init() {
		File file = new File(ServerPlugin.dataFolder+"/exampleConfig.yml");
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
		exampleConfig.options().header("Do not change any value here unless you know, what you are doing.");
		exampleConfig.addDefault("join", "§7[§a+§7] §6%player%");
		exampleConfig.addDefault("quit", "§7[§c-§7] §6%player%");
		exampleConfig.addDefault("broadcast", "§7[§6Broadcast§7]§f");
		exampleConfig.addDefault("towns.maxMembers", 60);
		exampleConfig.addDefault("towns.sizeX", 30);
		exampleConfig.addDefault("towns.sizeZ", 30);
		try {
			exampleConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		File file1 = new File(ServerPlugin.dataFolder+"/config.yml");
		if(file1.exists()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file1);
			config.options().header("Do not change any value here unless you know, what you are doing.");
			config.options().copyDefaults(true);
			Map<String, Object> index = exampleConfig.getValues(true);
			for(String string : index.keySet()) {
					config.addDefault(string, index.get(string));
			}
			setConfig(config);
		}
		
		
	}

	public static FileConfiguration getConfig() {
		File file = new File(ServerPlugin.dataFolder+"/config.yml");
		return YamlConfiguration.loadConfiguration(file);
	}

	public static void setConfig(FileConfiguration config) {
		File file = new File(ServerPlugin.dataFolder+"/config.yml");
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void correctConfig() {
		FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		Map<String, Object> index1 = exampleConfig.getValues(true);
		Map<String, Object> index2 = config.getValues(true);
		for(String s : index1.keySet()) {
			if(!index2.containsKey(s)) {
				config.addDefault(s, index2.get(s));
			}
		}
		setConfig(config);
	}
	
	public static String gets(String value) {
		return getConfig().getString(value);
	}
	
	public static int geti(String value) {
		return getConfig().getInt(value);
	}

	public static double getd(String value) {
		return getConfig().getDouble(value);
	}

	public static float getf(String value) {
		return (float) getConfig().getDouble(value);
	}

	public static boolean getb(String value) {
		return getConfig().getBoolean(value);
	}

}
