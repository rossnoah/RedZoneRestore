package net.vanillaplus.redzonerestore;

import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onCrystalSpawn (EntitySpawnEvent e){
        if(!RedZoneRestore.getInstance().getConfig().getBoolean("removecrystals")){
            return;
        }



        if(e.getEntity() instanceof EnderCrystal){



            if(RedZoneRestore.runAtBlock(e.getLocation().getBlock())){


                    SchedulerUtils.runLater(()->{
                        if(!e.getEntity().isDead()){
                            e.getEntity().remove();
                        }

                    },RedZoneRestore.getInstance().getConfig().getInt("restoredelay")*20);
                }


            }

        }

}
