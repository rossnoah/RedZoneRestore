package net.vanillaplus.redzonerestore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    //create listener
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getPlayer()!=null){
            if(RedZoneRestore.runAtBlock(e.getBlock())){
                Block b = e.getBlock();
                if(isTargetedMaterial(b.getType())){

                    Material type = b.getType();
                    SchedulerUtils.runLater(()->{
                        if(b.getType()==Material.AIR||b.getType()==Material.FIRE||b.isEmpty()||b.getType()==Material.WATER||b.getType()==Material.LAVA){
                            b.setType(type);
                        }

                    },RedZoneRestore.getInstance().getConfig().getInt("restoredelay")*20+(int)(Math.random()*RedZoneRestore.getInstance().getConfig().getInt("restoreraterange")));
                }
            }

        }
    }


    boolean isTargetedMaterial(Material m){

        for(String s:RedZoneRestore.getInstance().getConfig().getStringList("placelist")){

            if(s.equalsIgnoreCase(m.name())){
                return true;
            }

        }

        return false;
    }

}
