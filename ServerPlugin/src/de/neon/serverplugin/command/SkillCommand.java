package de.neon.serverplugin.command;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import de.neon.serverplugin.DataUtil;

public class SkillCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("skill")) {
				try {
					if(DataUtil.geti(p, "skillpoints") > 0) {
						p.getInventory().addItem(getBook(p));
					} else {
						
					}
					return true;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private CraftItemStack getBook(Player p) {
		ItemStack stack = new ItemStack(Item.d("written_book"));
		NBTTagCompound tag = new NBTTagCompound();
		if(!stack.hasTag()) {
			tag = new NBTTagCompound();
			stack.setTag(tag);
		}
		tag.set("author", new NBTTagString(ChatColor.MAGIC+"aaaaa"));
		tag.set("title", new NBTTagString(ChatColor.GOLD+"Skill-Buch"));
		tag.set("pages", new NBTTagString("{\"text\":\"Stats\n\",\"bold\":true,\"underlined\":true},{\"text\":\"\nStärke:\",\"color\":\"aqua\",\"bold\":false,\"underlined\":false},{\"text\":\""+DataUtil.geti(p, "strength")+"\",\"color\":\"gold\"},{\"text\":\"[+]\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stats strength\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Erhöhe Stärke\"}]}}},{\"text\":\"\nVerteidigung:\",\"color\":\"aqua\",\"bold\":false},{\"text\":\""+DataUtil.geti(p, "defensive")+"\",\"color\":\"gold\"},{\"text\":\"[+]\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stats defense\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Erhöhe deine Verteidigung\"}]}}},{\"text\":\"\nVitalität:\",\"color\":\"aqua\",\"bold\":false},{\"text\":\""+DataUtil.geti(p, "vitality")+"\",\"color\":\"gold\"},{\"text\":\"[+]\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stats vitality\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Erhöhe deine Vitalität\"}]}}},{\"text\":\"\nGeschicklichkeit:\",\"color\":\"aqua\",\"bold\":false},{\"text\":\""+DataUtil.geti(p, "dexterity")+"\",\"color\":\"gold\"},{\"text\":\"[+]\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stats dexterity\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Erhöhe deine Geschicklichkeit\"}]}}}"));
		stack.setTag(tag);
		return CraftItemStack.asCraftMirror(stack);
	}
}