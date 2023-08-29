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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import zeldaswordskills.ZSSAchievements;
import zeldaswordskills.ZSSMain;
import zeldaswordskills.api.entity.EnumVillager;
import zeldaswordskills.api.item.IUnenchantable;
import zeldaswordskills.api.item.IWeapon;
import zeldaswordskills.api.item.WeaponRegistry;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.entity.npc.EntityGoron;
import zeldaswordskills.entity.player.ZSSPlayerSkills;
import zeldaswordskills.ref.ModInfo;
import zeldaswordskills.skills.SkillBase;
import zeldaswordskills.util.MerchantRecipeHelper;
import zeldaswordskills.util.PlayerUtils;

/**
 * 
 * Broken version of each sword; can only be repaired by a blacksmith.
 *
 */
@Optional.Interface(iface = "mods.battlegear2.api.weapons.IBattlegearWeapon", modid = "battlegear2", striprefs = true)
public class ItemBrokenSword extends Item implements IUnenchantable, IWeapon, IBattlegearWeapon {

    public ItemBrokenSword() {
        super();
        setFull3D();
        setMaxDamage(0);
        setMaxStackSize(1);
        setHasSubtypes(true);
        setCreativeTab(ZSSCreativeTabs.tabCombat);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityVillager && !player.worldObj.isRemote) {
            boolean isGoron = (entity instanceof EntityGoron);
            EntityVillager villager = (EntityVillager) entity;
            MerchantRecipeList trades = villager.getRecipes(player);
            Item brokenItem = Item.getItemById(stack.getItemDamage());
            if (!(brokenItem instanceof ItemSword)
                || (brokenItem instanceof ItemZeldaSword && !((ItemZeldaSword) brokenItem).givesBrokenItem)) {
                ZSSMain.logger
                    .warn("Broken sword contained an invalid item: " + brokenItem + "; defaulting to Ordon Sword");
                brokenItem = ZSSItems.swordOrdon;
            }
            if (EnumVillager.BLACKSMITH.is(villager) || isGoron) {
                if (brokenItem != ZSSItems.swordGiant) {
                    PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.broken");
                    MerchantRecipeHelper.addToListWithCheck(
                        trades,
                        new MerchantRecipe(stack.copy(), new ItemStack(Items.emerald, 5), new ItemStack(brokenItem)));
                } else if (isGoron && villager.getCustomNameTag()
                    .equals("Medigoron")) {
                        if (ZSSPlayerSkills.get(player)
                            .getSkillLevel(SkillBase.bonusHeart) > 9) {
                            player.triggerAchievement(ZSSAchievements.swordBroken);
                            PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.broken.giant.1");
                            PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.broken.giant.2");
                            MerchantRecipeHelper.addToListWithCheck(
                                trades,
                                new MerchantRecipe(
                                    stack.copy(),
                                    new ItemStack(Items.emerald, 5),
                                    new ItemStack(brokenItem)));
                        } else {
                            PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.big");
                            PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.later");
                        }
                    } else {
                        PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.sorry");
                    }
            } else {
                PlayerUtils.sendTranslatedChat(player, "chat.zss.trade.sword.sorry");
            }

            return true;
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        Item sword = Item.getItemById(damage);
        if (sword instanceof ItemZeldaSword && ((ItemZeldaSword) sword).givesBrokenItem) {
            return sword.getIconFromDamage(-1); // -1 returns brokenIcon for ItemZeldaSword
        } else {
            return itemIcon;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Item sword = Item.getItemById(stack.getItemDamage());
        String name = (sword instanceof ItemZeldaSword && ((ItemZeldaSword) sword).givesBrokenItem)
            ? sword.getUnlocalizedName()
            : ZSSItems.swordOrdon.getUnlocalizedName();
        return StatCollector
            .translateToLocalFormatted(getUnlocalizedName() + ".name", StatCollector.translateToLocal(name + ".name"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, Item.getIdFromItem(ZSSItems.swordKokiri)));
        list.add(new ItemStack(item, 1, Item.getIdFromItem(ZSSItems.swordOrdon)));
        list.add(new ItemStack(item, 1, Item.getIdFromItem(ZSSItems.swordGiant)));
        list.add(new ItemStack(item, 1, Item.getIdFromItem(ZSSItems.swordDarknut)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("tooltip.zss.sword_broken.desc.0"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(ModInfo.ID + ":broken_sword_ordon");
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(
            SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
            new AttributeModifier(field_111210_e, "Weapon modifier", 2.0D, 0));
        return multimap;
    }

    @Override
    public boolean isSword(ItemStack stack) {
        return !WeaponRegistry.INSTANCE.isSwordForbidden(this);
    }

    @Override
    public boolean isWeapon(ItemStack stack) {
        return !WeaponRegistry.INSTANCE.isWeaponForbidden(this);
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean sheatheOnBack(ItemStack stack) {
        return false;
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean isOffhandHandDual(ItemStack stack) {
        return true;
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean offhandAttackEntity(OffhandAttackEvent event, ItemStack main, ItemStack offhand) {
        return true;
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean offhandClickAir(PlayerInteractEvent event, ItemStack main, ItemStack offhand) {
        return true;
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack main, ItemStack offhand) {
        return true;
    }

    @Method(modid = "battlegear2")
    @Override
    public void performPassiveEffects(Side side, ItemStack main, ItemStack offhand) {}

    @Method(modid = "battlegear2")
    @Override
    public boolean allowOffhand(ItemStack main, ItemStack offhand) {
        return true;
    }
}
