/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.adapters;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Board;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.RelationUtil;
import com.massivecraft.massivecore.ps.PS;
import com.ngxdev.faction.scoreboard.api.FactionHook;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionsV2 extends FactionHook {
    @Override
    public Relation getRelation(Object f1, Object f2) throws Exception {
        if (f1 == null || f2 == null) return Relation.WILD;
        Faction pf = (Faction) f1;
        Faction of = (Faction) f2;
        Rel rel = RelationUtil.getRelationOfThatToMe(pf, of);
        if (of.isNone()) return Relation.WILD;
        else if (pf == of) return Relation.PLAYER;
        else if (Faction.get("warzone") == of) return Relation.WARZONE;
        else if (Faction.get("safezone") == of) return Relation.SAFEZONE;
        else if (RelationUtil.getRelationOfThatToMe(pf, of) == Rel.ENEMY) return Relation.ENEMY;
        else if (rel == Rel.ALLY) return Relation.ALLY;
        else if (rel == Rel.TRUCE) return Relation.TRUCE;
        else if (rel == Rel.NEUTRAL) return Relation.NEUTRAL;
        else return Relation.WILD;
    }

    public List<String> getMap(Player p, int width, int height) throws Exception {
        Board b = BoardColl.get().get(p.getWorld());
        PS cp = PS.valueOf(p).getChunkCoords(true);

        List<String> ret = new ArrayList<>();
        Faction pf = MPlayer.get(p).getFaction();

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        PS flp = cp.plusChunkCoords(-halfWidth, -halfHeight);

        for (int dz = 0; dz < height; dz++) {
            StringBuilder row = new StringBuilder();
            for (int dx = 0; dx < width; dx++) {
                if (dx == halfWidth && dz == halfHeight) {
                    row.append("\u00a7" + Relation.YOU.ordinal()).append("\u2588");
                    continue;
                }

                PS hp = flp.plusChunkCoords(dx, dz);
                Faction hf = b.getFactionAt(hp);
                row.append("\u00a7").append(getRelation(pf, hf).ordinal()).append("\u2588");
            }

            String line = row.toString();
            ret.add(line);
        }

        return ret;
    }
}
