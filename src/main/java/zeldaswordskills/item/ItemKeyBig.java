/**
 * Copyright (C) <2018> <coolAlias>
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

package zeldaswordskills.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zeldaswordskills.api.item.IUnenchantable;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.ref.ModInfo;
import zeldaswordskills.util.BossType;
import zeldaswordskills.util.MerchantRecipeHelper;
import zeldaswordskills.util.PlayerUtils;

/**
 * 
 * Big Keys come in eight types for each of the eight typs of Boss Doors.
 *
 */
public class ItemKeyBig extends Item implements IUnenchantable {

    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemKeyBig() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(ZSSCreativeTabs.tabKeys);
    }

    /**
     * Returns the appropriate big key for biome found at given coordinates, or null
     */
    public static ItemStack getKeyForBiome(World world, int x, int z) {
        BossType type = BossType.getBossType(world, x, z);
        if (type != null) {
            return new ItemStack(ZSSItems.keyBig, 1, type.ordinal());
        } else {
            return null;
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!player.worldObj.isRemote && entity.getClass() == EntityVillager.class) {
            EntityVillager villager = (EntityVillager) entity;
            MerchantRecipeList trades = villager.getRecipes(player);
            if (trades != null) {
                MerchantRecipe trade = new MerchantRecipe(stack.copy(), new ItemStack(Items.emerald, 16));
                if (player.worldObj.rand.nextFloat() < 0.2F && MerchantRecipeHelper.addToListWithCheck(trades, trade)) {
                    PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.generic.sell.0");
                } else {
                    PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.generic.sorry.1");
                }
            } else {
                PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.generic.sorry.0");
            }
        }
        return true;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return StatCollector.translateToLocalFormatted(
            getUnlocalizedName() + ".name",
            BossType.values()[stack.getItemDamage() % BossType.values().length].getDisplayName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName().substring(9) + stack.getItemDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (BossType boss : BossType.values()) {
            list.add(new ItemStack(item, 1, boss.ordinal()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return iconArray[damage % BossType.values().length];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        iconArray = new IIcon[BossType.values().length];
        for (int i = 0; i < iconArray.length; ++i) {
            iconArray[i] = register.registerIcon(ModInfo.ID + ":key_" + BossType.values()[i].getUnlocalizedName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(StatCollector.translateToLocal("tooltip.zss.keybig.desc.0"));
    }
}
