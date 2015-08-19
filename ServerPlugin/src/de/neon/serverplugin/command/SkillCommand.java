package de.neon.serverplugin.command;

import java.util.List;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.neon.serverplugin.DataUtil;

public class SkillCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("skill")) {
				try {
					if(DataUtil.geti(p, "skillpoints") > 0) {
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
						BookMeta meta = (BookMeta) book.getItemMeta();
						try {
						     java.lang.reflect.Field pagesField = Class.forName("org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook").getDeclaredField("pages");
						     pagesField.setAccessible(true);
						     List<IChatBaseComponent> pages = (List<IChatBaseComponent>) pagesField.get(meta);
						     pages.add(ChatSerializer.a("{pages:[,{\\\"text\\\":\\\"Stats\n\\\",\\\"bold\\\":true,\\\"underlined\\\":true},{\\\"text\\\":\\\"\nStärke:\\\",\\\"color\\\":\\\"aqua\\\",\\\"bold\\\":false,\\\"underlined\\\":false},{\\\"text\\\":\\\""+DataUtil.geti(p, "strength")+"\\\",\\\"color\\\":\\\"gold\\\"},{\\\"text\\\":\\\"[+]\\\",\\\"color\\\":\\\"red\\\",\\\"bold\\\":true,\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/stats strength\\\"},\\\"hoverEvent\\\":{\\\"action\\\":\\\"show_text\\\",\\\"value\\\":{\\\"text\\\":\\\"\\\",\\\"extra\\\":[{\\\"text\\\":\\\"Erhöhe Stärke\\\"}]}}},{\\\"text\\\":\\\"\nVerteidigung:\\\",\\\"color\\\":\\\"aqua\\\",\\\"bold\\\":false},{\\\"text\\\":\\\""+DataUtil.geti(p, "defensive")+"\\\",\\\"color\\\":\\\"gold\\\"},{\\\"text\\\":\\\"[+]\\\",\\\"color\\\":\\\"red\\\",\\\"bold\\\":true,\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/stats defense\\\"},\\\"hoverEvent\\\":{\\\"action\\\":\\\"show_text\\\",\\\"value\\\":{\\\"text\\\":\\\"\\\",\\\"extra\\\":[{\\\"text\\\":\\\"Erhöhe deine Verteidigung\\\"}]}}},{\\\"text\\\":\\\"\nVitalität:\\\",\\\"color\\\":\\\"aqua\\\",\\\"bold\\\":false},{\\\"text\\\":\\\""+DataUtil.geti(p, "vitality")+"\\\",\\\"color\\\":\\\"gold\\\"},{\\\"text\\\":\\\"[+]\\\",\\\"color\\\":\\\"red\\\",\\\"bold\\\":true,\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/stats vitality\\\"},\\\"hoverEvent\\\":{\\\"action\\\":\\\"show_text\\\",\\\"value\\\":{\\\"text\\\":\\\"\\\",\\\"extra\\\":[{\\\"text\\\":\\\"Erhöhe deine Vitalität\\\"}]}}},{\\\"text\\\":\\\"\nGeschicklichkeit:\\\",\\\"color\\\":\\\"aqua\\\",\\\"bold\\\":false},{\\\"text\\\":\\\""+DataUtil.geti(p, "dexterity")+"\\\",\\\"color\\\":\\\"gold\\\"},{\\\"text\\\":\\\"[+]\\\",\\\"color\\\":\\\"red\\\",\\\"bold\\\":true,\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/stats dexterity\\\"},\\\"hoverEvent\\\":{\\\"action\\\":\\\"show_text\\\",\\\"value\\\":{\\\"text\\\":\\\"\\\",\\\"extra\\\":[{\\\"text\\\":\\\"Erhöhe deine Geschicklichkeit\\\"}]}}}}"));
						} catch(Exception e) {
							e.printStackTrace();
						}
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
}