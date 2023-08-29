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

package zeldaswordskills.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zeldaswordskills.ref.ModInfo;

public class BlockTargetDirectional extends BlockTarget {

    @SideOnly(Side.CLIENT)
    private IIcon iconFace;

    public BlockTargetDirectional(Material material) {
        super(material);
    }

    @Override
    public Result canGrabBlock(HookshotType type, World world, int x, int y, int z, int side) {
        return ((side + 1) == world.getBlockMetadata(x, y, z) ? Result.ALLOW : Result.DENY);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return side + 1;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        // add 1 to all meta values so 0 can flag block in inventory (original: 2, 5, 3, 4)
        int face = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        byte meta = (byte) (face == 0 ? 3 : face == 1 ? 6 : face == 2 ? 4 : 5);
        if (entity.rotationPitch < -45.0F) {
            meta = 1;
        } else if (entity.rotationPitch > 45.0F) {
            meta = 2;
        }
        world.setBlockMetadataWithNotify(x, y, z, meta, 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta == 0 ? (side == 1 ? iconFace : blockIcon) : ((side + 1) != meta ? blockIcon : iconFace);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon(ModInfo.ID + ":" + getUnlocalizedName().substring(9) + "_side");
        iconFace = register.registerIcon(ModInfo.ID + ":" + getUnlocalizedName().substring(9) + "_face");
    }
}
