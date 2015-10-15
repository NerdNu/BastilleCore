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
package com.c45y.Bastille.command;

import com.c45y.Bastille.BastilleCore;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author c45y
 */
public class BastilleCommand implements CommandExecutor  {
    private final BastilleCore _plugin;
    
    public BastilleCommand(BastilleCore plugin) {
        _plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You don't seem to be a player, try running these commands from in game.");
            return false;
        }
        
        Player player = (Player) sender;
        
        if (!cmd.getName().equalsIgnoreCase("bastille") || !player.hasPermission("bastille.command")) {
            return false;
        }
        
        if (args.length == 0) {
            player.sendMessage("Requires args: summon, ...");
            return true;
        }
        
        /*      1      2   3   4   5
        /summon <boss> <x> <y> <z> [world] */
        if (args[0].equalsIgnoreCase("summon")) {
            if (args.length < 4) {
                _plugin.sendPlayerMessage(player, "Missing args. /summon <boss> <x> <y> <z> [world]");
                return true;
            }
            String boss = args[1];
            int x = Integer.valueOf(args[2]);
            int y = Integer.valueOf(args[3]);
            int z = Integer.valueOf(args[4]);
            World w = player.getWorld();
            if (args.length == 5) {
                w = _plugin.getServer().getWorld(args[5]);
                if (w == null) {
                    _plugin.sendPlayerMessage(player, "Invalid args. Could not find world " + args[5]);
                    return true;
                }
            }
            
            if (!_plugin.spawnBoss(boss, new Location(w, x, y, z))) {
                _plugin.sendPlayerMessage(player, "Invalid args. Could not find boss " + boss);
            }
            return true;
        }
        
        
        return false;
    }
}
