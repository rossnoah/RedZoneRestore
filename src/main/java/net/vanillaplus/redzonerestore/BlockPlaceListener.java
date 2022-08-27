package net.vanillaplus.redzonerestore;

import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){



        if(RedZoneRestore.runAtBlock(e.getBlock())){

            if(isTargetedMaterial(e.getBlock().getType())){

                Material type = e.getBlock().getType();
                SchedulerUtils.runLater(()->{
                    if(e.getBlock().getType()==type){
                        e.getBlock().setType(Material.AIR);
                        if(RedZoneRestore.getInstance().getConfig().getBoolean("removecrystals")) {

                            if (type == Material.OBSIDIAN || type == Material.BEDROCK) {
                                //for(Entity entity:e.getBlock().getWorld().getNearbyEntities(e.getBlock().getBoundingBox().expand(0,1,0))){
                                for (Entity entity : e.getBlock().getWorld().getEntities()) {
                                    if (entity instanceof EnderCrystal) {
                                        //check coordinates then remove if above
                                        // RedZoneRestore.getInstance().getLogger().info(entity.getLocation().getBlock().toString());
                                        //  RedZoneRestore.getInstance().getLogger().info("block:"+e.getBlock().getRelative(0,1,0).toString());
                                        if (entity.getLocation().getBlock().toString().equalsIgnoreCase(e.getBlock().getRelative(0, 1, 0).toString())) {
                                            entity.remove();

                                        }
                                    }

                                }
                            }
                        }
                    }

                },RedZoneRestore.getInstance().getConfig().getInt("restoredelay")*20+100);
            }


        }

    }


    boolean isTargetedMaterial(Material m){

        for(String s:RedZoneRestore.getInstance().getConfig().getStringList("removelist")){

            if(s.equalsIgnoreCase(m.name())){
                return true;
            }

        }

        return false;
    }


}
