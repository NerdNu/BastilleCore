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
import com.c45y.Bastille.Entities.BastilleCreeper;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSwell;
import org.bukkit.Location;

/**
 *
 * @author c45y
 */
public class DrCuddles extends BastilleBoss {
    
    public DrCuddles(BastilleCore plugin) {
        super(plugin, "Dr Cuddles");
    }

    @Override
    public void spawn(Location location) {
        _entity = new BastilleCreeper(location.getWorld());
        _entity = _entity.maxhealth(60D).health(60F);
        _entity = _entity.ignoreDamageSource(DamageSource.LAVA);
        _entity = _entity.speed(0.4F);
        _entity = _entity.damage(0D);
        
        try {
            Field field = EntityCreeper.class.getDeclaredField("explosionRadius");
            field.setAccessible(true);
            field.setInt(_entity, 12);
        } catch (Exception e) {}
        
        try {
            Field field = EntityCreeper.class.getDeclaredField("maxFuseTicks");
            field.setAccessible(true);
            field.setInt(_entity, 8);
        } catch (Exception e) {}
                
        _entity.emtpyGoals();
        _entity.addGoal(1, new PathfinderGoalFloat((EntityCreature) _entity));
        _entity.addGoal(2, new PathfinderGoalSwell((EntityCreeper) _entity));
        _entity.addGoal(4, new PathfinderGoalLookAtPlayer((EntityCreature) _entity, EntityHuman.class, 8.0F));
        _entity.addGoal(6, new PathfinderGoalRandomLookaround((EntityCreature) _entity));
        _entity.emtpyTargets();
        _entity.addTarget(0, new PathfinderGoalNearestAttackableTarget((EntityCreature) _entity, EntityHuman.class, true));
        
        _entity.setCustomName("Dr Cuddles");
        _entity.setCustomNameVisible(true);
        
        _entity.setExpToDrop(300);
        
        _entity.spawn(location);
    }
}
