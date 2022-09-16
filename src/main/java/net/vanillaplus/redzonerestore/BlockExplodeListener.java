package net.vanillaplus.redzonerestore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockExplodeListener implements Listener {


    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        for(Block b:e.blockList()){
            if(RedZoneRestore.runAtBlock(b)){

                if(isTargetedMaterial(b.getType())){

                    Material type = b.getType();
                    SchedulerUtils.runLater(()->{
                        if(b.getType()==Material.AIR||b.getType()==Material.FIRE||b.isEmpty()||b.getType()==Material.WATER||b.getType()==Material.LAVA){
                            if(type==Material.SAND){
                                int down = -1;
                                if(b.getRelative(0,-1,0).getType()==Material.AIR){

                                }


                            }else {
                                b.setType(type);
                            }
                        }

                    },RedZoneRestore.getInstance().getConfig().getInt("restoredelay")*20+(int)(Math.random()*RedZoneRestore.getInstance().getConfig().getInt("restoreraterange")));
                }


            }



        }
    }


    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for(Block b:e.blockList()){
            if(RedZoneRestore.runAtBlock(b)){

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
