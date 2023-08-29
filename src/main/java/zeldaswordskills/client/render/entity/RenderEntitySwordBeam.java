/**
 * Copyright (C) <2015> <coolAlias>
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

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zeldaswordskills.api.item.ArmorIndex;
import zeldaswordskills.item.ZSSItems;
import zeldaswordskills.ref.ModInfo;

@SideOnly(Side.CLIENT)
public class RenderEntitySwordBeam extends Render {

    private static final ResourceLocation texture = new ResourceLocation(
        ModInfo.ID + ":textures/entity/sword_beam.png");

    public RenderEntitySwordBeam() {
        shadowSize = 0.25F;
        shadowOpaque = 0.75F;
    }

    public void renderBeam(Entity entity, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT + GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScalef(1.5F, 0.5F, 1.5F);
        bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float rgb = getRgb(entity);
        GL11.glColor3f(rgb, rgb, rgb);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(-0.5D, -0.25D, 0.0D, 0, 1);
        tessellator.addVertexWithUV(0.5D, -0.25D, 0.0D, 1, 1);
        tessellator.addVertexWithUV(0.5D, 0.75D, 0.0D, 1, 0);
        tessellator.addVertexWithUV(-0.5D, 0.75D, 0.0D, 0, 0);
        tessellator.draw();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
        renderBeam(entity, x, y, z, yaw, partialTick);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }

    private float getRgb(Entity entity) {
        if (entity instanceof EntityThrowable) {
            EntityLivingBase thrower = ((EntityThrowable) entity).getThrower();
            if (thrower != null && thrower.getEquipmentInSlot(ArmorIndex.EQUIPPED_HELM) != null
                && thrower.getEquipmentInSlot(ArmorIndex.EQUIPPED_HELM)
                    .getItem() == ZSSItems.maskFierce) {
                return 0.0F; // nice and dark for the Fierce Diety
            }
        }
        return 1.0F;
    }
}
