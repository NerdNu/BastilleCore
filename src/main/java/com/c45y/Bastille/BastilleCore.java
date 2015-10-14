/*
 * The MIT License
 *
 * Copyright 2015 c45y.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.c45y.Bastille;

import com.c45y.Bastille.command.BastilleCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BastilleCore extends JavaPlugin {
    private static BastilleCore _instance;
    private BastilleListener _listener;
    
    public final ChatColor _pluginColor = ChatColor.GREEN;
    
    public static BastilleCore getInstance() {
        return _instance;
    }    

    
    @Override
    public void onEnable() {
        /* Create a static reference to ourself */
        _instance = this;
        
        /* Register our listener(s) */
        _listener = new BastilleListener(this);
        getServer().getPluginManager().registerEvents(_listener, this);
        
        getCommand("bastille").setExecutor(new BastilleCommand(this));
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
    
    /**
     * Player communication function, uses our defined 'plugin color'
     * @param player
     * @param message
     */
    public void sendPlayerMessage(Player player, String message) {
        player.sendMessage(_pluginColor + message);
    }
    
    /**
     * Pass through for a notifying hasPermission call
     * @param player
     * @param permission
     * @return
     */
    public boolean hasPermission(Player player, String permission) {
        return hasPermission(player, permission, true);
    }
    
    /**
     * Check if a player has a given permission node, in the event they don't possibly notify them
     * @param player
     * @param permission
     * @param notify
     * @return
     */
    public boolean hasPermission(Player player, String permission, boolean notify) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            if (notify) {
                sendPlayerMessage(player, "You are missing the " + ChatColor.YELLOW + permission + _pluginColor + " permission.");
            }
            return false;
        }
    }
}
