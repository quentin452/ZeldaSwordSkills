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

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.battlegear2.api.IAllowItem;
import mods.battlegear2.api.ISheathed;
import zeldaswordskills.api.block.BlockWeight;
import zeldaswordskills.api.block.ISmashable;
import zeldaswordskills.api.damage.DamageUtils.DamageSourceBaseDirect;
import zeldaswordskills.api.entity.IParryModifier;
import zeldaswordskills.api.item.IArmorBreak;
import zeldaswordskills.api.item.ISmashBlock;
import zeldaswordskills.api.item.ISwingSpeed;
import zeldaswordskills.api.item.IUnenchantable;
import zeldaswordskills.api.item.IWeapon;
import zeldaswordskills.api.item.WeaponRegistry;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.handler.ZSSCombatEvents;
import zeldaswordskills.network.PacketDispatcher;
import zeldaswordskills.network.client.PacketISpawnParticles;
import zeldaswordskills.ref.ModInfo;
import zeldaswordskills.ref.Sounds;
import zeldaswordskills.util.WorldUtils;

@Optional.InterfaceList(
    value = { @Optional.Interface(iface = "mods.battlegear2.api.IAllowItem", modid = "battlegear2", striprefs = true),
        @Optional.Interface(iface = "mods.battlegear2.api.ISheathed", modid = "battlegear2", striprefs = true) })
public class ItemHammer extends Item implements IArmorBreak, IParryModifier, ISmashBlock, ISpawnParticles, ISwingSpeed,
    IWeapon, IUnenchantable, IAllowItem, ISheathed {

    /** Max resistance that a block may have and still be smashed */
    private final BlockWeight strength;

    /** Amount of damage this hammer inflicts */
    private final float weaponDamage;

    /** Percentage of damage that ignores armor */
    private final float ignoreArmorAmount;

    public ItemHammer(BlockWeight strength, float damage, float ignoreArmor) {
        super();
        this.strength = strength;
        this.weaponDamage = damage;
        this.ignoreArmorAmount = ignoreArmor;
        setFull3D();
        setMaxDamage(0);
        setMaxStackSize(1);
        setCreativeTab(ZSSCreativeTabs.tabCombat);
    }

    // func_150897_b is canHarvestBlock
    @Override
    public boolean func_150897_b(Block block) {
        return block instanceof ISmashable || block instanceof BlockBreakable;
    }

    @Override
    public float getOffensiveModifier(EntityLivingBase entity, ItemStack stack) {
        return 0.4F;
    }

    @Override
    public float getDefensiveModifier(EntityLivingBase entity, ItemStack stack) {
        return 0;
    }

    @Override
    public float getPercentArmorIgnored() {
        return ignoreArmorAmount;
    }

    @Override
    public BlockWeight getSmashStrength(EntityPlayer player, ItemStack stack, Block block, int meta) {
        return (block instanceof BlockBreakable) ? strength.next() : strength;
    }

    @Override
    public void onBlockSmashed(EntityPlayer player, ItemStack stack, Block block, int meta, boolean wasSmashed) {
        if (!wasSmashed) {
            WorldUtils.playSoundAtEntity(player, Sounds.HAMMER, 0.4F, 0.5F);
        }
    }

    @Override
    public float getExhaustion() {
        return (weaponDamage / 16.0F);
    }

    @Override
    public int getSwingSpeed() {
        return 30;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        WorldUtils.playSoundAtEntity(attacker, Sounds.HAMMER, 0.4F, 0.5F);
        double dx = 0.15D * (attacker.posX - target.posX);
        double dz = 0.15D * (attacker.posZ - target.posZ);
        float f = MathHelper.sqrt_double(dx * dx + dz * dz);
        if (f > 0.0F) {
            double resist = 1.0D - target.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
                .getAttributeValue();
            double f1 = resist * (weaponDamage / 8.0F) * 0.6000000238418579D;
            double k = f1 / f;
            target.addVelocity(-dx * k, 0.15D * f1, -dz * k);
        }
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.attackTime == 0) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int ticksRemaining) {
        if (this == ZSSItems.hammerSkull && player.attackTime == 0) {
            int ticksInUse = getMaxItemUseDuration(stack) - ticksRemaining;
            float charge = (float) ticksInUse / 40.0F;
            charge = Math.min((charge * charge + charge * 2.0F) / 3.0F, 1.0F);
            player.addExhaustion(charge * 2.0F);
            if (charge > 0.25F) {
                if (!player.worldObj.isRemote) {
                    PacketDispatcher.sendToAllAround(new PacketISpawnParticles(player, 4.0F), player, 64.0D);
                }
                player.swingItem();
                ZSSCombatEvents.setPlayerAttackTime(player);
                WorldUtils.playSoundAtEntity(player, Sounds.LEAPING_BLOW, 0.4F, 0.5F);
                DamageSource specialAttack = new DamageSourceBaseDirect("player", player)
                    .setStunDamage((int) (60 * charge), 5, true)
                    .setDamageBypassesArmor();
                float damage = (weaponDamage * charge) / 2.0F;
                if (damage > 0.5F) {
                    List<EntityLivingBase> entities = world
                        .getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(4.0D, 0.0D, 4.0D));
                    for (EntityLivingBase entity : entities) {
                        if (entity != player && entity.onGround) {
                            entity.attackEntityFrom(specialAttack, damage);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(World world, EntityPlayer player, ItemStack stack, double x, double y, double z,
        float radius) {
        int r = MathHelper.ceiling_float_int(radius);
        for (int i = 0; i < r; ++i) {
            for (int k = 0; k < r; ++k) {
                spawnParticlesAt(world, x + i, y, z + k);
                spawnParticlesAt(world, x + i, y, z - k);
                spawnParticlesAt(world, x - i, y, z + k);
                spawnParticlesAt(world, x - i, y, z - k);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticlesAt(World world, double x, double y, double z) {
        String particle;
        int posY = MathHelper.floor_double(y);
        Block block = world.getBlock(MathHelper.floor_double(x), posY - 1, MathHelper.floor_double(z));
        if (block.getMaterial() != Material.air) {
            particle = "blockcrack_" + Block.getIdFromBlock(block)
                + "_"
                + world.getBlockMetadata(MathHelper.floor_double(x), posY - 1, MathHelper.floor_double(z));
            for (int i = 0; i < 4; ++i) {
                double dx = x + world.rand.nextFloat() - 0.5F;
                double dy = posY + world.rand.nextFloat() * 0.2F;
                double dz = z + world.rand.nextFloat() - 0.5F;
                world.spawnParticle(particle, dx, dy, dz, world.rand.nextGaussian(), 0, world.rand.nextGaussian());
            }
        }
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(
            SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
            new AttributeModifier(field_111210_e, "Weapon modifier", (double) weaponDamage, 0));
        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(ModInfo.ID + ":" + getUnlocalizedName().substring(9));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(StatCollector.translateToLocal("tooltip." + getUnlocalizedName().substring(5) + ".desc.0"));
    }

    @Override
    public boolean isSword(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isWeapon(ItemStack stack) {
        return !WeaponRegistry.INSTANCE.isWeaponForbidden(this);
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean allowOffhand(ItemStack main, ItemStack offhand) {
        return offhand == null;
    }

    @Method(modid = "battlegear2")
    @Override
    public boolean sheatheOnBack(ItemStack stack) {
        return true;
    }
}
