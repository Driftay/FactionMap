/*
 * Copyright (c) 2018 NGXDEV. Licensed under MIT.
 */

package com.ngxdev.faction.scoreboard.placeholder;

import org.bukkit.entity.Player;

public class MvdwPlaceholder {
    public static String replace(Player player, String text) {
        return be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, text);
    }
}
