/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.nametags;

import com.ngxdev.faction.nametags.hook.FactionHookManager;
import com.ngxdev.faction.nametags.hook.FactionType;
import com.ngxdev.faction.scoreboard.api.FactionHook;
import com.ngxdev.utils.console.logging.basic.Log;
import com.ngxdev.utils.minecraft.scoreboard.CustomScoreboard;
import com.ngxdev.utils.yaml.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FN extends JavaPlugin {
    public static FN inst;
    public static Config config;
    public static FactionHook hook;
    public static boolean pha = false, mvdw = false;

    public FN() {
        FN.inst = this;
    }

    @Override
    public void onEnable() {
        config = new Config("config.yml", getResource("config.yml"), this);
        if (config.getInt("version") != 2) {
            config.file.renameTo(new File(config.file.getParentFile(), "config-old.yml"));
            config = new Config("config.yml", getResource("config.yml"), this);
        }
        new FactionNameManager();
        FactionType.values();//just to init
        hook = FactionHookManager.getHook();
        if (hook == null) {
            Log.severe("No compatible hook was found, plugin will now disable");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        int update_interval = config.getInt("scoreboard.refresh-rate");
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                CustomScoreboard.updateScoreboard(player, true);
            }
        }, update_interval, update_interval);
        pha = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        if (!pha) Log.warn("PlaceholderAPI is not installed on your server, placeholders will not work");
        mvdw = Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI");
        if (!mvdw) Log.warn("MVdWPlaceholderAPI is not installed on your server, placeholders will not work");
    }

    @Override
    public void onDisable() {
        FN.inst = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("\u00a7cUnknown arguments. /nametag <info/toggle/reload>");
        } else {
            switch (args[0].toLowerCase()) {
                case "info":
                case "explain": {
                    for (FactionType type : FactionType.values()) {
                        sender.sendMessage(String.format("\u00a7%s\u2588 \u00a7f %s", type.color, type.displayname));
                    }
                    break;
                }
                case "reload": {
                    if (sender.hasPermission("factionscoreboard.reload")) {
                        config.reload();
                        FactionType.reload();
                    }
                    break;
                }
                default:
                    sender.sendMessage("\u00a7cUnknown arguments. /map <info/toggle/reload>");
            }
        }
        return true;
    }
}
