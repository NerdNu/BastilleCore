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
import com.c45y.Bastille.Entities.BastilleZombie;

import net.minecraft.server.v1_9_R1.Blocks;
import net.minecraft.server.v1_9_R1.DamageSource;
import net.minecraft.server.v1_9_R1.Enchantment;
import net.minecraft.server.v1_9_R1.EntityCreature;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EnumItemSlot;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.Items;
import net.minecraft.server.v1_9_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_9_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R1.PathfinderGoalRandomStroll;

import org.bukkit.Location;

/**
 *
 * @author c45y
 */
public class SirMeowingtons extends BastilleBoss {
    public SirMeowingtons(BastilleCore plugin) {
        super(plugin, "Sir Meowingtons", 1000);
    }

    @Override
    public void spawn(Location location) {
        _entity = new BastilleZombie(location.getWorld());
        _entity = _entity.maxhealth(2000D).health(1500F);
        _entity = _entity.ignoreDamageSource(DamageSource.BURN);
        _entity = _entity.speed(0.4F);
        _entity = _entity.damage(0.5D);

        ItemStack is = new ItemStack(Items.STICK);
        is.addEnchantment(Enchantment.b("knockback"), 6);
        _entity.setEquipment(EnumItemSlot.MAINHAND, is);
        _entity.setDropChance(EnumItemSlot.MAINHAND, 0.1F);

        ItemStack ish = new ItemStack(Blocks.PUMPKIN);
        _entity.setEquipment(EnumItemSlot.HEAD, ish);
        _entity.setDropChance(EnumItemSlot.HEAD, 0F);

        _entity.emtpyGoals();
        _entity.addGoal(0, new PathfinderGoalFloat((EntityCreature) _entity));
        _entity.addGoal(2, new PathfinderGoalMeleeAttack((EntityCreature) _entity, 1.0D, false));
        _entity.addGoal(5, new PathfinderGoalMoveTowardsRestriction((EntityCreature) _entity, 1.0D));
        _entity.addGoal(7, new PathfinderGoalRandomStroll((EntityCreature) _entity, 1.0D));
        _entity.addGoal(8, new PathfinderGoalLookAtPlayer((EntityCreature) _entity, EntityHuman.class, 8.0F));
        _entity.addGoal(8, new PathfinderGoalRandomLookaround((EntityCreature) _entity));
        _entity.emtpyTargets();
        _entity.addTarget(0, new PathfinderGoalNearestAttackableTarget((EntityCreature) _entity, EntityHuman.class, true));

        _entity.setCustomName("Sir Meowingtons");
        _entity.setCustomNameVisible(true);

        _entity.spawn(location);

        patchMetadata();
    }
}
