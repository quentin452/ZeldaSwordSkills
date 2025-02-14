/**
 * Copyright (C) <2016> <coolAlias>
 * 
 * This file is part of coolAlias' Zelda Sword Skills Minecraft Mod; as such,
 * you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package zeldaswordskills.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zeldaswordskills.entity.mobs.EntityDekuBaba;
import zeldaswordskills.entity.mobs.EntityDekuBase;

@SideOnly(Side.CLIENT)
public class RenderDekuBaba extends RenderGenericLiving {

    public RenderDekuBaba(ModelBase model, float shadowSize, float scale, String texturePath) {
        super(model, shadowSize, scale, texturePath);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float partialTick) {
        super.preRenderCallback(entity, partialTick);
        if (entity instanceof EntityDekuBaba) {
            int fuse = ((EntityDekuBaba) entity).getBombTimer();
            if (fuse > 0) {
                float f1 = (fuse + partialTick) / ((float) EntityDekuBaba.FUSE_TIME - 2.0F);
                float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
                f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
                f1 *= f1;
                f1 *= f1;
                float f3 = (1.0F + f1 * 0.4F) * f2;
                float f4 = (1.0F + f1 * 0.1F) / f2;
                GL11.glScalef(f3, f4, f3);
            }
        }
    }

    @Override
    protected int getColorMultiplier(EntityLivingBase entity, float brightness, float partialTick) {
        if (entity instanceof EntityDekuBaba) {
            int fuse = ((EntityDekuBaba) entity).getBombTimer();
            if (fuse > 0) {
                float f1 = (fuse + partialTick) / ((float) EntityDekuBaba.FUSE_TIME - 2.0F);
                if ((int) (f1 * 10.0F) % 2 != 0) {
                    int i = MathHelper.clamp_int((int) (f1 * 0.2F * 255.0F), 0, 255);
                    short r = 255;
                    short g = 255;
                    short b = 255;
                    return i << 24 | r << 16 | g << 8 | b;
                }
            }
        }
        return 0;
    }

    // don't render any items
    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float partialTick) {}

    @Override
    protected float getDeathMaxRotation(EntityLivingBase entity) {
        boolean flag = (entity instanceof EntityDekuBase && ((EntityDekuBase) entity).custom_death != 0);
        return (flag ? 0.0F : super.getDeathMaxRotation(entity));
    }
}
