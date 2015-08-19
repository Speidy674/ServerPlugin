package de.neon.serverplugin.hologram;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.neon.serverplugin.command.DuelCommand;

public class Hologram {
    
    String Text = "";
    double Height = 0;
    Location loc = null;
    ArmorStand Hologram = null;
    List<String> lines = new ArrayList<String>();
    ArrayList<Entity> holos = new ArrayList<Entity>();
   
   
    public Hologram(Location location,String Text,double Height){
            this.Text = Text;
            this.loc = location;
            this.Height = Height;
    }
   
    public Hologram(Location location,List<String> lines,double Height){
            this.lines = lines;
            this.loc = location;
            this.Height = Height;
    }
   
    public Hologram(){
    }
   
    public void spawn(){
  if(lines.size() == 0){
            this.loc.setY((this.loc.getY() + this.Height)-1.25);
            Hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            Hologram.setCustomName(this.Text);
            Hologram.setCustomNameVisible(true);
            Hologram.setGravity(false);
            Hologram.setVisible(false);
            }else{
                    if(lines.size() == 1){
                            spawn();
                            return;
                    }
                    if(lines.size() > 1){
                            this.loc.setY((this.loc.getY() + this.Height)-1.25);
                            for(int i = lines.size();i>0;i--){
                            final ArmorStand Hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                            holos.add(Hologram);
                            Hologram.setCustomName(lines.get(i-1));
                            Hologram.setCustomNameVisible(true);
                            Hologram.setGravity(false);
                            Hologram.setVisible(false);
                            this.loc.setY(this.loc.getY()+0.25);
                            }
                    }
            }
    }

    public void spawntemp(int Time){
             if(lines.size() == 0){
            this.loc.setY((this.loc.getY() + this.Height)-1.25);
            Hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            Hologram.setCustomName(this.Text);
            Hologram.setCustomNameVisible(true);
            Hologram.setGravity(false);
            Hologram.setVisible(false);

            Bukkit.getScheduler().runTaskLater(DuelCommand.plugin, new Runnable() {
                    @Override
                    public void run() {
                            remove();
                    }
            }, Time);
             }else{
                     if(lines.size() == 1){
                             lines.clear();
                             spawntemp(Time);
                                    return;
                            }
                           
                            if(lines.size() > 1){
                                    this.loc.setY((this.loc.getY() + this.Height)-1.25);
                                    for(int i = lines.size();i>0;i--){
                                    final ArmorStand Hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                                    Hologram.setCustomName(lines.get(i-1));
                                    Hologram.setCustomNameVisible(true);
                                    Hologram.setGravity(false);
                                    Hologram.setVisible(false);
                                    this.loc.setY(this.loc.getY()+0.25);
                                    Bukkit.getScheduler().runTaskLater(DuelCommand.plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                    Hologram.remove();
                                            }
                                    }, Time);
                                    }
                            }
             }
    }
   
    public void remove(){
            if(ifHologram()){
                    Hologram.remove();
            }
            if(lines.size() > 0){
                    for(Entity entity : holos){
                            entity.remove();
                    }
            }
    }
   
    public void teleport(Location location){
            if(ifHologram()){
            Hologram.teleport(location);
            }
    }
   
    public void changeText(String Text){
            if(ifHologram()){
            Hologram.setCustomName(this.Text);
            }
    }
   
    public void setText(String Text){
    this.Text = Text;
    }
   
    public void setLines(List<String> lines){
    this.lines = lines;
    }
   
    public void setLocation(Location location){
    this.loc = location;
    }
   
    public void setHeight(double Height){
    this.Height = Height;
    }

    public Boolean ifHologram(){
            if(this.Hologram != null){
            return true;
            }else{
            return false;
            }
    }
}
