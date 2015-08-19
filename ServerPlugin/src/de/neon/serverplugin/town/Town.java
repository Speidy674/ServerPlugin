package de.neon.serverplugin.town;

import java.util.ArrayList;

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