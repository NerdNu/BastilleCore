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

import com.c45y.Bastille.boss.*;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author c45y
 */
public class BastilleListener implements Listener {
    private final BastilleCore _plugin;
    
    public BastilleListener(BastilleCore plugin) {
        _plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != SpawnReason.NATURAL) {
            return;
        }
        
        BastilleBoss boss;
        
        switch (_plugin.getRandomChance()) {
            case 0: 
                boss = new SirMeowingtons(_plugin);
                break;
            case 1:
                boss = new LordPuggleston(_plugin);
                break;
            case 2:
                boss = new DrCuddles(_plugin);
                break;
            case 3:
                boss = new MrSkeletal(_plugin);
                break;
            default:
                return;
        }
        
        Location location = event.getEntity().getLocation();
        _plugin.getLogger().log(Level.INFO, "A boss mob has been spawned | {0} at {1} {2} {3}", new Object[] {boss.getName(), location.getX(), location.getY(), location.getZ()});
        boss.spawn(location);
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {       
        if (event.getEntity() == null || event.getEntity().getCustomName() == null) {
            return;
        }
        String customName = BastilleBoss.getMetaName((CraftEntity) event.getEntity());
        if (customName == null) {
            return;
        }
       
        BastilleBoss boss = _plugin.getBoss(customName);
        if (boss != null) {
            System.out.println(customName + " found! Setting exp to: " + boss.getExp());
            event.setDroppedExp(boss.getExp());
        }
    }
}
