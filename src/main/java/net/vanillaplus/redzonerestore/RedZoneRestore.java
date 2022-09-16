package net.vanillaplus.redzonerestore;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class RedZoneRestore extends JavaPlugin {

    public static Plugin plugin;

    private static StateFlag REDZONERESTORE_ALLOWED_FLAG;

    public static StateFlag getRedZoneRestoreAllowedFlag() {
        return REDZONERESTORE_ALLOWED_FLAG;
    }
    @Override
    public void onEnable() {
        plugin = this;
        SchedulerUtils.setPlugin(this);
        saveDefaultConfig();
        updateConfig();

        this.getCommand("rzrreload").setExecutor(new ReloadCommand());


        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(),this);
        Bukkit.getPluginManager().registerEvents(new BlockExplodeListener(),this);
      //  Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(),this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(),this);

    }


    @Override
    public void onLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        // Register Flag
        try {
            // Create the redzone restore flag.
            StateFlag flag = new StateFlag("redzonerestore", false);
            registry.register(flag);
            REDZONERESTORE_ALLOWED_FLAG = flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("town-creation");

            // Happens if a flag with the same name exists.
            if (existing instanceof StateFlag) {
                REDZONERESTORE_ALLOWED_FLAG = (StateFlag) existing;
                getLogger().warning("Found WorldGuard Flag with that matches, overriding it.");
            } else {
                getLogger().warning("RedZoneRestore WorldGuard Flag could not be created:\n" + e.getMessage());
            }
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance(){
        return plugin;
    }


    public Configuration cfg = this.getConfig().getDefaults();
    public void updateConfig() {
        try {
            if(new File(getDataFolder() + "/config.yml").exists()) {
                boolean changesMade = false;
                YamlConfiguration tmp = new YamlConfiguration();
                tmp.load(getDataFolder() + "/config.yml");
                for(String str : cfg.getKeys(true)) {
                    if(!tmp.getKeys(true).contains(str)) {
                        tmp.set(str, cfg.get(str));
                        changesMade = true;
                    }
                }
                if(changesMade)
                    tmp.save(getDataFolder() + "/config.yml");
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static boolean runAtBlock(Block b) {
        // Convert properties into WorldGuard compatible objects.
        BlockVector3 loc = BukkitAdapter.asBlockVector(b.getLocation());
        World world = BukkitAdapter.adapt(b.getWorld());


        // Get the regions.
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);

        // If there are no regions don't bother.
        if (regions == null) {
            return false;
        }

        // Get the regions of the block
        ApplicableRegionSet set = regions.getApplicableRegions(loc);

        // Check the flag.
        if (set.testState(null,RedZoneRestore.getRedZoneRestoreAllowedFlag())) {
            return true;
        }else{
            return false;
        }
    }


}
