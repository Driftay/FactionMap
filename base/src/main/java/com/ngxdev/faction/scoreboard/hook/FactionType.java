/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.hook;

import com.ngxdev.faction.scoreboard.FS;

public enum FactionType {
    WILDERNESS("wilderness", "Wilderness"),
    SAFEZONE("safezone", "Safezone"),
    WARZONE("warzone", "Warzone"),
    ALLY("ally", "Ally"),
    ENEMY("enemy", "Enemy"),
    NEUTRAL("neutral", "Neutral"),
    PLAYER("player", "You"),
    TRUCE("truce", "Truce"),
    YOU("you", "You");

    public static String replaceColors(String line) throws Exception {
        return line
                .replace("\u00a70", "\u00a7$" + WILDERNESS.color)
                .replace("\u00a71", "\u00a7$" + SAFEZONE.color)
                .replace("\u00a72", "\u00a7$" + WARZONE.color)
                .replace("\u00a73", "\u00a7$" + ALLY.color)
                .replace("\u00a74", "\u00a7$" + ENEMY.color)
                .replace("\u00a75", "\u00a7$" + NEUTRAL.color)
                .replace("\u00a76", "\u00a7$" + PLAYER.color)
                .replace("\u00a77", "\u00a7$" + TRUCE.color)
                .replace("\u00a78", "\u00a7$" + YOU.color)
                .replace("\u00a7$", "\u00a7");
    }

    public String id;
    public String displayname;
    public String color;

    public static void reload() {
        for (FactionType type : values()) {
            type.color = FS.config.getString("map-config." + type.id);
        }
    }

    FactionType(String s, String s2) {
        id = s;
        displayname = s2;
        color = FS.config.getString("map-config." + s);
    }
}
