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

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zeldaswordskills.api.block.BlockWeight;
import zeldaswordskills.block.BlockChestInvisible.TileEntityChestInvisible;
import zeldaswordskills.block.tileentity.TileEntityCeramicJar;
import zeldaswordskills.block.tileentity.TileEntityChestLocked;
import zeldaswordskills.block.tileentity.TileEntityDungeonBlock;
import zeldaswordskills.block.tileentity.TileEntityDungeonCore;
import zeldaswordskills.block.tileentity.TileEntityGossipStone;
import zeldaswordskills.block.tileentity.TileEntityInscription;
import zeldaswordskills.block.tileentity.TileEntityPedestal;
import zeldaswordskills.block.tileentity.TileEntitySacredFlame;
import zeldaswordskills.client.render.block.RenderAncientTablet;
import zeldaswordskills.client.render.block.RenderCeramicJar;
import zeldaswordskills.client.render.block.RenderChestLocked;
import zeldaswordskills.client.render.block.RenderGiantLever;
import zeldaswordskills.client.render.block.RenderSacredFlame;
import zeldaswordskills.client.render.block.RenderSpecialCrop;
import zeldaswordskills.client.render.block.RenderTileDungeonBlock;
import zeldaswordskills.client.render.block.RenderTileEntityCeramicJar;
import zeldaswordskills.client.render.block.RenderTileEntityChestLocked;
import zeldaswordskills.client.render.block.RenderTileEntityPedestal;
import zeldaswordskills.item.ItemAncientTablet;
import zeldaswordskills.item.ItemBlockTime;
import zeldaswordskills.item.ItemCeramicJar;
import zeldaswordskills.item.ItemDungeonBlock;
import zeldaswordskills.item.ItemGossipStone;
import zeldaswordskills.item.ItemMetadataBlock;
import zeldaswordskills.item.ItemSacredFlame;
import zeldaswordskills.item.ItemSecretStone;
import zeldaswordskills.item.ItemWarpStone;
import zeldaswordskills.item.ZSSItems;
import zeldaswordskills.util.BlockRotationData;

public class ZSSBlocks {

    public static Block barrierLight, barrierHeavy, quakeStone, timeBlock, bombFlower, ceramicJar, chestLocked,
        chestInvisible, pedestal, ancientTablet, pegWooden, pegRusty, leverGiant, beamWooden, hookTarget, hookTargetAll,
        gossipStone, inscription, warpStone, secretStone, sacredFlame,
        // the following have a real Item, not an ItemBlock:
        doorLocked, doorLockedSmall, dungeonCore, dungeonStone;

    /**
     * Call during FMLPreInitializationEvent to initialize and register all blocks
     */
    public static void preInit() {
        barrierLight = new BlockHeavy(Material.rock, BlockWeight.MEDIUM).setBlockName("zss.barrier_light");
        barrierHeavy = new BlockHeavy(Material.rock, BlockWeight.VERY_HEAVY).setBlockName("zss.barrier_heavy");
        pegWooden = new BlockPeg(ZSSBlockMaterials.pegWoodMaterial, BlockWeight.VERY_LIGHT)
            .setBlockName("zss.peg_wooden");
        pegRusty = new BlockPeg(ZSSBlockMaterials.pegRustyMaterial, BlockWeight.MEDIUM).setBlockName("zss.peg_rusty");
        ceramicJar = new BlockCeramicJar().setBlockName("zss.ceramic_jar")
            .setBlockTextureName("stone");
        chestLocked = new BlockChestLocked().setBlockName("zss.chest_locked");
        pedestal = new BlockPedestal().setBlockName("zss.pedestal");
        sacredFlame = new BlockSacredFlame().setBlockName("zss.sacredflame");
        doorLocked = new BlockDoorBoss(Material.iron).setBlockName("zss.door_locked");
        secretStone = new BlockSecretStone(Material.rock).setBlockName("zss.secretstone");
        dungeonCore = new BlockDungeonCore(Material.rock).setBlockName("zss.dungeoncore");
        dungeonStone = new BlockDungeonStone(Material.rock).setBlockName("zss.dungeonstone");
        beamWooden = new BlockBar(Material.wood).setBlockName("zss.beam_wooden");
        hookTarget = new BlockTargetDirectional(Material.rock).setBlockName("zss.hook_target");
        leverGiant = new BlockGiantLever().setBlockName("zss.lever_giant");
        chestInvisible = new BlockChestInvisible().setBlockName("zss.chest_invisible");
        timeBlock = new BlockTime().setBlockName("zss.time_block");
        inscription = new BlockSongInscription().setBlockName("zss.inscription");
        warpStone = new BlockWarpStone().setBlockName("zss.warp_stone");
        gossipStone = new BlockGossipStone().setBlockName("zss.gossip_stone");
        bombFlower = new BlockBombFlower().setBlockName("zss.bomb_flower");
        hookTargetAll = new BlockTarget(Material.rock).setBlockName("zss.hook_target_all");
        doorLockedSmall = new BlockDoorLocked(Material.iron).setBlockName("zss.door_locked_small");
        ancientTablet = new BlockAncientTablet(Material.rock).setBlockName("zss.ancient_tablet");
        quakeStone = new BlockQuakeStone().setBlockName("zss.quake_stone");

        register();
    }

    /**
     * Registers all custom Item renderers
     */
    @SideOnly(Side.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCeramicJar.class, new RenderTileEntityCeramicJar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestLocked.class, new RenderTileEntityChestLocked());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new RenderTileEntityPedestal());
        RenderingRegistry.registerBlockHandler(new RenderAncientTablet());
        RenderingRegistry.registerBlockHandler(new RenderTileDungeonBlock());
        RenderingRegistry.registerBlockHandler(new RenderSpecialCrop());
        RenderingRegistry.registerBlockHandler(new RenderCeramicJar());
        RenderingRegistry.registerBlockHandler(new RenderChestLocked());
        RenderingRegistry.registerBlockHandler(new RenderGiantLever());
        RenderingRegistry.registerBlockHandler(new RenderSacredFlame());
    }

    private static void register() {
        GameRegistry.registerBlock(barrierLight, barrierLight.getUnlocalizedName());
        GameRegistry.registerBlock(barrierHeavy, barrierHeavy.getUnlocalizedName());
        GameRegistry.registerBlock(pegWooden, pegWooden.getUnlocalizedName());
        GameRegistry.registerBlock(pegRusty, pegRusty.getUnlocalizedName());
        GameRegistry.registerBlock(ceramicJar, ItemCeramicJar.class, ceramicJar.getUnlocalizedName());
        GameRegistry.registerBlock(chestLocked, chestLocked.getUnlocalizedName());
        GameRegistry.registerBlock(chestInvisible, chestInvisible.getUnlocalizedName());
        GameRegistry.registerBlock(pedestal, ItemMetadataBlock.class, pedestal.getUnlocalizedName());
        GameRegistry.registerBlock(sacredFlame, ItemSacredFlame.class, sacredFlame.getUnlocalizedName());
        GameRegistry.registerBlock(doorLocked, doorLocked.getUnlocalizedName());
        GameRegistry.registerBlock(doorLockedSmall, doorLockedSmall.getUnlocalizedName());
        GameRegistry.registerBlock(secretStone, ItemSecretStone.class, secretStone.getUnlocalizedName());
        GameRegistry.registerBlock(dungeonCore, ItemDungeonBlock.class, dungeonCore.getUnlocalizedName());
        GameRegistry.registerBlock(dungeonStone, ItemDungeonBlock.class, dungeonStone.getUnlocalizedName());
        GameRegistry.registerBlock(beamWooden, beamWooden.getUnlocalizedName());
        GameRegistry.registerBlock(hookTarget, hookTarget.getUnlocalizedName());
        GameRegistry.registerBlock(hookTargetAll, hookTargetAll.getUnlocalizedName());
        GameRegistry.registerBlock(leverGiant, leverGiant.getUnlocalizedName());
        GameRegistry.registerBlock(timeBlock, ItemBlockTime.class, timeBlock.getUnlocalizedName());
        GameRegistry.registerBlock(inscription, inscription.getUnlocalizedName());
        GameRegistry.registerBlock(warpStone, ItemWarpStone.class, warpStone.getUnlocalizedName());
        GameRegistry.registerBlock(gossipStone, ItemGossipStone.class, gossipStone.getUnlocalizedName());
        GameRegistry.registerBlock(bombFlower, bombFlower.getUnlocalizedName());
        GameRegistry.registerBlock(ancientTablet, ItemAncientTablet.class, ancientTablet.getUnlocalizedName());
        GameRegistry.registerBlock(quakeStone, ItemMetadataBlock.class, quakeStone.getUnlocalizedName());

        GameRegistry.registerTileEntity(TileEntityCeramicJar.class, "tileEntityCeramicJar");
        GameRegistry.registerTileEntity(TileEntityChestLocked.class, "tileEntityChestLocked");
        GameRegistry.registerTileEntity(TileEntityChestInvisible.class, "tileEntityChestInvisible");
        GameRegistry.registerTileEntity(TileEntityDungeonBlock.class, "tileEntityDungeonBlock");
        GameRegistry.registerTileEntity(TileEntityDungeonCore.class, "tileEntityDungeonCore");
        GameRegistry.registerTileEntity(TileEntityGossipStone.class, "tileEntityGossipStone");
        GameRegistry.registerTileEntity(TileEntityInscription.class, "tileEntityInscription");
        GameRegistry.registerTileEntity(TileEntityPedestal.class, "tileEntityPedestal");
        GameRegistry.registerTileEntity(TileEntitySacredFlame.class, "tileEntitySacredFlame");

        // register item blocks for comparator sorting:
        try {
            for (Field f : ZSSBlocks.class.getFields()) {
                if (Block.class.isAssignableFrom(f.getType())) {
                    Block block = (Block) f.get(null);
                    if (block != null) {
                        ZSSItems.registerItemBlock(new ItemStack(block).getItem());
                    }
                }
            }
        } catch (Exception e) {

        }

        // Register rotation types for custom blocks
        BlockRotationData.registerCustomBlockRotation(ancientTablet, BlockRotationData.Rotation.WOOD);
        BlockRotationData.registerCustomBlockRotation(chestLocked, BlockRotationData.Rotation.PISTON_CONTAINER);
        BlockRotationData.registerCustomBlockRotation(chestInvisible, BlockRotationData.Rotation.PISTON_CONTAINER);
        BlockRotationData.registerCustomBlockRotation(inscription, BlockRotationData.Rotation.PISTON_CONTAINER);
        BlockRotationData.registerCustomBlockRotation(leverGiant, BlockRotationData.Rotation.LEVER);
    }
}
