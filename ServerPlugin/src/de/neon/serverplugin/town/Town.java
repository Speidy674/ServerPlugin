package de.neon.serverplugin.town;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.neon.serverplugin.ServerPlugin;

public class Town {

	public static final int MAX_MEMBERS = 60;
	public static final int SIZE_X = 30;
	public static final int SIZE_Z = 30;
	
	private String owner;
	private String name;
	private ArrayList<String> members;
	private double minX;
	private double maxX;
	private double minZ;
	private double maxZ;
	
	public Town(String owner, String name, double minX, double maxX, double minZ, double maxZ, ArrayList<String> members) {
		this.owner = owner;
		this.name = name;
		this.members = members;
		this.minX = minX;
		this.maxX = maxX;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}
	
	public void addMember(String s) {
		members.add(s);
		File file = new File(ServerPlugin.town+"/"+name+".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		for(int i = 0; i < MAX_MEMBERS; i++) {
			if(config.getString("member"+i).equals("")) {
				config.set("members"+i, s);
			}
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeMember(String s) {
		members.remove(s);
		File file = new File(ServerPlugin.town+"/"+name+".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		for(int i = 0; i < MAX_MEMBERS; i++) {
			if(config.getString("member"+i).equals(s)) {
				config.set("members"+i, "");
			}
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isMember(String s) {
		return members.contains(s);
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinZ() {
		return minZ;
	}

	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}