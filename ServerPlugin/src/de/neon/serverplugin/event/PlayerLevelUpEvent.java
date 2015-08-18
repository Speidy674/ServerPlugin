package de.neon.serverplugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUpEvent extends Event {

	public static HandlerList handlers = new HandlerList();
	
	private final Player p;
	private final int grewTo;
	
	public PlayerLevelUpEvent(Player p, int grewTo) {
		this.p = p;
		this.grewTo = grewTo;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public int getGrewFrom() {
		return grewTo - 1;
	}
	
	public int getGrewTo() {
		return grewTo;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
}