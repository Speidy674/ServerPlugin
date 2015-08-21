package de.neon.serverplugin.command;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.ServerPlugin;
import de.neon.serverplugin.TownUtil;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.town.Town;

public class TownCommand implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("town")) {
				if(args.length == 0) {
					if(ServerPlugin.create.contains(p)) {
						ServerPlugin.create.remove(p);
						p.sendMessage("§6Du erstellst nun keine Stadt mehr.");
						return true;
					}
					if(ServerPlugin.invite.contains(p)) {
						ServerPlugin.invite.remove(p);
						p.sendMessage("§6Du lädst nun niemanden ein.");
						return true;
					}
					if(ServerPlugin.welcome.contains(p)) {
						ServerPlugin.welcome.remove(p);
						p.sendMessage("§6Du änderst nun nicht mehr die Willkommens Nachricht.");
						return true;
					}
					if(ServerPlugin.adoption.contains(p)) {
						ServerPlugin.adoption.remove(p);
						p.sendMessage("§6Du änderst nun nicht mehr die Verabschiedungs Nachricht.");
						return true;
					}
					if(!TownUtil.isInTown(p)) {
						sendCreateNewTownGUI(p);
						return true;
					}
					if(TownUtil.ownsTown(p)) {
						sendOwnerGUI(p);
						return true;
					}
					if(TownUtil.isMemberInTown(p) && !TownUtil.ownsTown(p)) {
						sendMemberGUI(p);
						return true;
					}
				}
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("accept")) {
						if(!Util.townInviteList.containsKey(p)) {
							p.sendMessage("§4Du hast keine laufende Anfrage");
							return true;
						} else {
							if(!TownUtil.exists(Util.townInviteList.get(p).getName())) {
								p.sendMessage("§4Die Stadt wurde gelöscht.");
								Util.townInviteList.remove(p);
								return true;
							}
							Town town = Util.townInviteList.get(p);
							town.addMember(p.getName());
							p.sendMessage("§aDu bist jetzt ein Bewohner der Stadt §b"+town.getName()+"§a.");
							if(Bukkit.getPlayer(town.getOwner()) != null) {
								Bukkit.getPlayer(town.getOwner()).sendMessage("§b"+p.getName()+" §aist nun ein Bewohner deiner Stadt!");
							}
							Util.townInviteList.remove(p);
							DataUtil.set(p, "town", town.getName());
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("decline")) {
						if(!Util.townInviteList.containsKey(p)) {
							p.sendMessage("§4Du hast keine laufende Anfrage");
							return true;
						} else {
							Town town = Util.townInviteList.get(p);
							p.sendMessage("§aDu hast die Anfrage abgelehnt.");
							if(Bukkit.getPlayer(town.getOwner()) != null) {
								Bukkit.getPlayer(town.getOwner()).sendMessage("§4"+p.getName()+" hat die Anfrage abgelehnt!");
							}
							Util.townInviteList.remove(p);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private void sendMemberGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "Towns");
		ItemStack leave = new ItemStack(Material.BARRIER);
		ItemMeta leaveMeta = leave.getItemMeta();
		leaveMeta.setDisplayName("§cVerlasse deine Stadt");
		leave.setItemMeta(leaveMeta);
		ItemStack member = new ItemStack(Material.SKULL_ITEM);
		ItemMeta memberMeta = member.getItemMeta();
		memberMeta.setDisplayName("§bZeige dir die Stadtbewohner Liste");
		member.setItemMeta(memberMeta);
		ItemStack owner = new ItemStack(Material.SKULL_ITEM);
		SkullMeta ownerMeta = (SkullMeta) owner.getItemMeta();
		owner.setDurability((short) 3);
		if(Bukkit.getPlayer(TownUtil.getTown(p).getOwner()) == null) {
			ownerMeta.setDisplayName("§cOwner: "+TownUtil.getTown(p).getOwner());
		} else {
			ownerMeta.setDisplayName("§aOwner: "+TownUtil.getTown(p).getOwner());
		}
		owner.setItemMeta(ownerMeta);
		inv.addItem(member);
		inv.addItem(owner);
		inv.setItem(8, leave);
		p.openInventory(inv);
	}
	
	private void sendOwnerGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "Towns");
		ItemStack delete = new ItemStack(Material.TNT);
		ItemMeta deleteMeta = delete.getItemMeta();
		deleteMeta.setDisplayName("§4Lösche deine Stadt");
		delete.setItemMeta(deleteMeta);
		ItemStack welcome = new ItemStack(Material.YELLOW_FLOWER);
		ItemMeta welcomeMeta = welcome.getItemMeta();
		welcomeMeta.setDisplayName("§aVeränder die Willkommens Narchicht");
		welcome.setItemMeta(welcomeMeta);
		ItemStack adoption = new ItemStack(Material.RED_ROSE);
		ItemMeta adoptionMeta = adoption.getItemMeta();
		adoptionMeta.setDisplayName("§cVeränder die Verabschiedungs Narchicht");
		adoption.setItemMeta(adoptionMeta);
		ItemStack member = new ItemStack(Material.SKULL_ITEM);
		ItemMeta memberMeta = member.getItemMeta();
		memberMeta.setDisplayName("§bZeige dir die Stadtbewohner Liste");
		member.setItemMeta(memberMeta);
		ItemStack invite = new ItemStack(Material.WATCH);
		ItemMeta inviteMeta = invite.getItemMeta();
		inviteMeta.setDisplayName("§6Lade neue Leute in deine Stadt ein");
		invite.setItemMeta(inviteMeta);
		inv.addItem(member);
		inv.addItem(invite);
		inv.addItem(welcome);
		inv.addItem(adoption);
		inv.setItem(8, delete);
		p.openInventory(inv);
	}

	private void sendCreateNewTownGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "Towns");
		ItemStack stack = new ItemStack(Material.COMPASS);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§2Erstelle eine neue Stadt");
		stack.setItemMeta(meta);
		inv.addItem(stack);
		p.openInventory(inv);
	}
}