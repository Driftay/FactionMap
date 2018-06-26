/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.nametags;

import com.ngxdev.faction.nametags.hook.FactionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class FactionNameManager implements Runnable {
    public FactionNameManager() {
        Bukkit.getScheduler().runTaskTimer(FN.inst, this, 0, 20);
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                Scoreboard sb = p.getScoreboard();
                if (sb == null) {
                    sb = Bukkit.getScoreboardManager().getNewScoreboard();
                    p.setScoreboard(sb);
                }
                Objective obj = sb.getObjective("names");
                if (obj == null) sb.registerNewObjective("names", "dummy");
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    if (p == p2) continue;
                    FactionType color = FactionType.getRelation(FN.hook.getRelation(p, p2));
                    Team t = sb.getTeam(p2.getName());
                    if (t == null) t = sb.registerNewTeam(p2.getName());
                    t.setPrefix("\u00a7" + color.color);
                    t.addPlayer(p2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
