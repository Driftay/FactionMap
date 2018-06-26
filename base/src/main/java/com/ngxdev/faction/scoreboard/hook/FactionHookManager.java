/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.hook;

import com.ngxdev.faction.scoreboard.adapters.FactionOne;
import com.ngxdev.faction.scoreboard.adapters.FactionUUID;
import com.ngxdev.faction.scoreboard.adapters.FactionsV2;
import com.ngxdev.faction.scoreboard.adapters.LegacyFactions;
import com.ngxdev.faction.scoreboard.api.FactionHook;
import com.ngxdev.utils.console.logging.basic.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum FactionHookManager {
    FACTIONS_UUID("1\\.6\\.9\\.5-U.*", FactionUUID.class),
    FACTIONS_ONE("1\\.[0-9]+\\.[0-9]+.*", FactionOne.class),
    FACTIONS_V2("2\\.[0-9]+\\.[0-9]+.*", FactionsV2.class),;

    public String name;
    public Class<? extends FactionHook> clazz;

    FactionHookManager(String name, Class<? extends FactionHook> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static FactionHook getHook() {
        Plugin legacy = Bukkit.getPluginManager().getPlugin("LegacyFactions");
        if (legacy == null) {
            Plugin pl = Bukkit.getPluginManager().getPlugin("Factions");
            if (pl == null) return null;
            Log.info("Faction version detected: " + pl.getDescription().getVersion());
            for (FactionHookManager hook : values()) {
                if (pl.getDescription().getVersion().matches(hook.name)) {
                    try {
                        Log.info("Using hook: " + hook.name());
                        return hook.clazz.newInstance();
                    } catch (Exception e) {
                        Log.warn("Found plugin hook " + hook.name + " but failed to init it");
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                return LegacyFactions.class.newInstance();
            } catch (Exception e) {
                Log.warn("Found plugin hook LegacyFactions but failed to init it");
                e.printStackTrace();
            }
        }
        return null;
    }
}