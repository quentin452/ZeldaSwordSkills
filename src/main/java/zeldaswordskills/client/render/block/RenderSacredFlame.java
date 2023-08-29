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

package zeldaswordskills.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import zeldaswordskills.block.BlockSacredFlame;

public class RenderSacredFlame implements ISimpleBlockRenderingHandler {

    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();

    public RenderSacredFlame() {}

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block parBlock, int modelId,
        RenderBlocks renderer) {
        if (!(parBlock instanceof BlockSacredFlame)) {
            return false;
        }
        BlockSacredFlame block = (BlockSacredFlame) parBlock;
        Tessellator tessellator = Tessellator.instance;
        int meta = world.getBlockMetadata(x, y, z);
        IIcon icon = block.getFireIcon(0, meta);
        IIcon icon1 = block.getFireIcon(1, meta);
        /*
         * switch(meta) {
         * case 0x1: tessellator.setColorOpaque_F(1.0F, 0.188F, 0.188F); break;
         * case 0x2: tessellator.setColorOpaque_F(0.0F, 0.788F, 0.341F); break;
         * case 0x4: tessellator.setColorOpaque_F(0.255F, 0.412F, 0.882F); break;
         * default: tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
         * }
         */
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        double minU = icon.getMinU();
        double minV = icon.getMinV();
        double maxU = icon.getMaxU();
        double maxV = icon.getMaxV();
        double dy = y + 1.4F;
        double dx1 = x + 0.5D + 0.2D;
        double dx2 = x + 0.5D - 0.2D;
        double dz1 = z + 0.5D + 0.2D;
        double dz2 = z + 0.5D - 0.2D;
        double dx3 = x + 0.5D - 0.3D;
        double dx4 = x + 0.5D + 0.3D;
        double dz3 = z + 0.5D - 0.3D;
        double dz4 = z + 0.5D + 0.3D;
        tessellator.addVertexWithUV(dx3, dy, (z + 1), maxU, minV);
        tessellator.addVertexWithUV(dx1, y, (z + 1), maxU, maxV);
        tessellator.addVertexWithUV(dx1, y, z, minU, maxV);
        tessellator.addVertexWithUV(dx3, dy, z, minU, minV);
        tessellator.addVertexWithUV(dx4, dy, z, maxU, minV);
        tessellator.addVertexWithUV(dx2, y, z, maxU, maxV);
        tessellator.addVertexWithUV(dx2, y, (z + 1), minU, maxV);
        tessellator.addVertexWithUV(dx4, dy, (z + 1), minU, minV);
        minU = icon1.getMinU();
        minV = icon1.getMinV();
        maxU = icon1.getMaxU();
        maxV = icon1.getMaxV();
        tessellator.addVertexWithUV((x + 1), dy, dz4, maxU, minV);
        tessellator.addVertexWithUV((x + 1), y, dz2, maxU, maxV);
        tessellator.addVertexWithUV(x, y, dz2, minU, maxV);
        tessellator.addVertexWithUV(x, dy, dz4, minU, minV);
        tessellator.addVertexWithUV(x, dy, dz3, maxU, minV);
        tessellator.addVertexWithUV(x, y, dz1, maxU, maxV);
        tessellator.addVertexWithUV((x + 1), y, dz1, minU, maxV);
        tessellator.addVertexWithUV((x + 1), dy, dz3, minU, minV);
        dx1 = x + 0.5D - 0.5D;
        dx2 = x + 0.5D + 0.5D;
        dz1 = z + 0.5D - 0.5D;
        dz2 = z + 0.5D + 0.5D;
        dx3 = x + 0.5D - 0.4D;
        dx4 = x + 0.5D + 0.4D;
        dz3 = z + 0.5D - 0.4D;
        dz4 = z + 0.5D + 0.4D;
        tessellator.addVertexWithUV(dx3, dy, z, minU, minV);
        tessellator.addVertexWithUV(dx1, y, z, minU, maxV);
        tessellator.addVertexWithUV(dx1, y, (z + 1), maxU, maxV);
        tessellator.addVertexWithUV(dx3, dy, (z + 1), maxU, minV);
        tessellator.addVertexWithUV(dx4, dy, (z + 1), minU, minV);
        tessellator.addVertexWithUV(dx2, y, (z + 1), minU, maxV);
        tessellator.addVertexWithUV(dx2, y, z, maxU, maxV);
        tessellator.addVertexWithUV(dx4, dy, z, maxU, minV);
        minU = icon.getMinU();
        minV = icon.getMinV();
        maxU = icon.getMaxU();
        maxV = icon.getMaxV();
        tessellator.addVertexWithUV(x, dy, dz4, minU, minV);
        tessellator.addVertexWithUV(x, y, dz2, minU, maxV);
        tessellator.addVertexWithUV((x + 1), y, dz2, maxU, maxV);
        tessellator.addVertexWithUV((x + 1), dy, dz4, maxU, minV);
        tessellator.addVertexWithUV((x + 1), dy, dz3, minU, minV);
        tessellator.addVertexWithUV((x + 1), y, dz1, minU, maxV);
        tessellator.addVertexWithUV(x, y, dz1, maxU, maxV);
        tessellator.addVertexWithUV(x, dy, dz3, maxU, minV);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
