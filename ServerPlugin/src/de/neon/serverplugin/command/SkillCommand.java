package de.neon.serverplugin.command;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.neon.serverplugin.DataUtil;

public class SkillCommand implements CommandExecutor {

	@Override
	@SuppressWarnings("unchecked")
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player) {
			Player p = (Player) s;
			if(cmd.getName().equalsIgnoreCase("skill")) {
				try {
					ItemStack book = new ItemStack(Material.BOOK);
					BookMeta meta = (BookMeta) book.getItemMeta();
					Field pagesField = null;
					pagesField = CraftMetaBook.class.getDeclaredField("pages");
					pagesField.setAccessible(true);
					List<IChatBaseComponent> pages = (List<IChatBaseComponent>) pagesField.get(meta);
					if(DataUtil.geti(p, "skillpoints") > 0) {
						pages.add(ChatSerializer.a(""));
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}