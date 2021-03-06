/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
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
package org.spongepowered.mod.mixin.block.data;

import com.google.common.base.Optional;
import net.minecraft.inventory.IInventory;
import net.minecraft.potion.Potion;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityLockable;

import org.spongepowered.api.block.data.Beacon;
import org.spongepowered.api.potion.PotionEffectType;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@NonnullByDefault
@Implements(@Interface(iface = Beacon.class, prefix = "beacon$"))
@Mixin(net.minecraft.tileentity.TileEntityBeacon.class)
public abstract class MixinTileEntityBeacon extends TileEntityLockable implements IUpdatePlayerListBox, IInventory {

    @Override
    @Shadow
    public abstract int getField(int id);

    @Override
    @Shadow
    public abstract void setField(int id, int value);

    public Optional<PotionEffectType> beacon$getPrimaryEffect() {
        return Optional.fromNullable((PotionEffectType) Potion.potionTypes[getField(1)]);
    }

    public void beacon$setPrimaryEffect(PotionEffectType effect) {
        setField(1, ((Potion) effect).getId());
    }

    public Optional<PotionEffectType> beacon$getSecondaryEffect() {
        return Optional.fromNullable((PotionEffectType) Potion.potionTypes[getField(2)]);
    }

    void beacon$setSecondaryEffect(PotionEffectType effect) {
        setField(2, ((Potion) effect).getId());
    }

    void beacon$clearEffects() {
        setField(1, 0);
        setField(2, 0);
    }

    int beacon$getCompletedLevels() {
        return getField(0);
    }

}
