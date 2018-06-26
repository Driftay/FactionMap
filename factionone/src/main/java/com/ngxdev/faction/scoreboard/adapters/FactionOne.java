/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.adapters;

import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Rel;
import com.ngxdev.faction.scoreboard.api.FactionHook;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionOne extends FactionHook {
    @Override
    public FactionHook.Relation getRelation(Object f1, Object f2) throws Exception {
        if (f1 == null || f2 == null) return FactionHook.Relation.WILD;
        Faction pf = (Faction) f1;
        Faction of = (Faction) f2;

        Rel rel = pf.getRelationTo(of);

        if (of.getId().equalsIgnoreCase("0")) return FactionHook.Relation.WILD;
        else if (of.getId().equalsIgnoreCase(pf.getId())) return FactionHook.Relation.PLAYER;
        else if (of.getId().equalsIgnoreCase("-2")) return FactionHook.Relation.WARZONE;
        else if (of.getId().equalsIgnoreCase("-1")) return FactionHook.Relation.SAFEZONE;
        else if (rel == Rel.ENEMY) return FactionHook.Relation.ENEMY;
        else if (rel == Rel.ALLY) return FactionHook.Relation.ALLY;
        else if (rel == Rel.TRUCE) return FactionHook.Relation.TRUCE;
        else if (rel == Rel.NEUTRAL) return FactionHook.Relation.NEUTRAL;
        else return FactionHook.Relation.WILD;
    }

    @Override
    public List<String> getMap(Player p, int height, int width) throws Exception {
        List<String> ret = new ArrayList<>();
        FPlayer fp = FPlayers.i.get(p);
        FLocation fl = new FLocation(p.getLocation());

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        FLocation topLeft = fl.getRelative(-halfWidth, -halfHeight);


        for (int dz = 0; dz < height; dz++) {
            StringBuilder row = new StringBuilder();
            for (int dx = 0; dx < width; dx++) {
                if (dx == halfWidth && dz == halfHeight) {
                    row.append("\u00a7" + Relation.YOU.ordinal()).append("\u2588");
                    continue;
                }

                Faction fh = Board.getFactionAt(topLeft.getRelative(dx, dz));
                Faction pf = fp.getFaction();
                row.append("\u00a7").append(getRelation(fh, pf).ordinal()).append("\u2588");
            }

            String line = row.toString();
            ret.add(line);
        }

        return ret;
    }
}
