package io.github.jaronz.mwcommandblacklist;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class MWCommandBlacklist extends PluginBase {
    private static final int configVersion = 2;

    private PluginLogger logger;
    public static MWCommandBlacklist instance;

    @Override
    public void onLoad() {
        logger = this.getLogger();
        logger.info(TextFormat.WHITE + this.getName() + " has been loaded!");
    }

    @Override
    public void onEnable() {
        logger.info(TextFormat.DARK_GREEN + this.getName() + " has been enabled!");
        instance = this;

        saveDefaultConfig();
        Config config = getConfig();

        if(config.getInt("version") < configVersion) {
            logger.error("The config file of " + this.getName() + " is outdated, please delete the old config.yml file.");
            this.setEnabled(false);
        }

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info(TextFormat.DARK_RED + this.getName() + " has been disabled!");
    }
}
