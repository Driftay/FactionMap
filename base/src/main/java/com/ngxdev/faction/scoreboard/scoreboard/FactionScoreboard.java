/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.scoreboard;

import com.ngxdev.faction.scoreboard.FS;
import com.ngxdev.faction.scoreboard.hook.FactionType;
import com.ngxdev.faction.scoreboard.placeholder.MvdwPlaceholder;
import com.ngxdev.faction.scoreboard.placeholder.PlaceholderAPI;
import com.ngxdev.utils.minecraft.scoreboard.CustomScoreboard;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionScoreboard extends CustomScoreboard {
    private Player p;
    List<String> mapCache = new ArrayList<>();
    public boolean updateMap = true;

    public FactionScoreboard(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
        String title = FS.config.getString("scoreboard.title");
        if (FS.pha) title = PlaceholderAPI.replace(p, title);
        if (FS.mvdw) title = MvdwPlaceholder.replace(p, title);
        setTitle(title);
        for (String s : FS.config.getStringList("scoreboard.lines")) {
            if (s.matches("%faction-map\\|[0-9]+,[0-9]+%")) {
                if (updateMap) {
                    mapCache.clear();
                    try {
                        String[] args = s.replace("%", "").split("\\|");
                        String[] size = args[1].split(",");
                        List<String> map = FS.hook.getMap(p, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                        for (String mapline : map) {
                            String mapEntry = FactionType.replaceColors(mapline);
                            addLine(mapEntry);
                            mapCache.add(mapEntry);
                        }
                        updateMap = false;
                    } catch (Exception e) {
                        addLine("MapCreator failed");
                        e.printStackTrace();
                    }
                } else {
                    for (String mapEntry : mapCache) addLine(mapEntry);
                }
                continue;
            }
            if (FS.pha) s = PlaceholderAPI.replace(p, s);
            if (FS.mvdw) s = MvdwPlaceholder.replace(p, s);
            addLine(s);
        }
    }
}
