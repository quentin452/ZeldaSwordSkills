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

package zeldaswordskills.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import zeldaswordskills.api.damage.DamageUtils.DamageSourceIceIndirect;
import zeldaswordskills.entity.ZSSEntityInfo;
import zeldaswordskills.ref.Sounds;

/**
 * 
 * Ice Arrows freeze water and enemies, as well as turning lava to stone or obsidian.
 * 
 * Be warned that freezing creepers does not prevent them from exploding.
 *
 */
public class EntityArrowIce extends EntityArrowElemental {

    public EntityArrowIce(World world) {
        super(world);
    }

    public EntityArrowIce(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityArrowIce(World world, EntityLivingBase shooter, float velocity) {
        super(world, shooter, velocity);
    }

    public EntityArrowIce(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity,
        float wobble) {
        super(world, shooter, target, velocity, wobble);
    }

    @Override
    protected DamageSource getDamageSource(Entity entity) {
        return new DamageSourceIceIndirect("arrow.ice", this, this.shootingEntity, 50, 1).setProjectile()
            .setMagicDamage();
    }

    @Override
    protected String getParticleName() {
        return "magicCrit";
    }

    @Override
    protected boolean shouldSpawnParticles() {
        return true;
    }

    @Override
    protected void updateInAir() {
        super.updateInAir();
        if (!this.worldObj.isRemote) {
            boolean flag = this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
            flag = flag || this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.lava, this);
            if (flag && this.affectBlocks()) {
                this.setDead();
            }
        }
    }

    @Override
    protected void handlePostDamageEffects(EntityLivingBase entity) {
        super.handlePostDamageEffects(entity);
        if (!entity.isDead) {
            ZSSEntityInfo.get(entity)
                .stun(MathHelper.ceiling_float_int(this.calculateDamage(entity)) * 10, true);
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);
            this.worldObj.setBlock(i, j, k, Blocks.ice);
            this.worldObj.setBlock(i, j + 1, k, Blocks.ice);
            this.worldObj.playSoundEffect(
                i + 0.5D,
                j + 0.5D,
                k + 0.5D,
                Sounds.GLASS_BREAK,
                1.0F,
                this.rand.nextFloat() * 0.4F + 0.8F);
        }
    }

    @Override
    protected boolean affectBlock(Block block, int x, int y, int z) {
        boolean flag = false;
        if (block.getMaterial() == Material.water) {
            this.worldObj.playSoundEffect(
                x + 0.5D,
                y + 0.5D,
                z + 0.5D,
                Sounds.GLASS_BREAK,
                1.0F,
                this.rand.nextFloat() * 0.4F + 0.8F);
            this.worldObj.setBlock(x, y, z, Blocks.ice);
            flag = true;
        } else if (block.getMaterial() == Material.lava) {
            this.worldObj.playSoundEffect(
                x + 0.5D,
                y + 0.5D,
                z + 0.5D,
                Sounds.FIRE_FIZZ,
                1.0F,
                this.rand.nextFloat() * 0.4F + 0.8F);
            this.worldObj.setBlock(x, y, z, (block == Blocks.lava ? Blocks.obsidian : Blocks.cobblestone));
            flag = true;
        } else if (block.getMaterial() == Material.fire) {
            this.worldObj.playSoundEffect(
                x + 0.5D,
                y + 0.5D,
                z + 0.5D,
                Sounds.FIRE_FIZZ,
                1.0F,
                this.rand.nextFloat() * 0.4F + 0.8F);
            this.worldObj.setBlockToAir(x, y, z);
            flag = true;
        }
        return flag;
    }
}
