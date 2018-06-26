/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.nametags.hook;

import com.ngxdev.faction.nametags.FN;
import com.ngxdev.faction.scoreboard.api.FactionHook;

public enum FactionType {
    WILD(FactionHook.Relation.WILD, "wilderness", "Wilderness"),
    SAFEZONE(FactionHook.Relation.SAFEZONE, "safezone", "Safezone"),
    WARZONE(FactionHook.Relation.WARZONE, "warzone", "Warzone"),
    ALLY(FactionHook.Relation.ALLY, "ally", "Ally"),
    ENEMY(FactionHook.Relation.ENEMY, "enemy", "Enemy"),
    NEUTRAL(FactionHook.Relation.NEUTRAL, "neutral", "Neutral"),
    PLAYER(FactionHook.Relation.PLAYER, "player", "You"),
    TRUCE(FactionHook.Relation.TRUCE, "truce", "Truce"),
    YOU(FactionHook.Relation.YOU, "you", "You");

    public FactionHook.Relation rel;
    public String id;
    public String displayname;
    public String color;

    public static void reload() {
        for (FactionType type : values()) {
            type.color = FN.config.getString("name-config." + type.id);
        }
    }

    public static FactionType getRelation(FactionHook.Relation other) {
        for (FactionType rel : FactionType.values()) {
            if (other == rel.rel) return rel;
        }
        return WILD;
    }

    FactionType(FactionHook.Relation rel, String s, String s2) {
        this.rel = rel;
        this.id = s;
        this.displayname = s2;
        this.color = FN.config.getString("name-config." + s);
    }
}
