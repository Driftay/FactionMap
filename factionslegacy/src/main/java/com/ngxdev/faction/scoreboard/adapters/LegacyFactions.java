/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.adapters;

import com.ngxdev.faction.scoreboard.api.FactionHook;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LegacyFactions extends FactionHook {
    @Override
    public Relation getRelation(Object f1, Object f2) throws Exception {
        if (f1 == null || f2 == null) return Relation.WILD;
        Faction pf = (Faction) f1;
        Faction of = (Faction) f2;
        String s = of.getId();
        if (s.equalsIgnoreCase("0")) return Relation.WILD;
        else if (s.equalsIgnoreCase(Objects.toString(pf.getClass().getMethod("getId").invoke(pf)))) return Relation.PLAYER;
        else if (s.equalsIgnoreCase("-2")) return Relation.WARZONE;
        else if (s.equalsIgnoreCase("-1")) return Relation.SAFEZONE;
        else if (pf.getRelationTo(of) == net.redstoneore.legacyfactions.Relation.ENEMY) return Relation.ENEMY;
        else if (pf.getRelationTo(of) == net.redstoneore.legacyfactions.Relation.ALLY) return Relation.ALLY;
        else if (pf.getRelationTo(of) == net.redstoneore.legacyfactions.Relation.TRUCE) return Relation.TRUCE;
        else if (pf.getRelationTo(of) == net.redstoneore.legacyfactions.Relation.NEUTRAL) return Relation.NEUTRAL;
        else return Relation.WILD;
    }

    @Override
    public List<String> getMap(Player p, int height, int width) throws Exception {
        List<String> ret = new ArrayList<>();
        FPlayer fPlayer = FPlayerColl.get(p);
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

                Faction fh = Board.get().getFactionAt(topLeft.getRelative(dx, dz));
                Faction pf = fPlayer.getFaction();
                Relation rel = getRelation(pf, fh);
                row.append("\u00a7").append(rel.ordinal()).append("\u2588");
            }

            String line = row.toString();
            ret.add(line);
        }

        return ret;
    }
}
