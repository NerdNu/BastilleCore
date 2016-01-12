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
package com.c45y.Bastille.boss;

import com.c45y.Bastille.BastilleCore;
import com.c45y.Bastille.BastilleEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author c45y
 */
public abstract class BastilleBoss {
    protected BastilleCore _plugin;
    protected BastilleEntity _entity;
    private String _name;
    private int _exp;
    
    public BastilleBoss(BastilleCore plugin, String name) {
        this(plugin, name, 20);
    }
    
    public BastilleBoss(BastilleCore plugin, String name, int exp) {
        _plugin = plugin;
        _name = name;
        _exp = exp;
    }
        
    public String getName() {
        return _name;
    }
    
    public String getMachineName() {
        return  _name.replace(" ", "");
    }
    
    public int getExp() {
        return _exp;
    }
    
    public BastilleEntity getEntity() {
        return _entity;
    }
    
    public void patchMetadata() {
        CraftEntity e = _entity.getBukkitEntity();
        e.setMetadata("bastille.name", new FixedMetadataValue(_plugin, getMachineName()));
        e.setMetadata("bastille.exp", new FixedMetadataValue(_plugin, _exp));
    }
    
    public static String getMetaName(CraftEntity e) {
        if (e.hasMetadata("bastille.name")) {
            return e.getMetadata("bastille.name").get(0).asString();
        }
        return null;
    }
    
    public static Integer getMetaExp(CraftEntity e) {
        if (e.hasMetadata("bastille.exp")) {
            return e.getMetadata("bastille.exp").get(0).asInt();
        }
        return null;
    }
    
    /**
     * Spawn our boss mob, should be a fairly direct mapping to Bastille*.spawn
     * @param location
     */
    public abstract void spawn(Location location); 
}
