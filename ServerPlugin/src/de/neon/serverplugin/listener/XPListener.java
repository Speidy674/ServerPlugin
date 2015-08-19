package de.neon.serverplugin.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import de.neon.serverplugin.DataUtil;
import de.neon.serverplugin.Util;
import de.neon.serverplugin.event.PlayerLevelUpEvent;
import de.neon.serverplugin.hologram.Hologram;

public class XPListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		LivingEntity e1 = e.getEntity();
		LivingEntity e2 = e1.getKiller();
		if(e2 instanceof Player && !(e1 instanceof Player)) {
			Player p = (Player) e2;
			if(DataUtil.geti(p, "level") >= 60) return;
			DataUtil.increasef(p, "xp", e.getDroppedExp() / 3.5f);
			
			List<String> lines = new ArrayList<String>();
            lines.add("§7[§aXP§7]");
            lines.add("§4+"+Util.formatFloat(e.getDroppedExp() / 3.5f, 1));
		
            final Location loc = e1.getLocation();
            loc.setY(e2.getLocation().getY()-1);
            
            Hologram hologram = new Hologram();
            hologram.setLines(lines);
            hologram.setLocation(loc);
            hologram.setHeight(1.5D);
            hologram.spawntemp(60);
			
			if(DataUtil.getf(p, "xp") >= Util.experienceRequied(DataUtil.geti(p, "level")+1)) {
				DataUtil.increasei(p, "level", 1);
				DataUtil.increasei(p, "skillpoints", 1);
				DataUtil.set(p, "xp", 0);
				Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(p, DataUtil.geti(p, "level")));
			}
		}
	}
	
	@EventHandler
	public void onPlayerLevelUp(PlayerLevelUpEvent e) {
		Player p = e.getPlayer();
		if(e.getGrewTo() == 40 || e.getGrewTo() == 50 || e.getGrewTo() == 60) {
			Bukkit.broadcastMessage("§b"+p.getName()+"§6 hat das Level §b"+e.getGrewTo()+" §6erreicht!");
		}
		Util.displayTitleBar(p, "§aLevel Up!", "§6Benutze /stats, um deine Stats zu erhöhen", 10, 40, 10);
		Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).withFade(Color.WHITE).withColor(Color.WHITE).trail(true).build());
		meta.setPower(1);
		firework.setFireworkMeta(meta);
	}
}