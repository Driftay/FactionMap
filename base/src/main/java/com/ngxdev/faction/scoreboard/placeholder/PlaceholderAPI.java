/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.placeholder;

import org.bukkit.entity.Player;

public class PlaceholderAPI {
    public static String replace(Player player, String text) {
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
    }
}
