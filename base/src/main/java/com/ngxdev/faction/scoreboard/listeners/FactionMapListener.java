/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.listeners;

import com.ngxdev.faction.scoreboard.FS;
import com.ngxdev.faction.scoreboard.scoreboard.FactionScoreboard;
import com.ngxdev.utils.minecraft.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionMapListener implements Listener {
    public FactionMapListener() {
        Bukkit.getPluginManager().registerEvents(this, FS.inst);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        if (FS.config.getBoolean("default-state")) CustomScoreboard.setScoreboard(e.getPlayer(), new FactionScoreboard(e.getPlayer()), true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!e.getFrom().getChunk().equals(e.getTo().getChunk())) {
            Bukkit.getScheduler().runTask(FS.inst, () -> {
                CustomScoreboard sb = CustomScoreboard.getScoreboard(e.getPlayer());
                if (sb != null && sb instanceof FactionScoreboard) {
                    ((FactionScoreboard)sb).updateMap = true;
                    CustomScoreboard.updateScoreboard(e.getPlayer(), true);
                }
            });
        }
    }
}

