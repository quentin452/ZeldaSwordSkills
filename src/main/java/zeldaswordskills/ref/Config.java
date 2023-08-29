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

package zeldaswordskills.ref;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import zeldaswordskills.ZSSMain;
import zeldaswordskills.api.item.WeaponRegistry;
import zeldaswordskills.block.BlockWarpStone;
import zeldaswordskills.client.gui.IGuiOverlay.HALIGN;
import zeldaswordskills.client.gui.IGuiOverlay.VALIGN;
import zeldaswordskills.entity.ZSSEntities;
import zeldaswordskills.item.ZSSItems;
import zeldaswordskills.network.client.SyncConfigPacket;
import zeldaswordskills.skills.BonusHeart;
import zeldaswordskills.skills.SkillBase;
import zeldaswordskills.songs.AbstractZeldaSong;
import zeldaswordskills.songs.ZeldaSongs;
import zeldaswordskills.util.BiomeType;
import zeldaswordskills.util.BossType;
import zeldaswordskills.util.WarpPoint;

/**
 * 
 * Fields denoted with [SYNC] need to be sent to each client as they log in
 * to the server. In some cases this is because the field is mainly used
 * on the client (e.g. attack speed), in others because the field is used on
 * both sides and may result in inconsistencies if not synced (e.g. whip length).
 *
 */
public class Config {

    public static Configuration config;
    /* ================== CLIENT SIDE SETTINGS ===================== */
    private static Configuration clientConfig;
    /** [Buff HUD] Maximum number of icons to display per row or column [1 - 10] */
    public static int buffBarMaxIcons;
    /** [Buff HUD] Whether the buff bar should be displayed by default */
    public static boolean isBuffBarEnabled;
    /** [Buff HUD] Whether the buff bar should be displayed horizontally */
    public static boolean isBuffBarHorizontal;
    /** [Buff HUD][Alignment: Horizontal] Alignment on the X axis [left|center|right] */
    public static HALIGN buffBarHAlign;
    /** [Buff HUD][Alignment: Vertical] Alignment on the Y axis [top|center|bottom] */
    public static VALIGN buffBarVAlign;
    /** [Buff HUD][Offset: X] Moves the HUD element left (-) or right (+) this number of pixels */
    public static int buffBarOffsetX;
    /** [Buff HUD][Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels */
    public static int buffBarOffsetY;
    /** [Chat] Whether to show a chat message when striking secret blocks */
    public static boolean showSecretMessage;
    /** [Combo HUD] Whether the combo hit counter will display by default (may be toggled in game) */
    public static boolean isComboHudEnabled;
    /** [Combo HUD] Number of combo hits to display */
    public static int hitsToDisplay;
    /** [Combo HUD][Alignment: Horizontal] Alignment on the X axis [left|center|right] */
    public static HALIGN comboHudHAlign;
    /** [Combo HUD][Alignment: Vertical] Alignment on the Y axis [top|center|bottom] */
    public static VALIGN comboHudVAlign;
    /** [Combo HUD][Offset: X] Moves the HUD element left (-) or right (+) this number of pixels */
    public static int comboHudOffsetX;
    /** [Combo HUD][Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels */
    public static int comboHudOffsetY;
    /** [Controls] Whether to use vanilla movement keys to activate skills such as Dodge and Parry */
    public static boolean allowVanillaControls;
    /**
     * [Controls] Whether Dodge and Parry require double-tap or not (double-tap always required with vanilla control
     * scheme)
     */
    public static boolean requireDoubleTap;
    /**
     * [Ending Blow HUD] Enable Ending Blow HUD display (if disabled, there is not any indication that the skill is
     * ready to use)
     */
    public static boolean isEndingBlowHudEnabled;
    /** [Ending Blow HUD][Alignment: Horizontal] Alignment on the X axis [left|center|right] */
    public static HALIGN endingBlowHudHAlign;
    /** [Ending Blow HUD][Alignment: Vertical] Alignment on the Y axis [top|center|bottom] */
    public static VALIGN endingBlowHudVAlign;
    /** [Ending Blow HUD][Offset: X] Moves the HUD element left (-) or right (+) this number of pixels */
    public static int endingBlowHudOffsetX;
    /** [Ending Blow HUD][Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels */
    public static int endingBlowHudOffsetY;
    /** [Item Mode HUD] Enable item mode HUD display (if disabled, mode may still be viewed in the item's tooltip) */
    public static boolean isItemModeEnabled;
    /** [Item Mode HUD][Alignment: Horizontal] Alignment on the X axis [left|center|right] */
    public static HALIGN itemModeHAlign;
    /** [Item Mode HUD][Alignment: Vertical] Alignment on the Y axis [top|center|bottom] */
    public static VALIGN itemModeVAlign;
    /** [Item Mode HUD][Offset: X] Moves the HUD element left (-) or right (+) this number of pixels */
    public static int itemModeOffsetX;
    /** [Item Mode HUD][Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels */
    public static int itemModeOffsetY;
    /** [Magic Meter][Alignment: Horizontal] Alignment on the X axis [left|center|right] */
    public static HALIGN magicMeterHAlign;
    /** [Magic Meter][Alignment: Vertical] Alignment on the Y axis [top|center|bottom] */
    public static VALIGN magicMeterVAlign;
    /** [Magic Meter] Enable text display of current Magic Points */
    public static boolean isMagicMeterTextEnabled;
    /** [Magic Meter] Enable the Magic Meter HUD display */
    public static boolean isMagicMeterEnabled;
    /** [Magic Meter][Offset: X] Moves the Meter left (-) or right (+) this number of pixels */
    public static int magicMeterOffsetX;
    /** [Magic Meter][Offset: Y] Moves the Meter up (-) or down (+) this number of pixels */
    public static int magicMeterOffsetY;
    /** [Magic Meter][Orientation] True for a horizontal magic meter, or false for a vertical one */
    public static boolean isMagicMeterHorizontal;
    /**
     * [Magic Meter][Orientation: Mana] True to drain mana from right-to-left or top-to-bottom depending on orientation;
     * false for the opposite
     */
    public static boolean isMagicBarLeft;
    /** [Magic Meter][Width: Max] Maximum width of the magic meter [25 - 100] */
    public static int magicMeterWidth;
    /**
     * [Magic Meter][Width: Increment] Number of increments required to max out the magic meter, where each increment is
     * 50 magic points [1-10]
     */
    public static int magicMeterIncrements;
    /** [Song GUI] Number of ticks allowed between notes before played notes are cleared [5 - 100] */
    public static int resetNotesInterval;
    /** [Sound] Whether to play the 'itembreak' sound when the hookshot misses */
    public static boolean enableHookshotSound;
    /** [Targeting] Prioritize mobs over other entity types when targeting */
    public static boolean targetMobs;
    /** [Targeting] Whether auto-targeting is enabled or not (toggle in game by pressing '.') */
    public static boolean enableAutoTarget;

    public static boolean toggleAutoTarget() {
        enableAutoTarget = !enableAutoTarget;
        return enableAutoTarget;
    }

    /** [Targeting] Whether players can be targeted (toggle in game by pressing '.' while sneaking) */
    public static boolean canTargetPlayers;

    public static boolean toggleTargetPlayers() {
        canTargetPlayers = !canTargetPlayers;
        return canTargetPlayers;
    }

    /* ================== MOD INTER-COMPATIBILITY ===================== */
    /** [SYNC] [BattleGear2] Allow Master Swords to be held in the off-hand */
    private static boolean enableOffhandMaster;
    /* ================== GENERAL ===================== */
    /** [SYNC] Whether players can be stunned; if false, item use is still interrupted */
    private static boolean enableStunPlayer;
    /** [SYNC] Whether the swing speed timer prevents all left-clicks, or only items that use swing speeds */
    private static boolean enableSwingSpeed;
    /**
     * [SYNC] Default swing speed (anti-left-click-spam): Sets base number of ticks between each left-click (0 to
     * disable)[0-20]
     */
    private static int baseSwingSpeed;
    /**
     * Hardcore Zelda Fan: Start with only 3 hearts (applies a -14 max health modifier, so it can be enabled or disabled
     * at any time)
     */
    private static boolean enableHardcoreZeldaFanMode;
    /**
     * [SYNC] Whether regular (i.e. breakable) secret stone blocks can be picked up using appropriate items (e.g.
     * gauntlets)
     */
    private static boolean enableSecretStoneLift;
    /** [SYNC] Whether vanilla blocks can be picked up using appropriate items (e.g. gauntlets) */
    private static boolean enableVanillaLift;
    /** [SYNC] Whether vanilla blocks can be smashed using appropriate items (e.g. hammers) */
    private static boolean enableVanillaSmash;
    /** Always pick up small hearts regardless of health */
    private static boolean alwaysPickupHearts;
    /**
     * [Boss] Boss health multiplier, as a percent increase per difficulty level (will not apply to real bosses)
     * [100-500]
     */
    private static float bossHealthFactor;
    /** [Boss] Number of boss mobs to spawn in Boss Dungeons (will not apply to real bosses) [1-8] */
    private static int bossNumber;
    /** [Ceramic Jars] Whether ceramic jar tile entities update each tick, allowing them to store dropped items */
    private static boolean enableJarUpdates;
    /** [Sacred Flames] Number of days before flame rekindles itself (0 to disable) [0-30] */
    private static int sacredRefreshRate;
    /**
     * [Skulltula Tokens] Number of days between each recurring reward for completing the quest (0 to disable recurring
     * reward) [0-30]
     */
    private static int skulltulaRewardRate;
    /** [Mob Buff] Disable all buffs (resistances and weaknesses) for vanilla mobs */
    private static boolean disableVanillaBuffs;
    /** [NPC] Sets whether Zelda NPCs are invulnerable or not */
    private static boolean npcsAreInvulnerable;
    /** [NPC][Navi] Range at which Navi can sense secret rooms, in blocks (0 to disable) [0-10] */
    private static int naviRange;
    /** [NPC][Navi] Frequency with which Navi checks the proximity for secret rooms, in ticks [20-200] */
    private static int naviFrequency;
    /* ================== ITEMS ===================== */
    /** [Arrows] Whether transforming arrows with the Sacred Flames has a chance to consume the flame */
    private static boolean arrowsConsumeFlame;
    /** [SYNC] [Bombs] Minimum fuse time; set to 0 to disable held bomb ticks */
    private static int bombFuseTime;
    /** [Bombs] Whether bombs are non-griefing, i.e. can only destroy secret stone */
    private static boolean onlyBombSecretStone;
    /** [Bombs] Whether bombs can destroy regular blocks in Adventure Mode */
    private static boolean bombsGriefAdventure;
    /** [Boomerang] Allow Boomerang to destroy grass and similar blocks */
    private static boolean enableBoomerangDenude;
    /** [Deku Leaf] Allow Deku Leaf whirlwind to destroy leaves */
    private static boolean enableDekuDenude;
    /** [Din's Fire] Whether Din's Fire can set blocks on fire */
    private static boolean enableDinIgnite;
    /** [Din's Fire] Whether Din's Fire can melt unbreakable ice blocks */
    private static boolean enableDinMelt;
    /**
     * [SYNC] [Enchantments] Disable the vanilla behavior allowing unenchantable items to be enchanted using the anvil
     */
    private static boolean disableAllUnenchantables;
    /**
     * [Hammer] True to allow the Megaton Hammer to break Quake Stone (also requires player to have Golden Gauntlets in
     * inventory)
     */
    private static boolean enableMegaSmashQuake;
    /** [Hero's Bow] Cost (in emeralds) to upgrade, per level */
    private static int heroBowUpgradeCost;
    /** [Hero's Bow] Whether the fire arrow can ignite affected blocks */
    private static boolean enableFireArrowIgnite;
    /** [Hero's Bow] Whether the fire arrow can destroy unbreakable ice blocks */
    private static boolean enableFireArrowMelt;
    /** [Hero's Bow] Whether the light arrow can penetrate blocks */
    private static boolean enableLightArrowNoClip;
    /** [Hero's Bow] Whether to automate bomb arrow firing when sneaking */
    private static boolean enableAutoBombArrows;
    /** [SYNC] [Hookshot] Max range of non-extended hookshots */
    private static int hookshotRange;
    /**
     * [SYNC] [Hookshot] Whether hookshots are allowed to interact ONLY with IHookable blocks - great for adventure
     * maps!
     */
    private static boolean enableHookableOnly;
    /** [Hookshot] Whether hookshots are allowed to destroy certain blocks such as glass */
    private static boolean enableHookshotBreakBlocks;
    /** [Magic Medallions] True if Ether and Quake medallions can affect players */
    private static boolean medallionsAffectPlayers;
    /** [Magic Rods] Cost (in emeralds) to upgrade (note that the Tornado Rod costs 3/4 this value) [128-1280] */
    private static int rodUpgradeCost;
    /** [Magic Rods] Enable fire rod to set blocks on fire */
    private static boolean rodFireGriefing;
    /** [Master Sword] Number of mobs that need to be killed to upgrade the Tempered Sword */
    private static int temperedRequiredKills;
    /** [SYNC] [Master Sword] Whether ALL master swords provide power when placed in a Sword Pedestal */
    private static boolean allMasterSwordsProvidePower;
    /** [Skeleton Key] Number of locked chests which can be opened before key breaks (0 for no limit) [0-500] */
    private static int numSkelKeyUses;
    /** [Slingshot] Cost (in emeralds) for first upgrade */
    private static int slingshotUpgradeOne;
    /** [Slingshot] Cost (in emeralds) for second upgrade */
    private static int slingshotUpgradeTwo;
    /** [SYNC] [Whip] Range, in blocks, of the standard whip [4-12] */
    private static int whipRange;
    /* ================== STARTING GEAR ===================== */
    /** Whether starting gear will be granted */
    public static boolean enableStartingGear;
    /** Whether starting gear is automatically equipped when granted */
    public static boolean enableAutoEquip;
    /** Begin the game with Link's House - place it anywhere you like! */
    public static boolean enableLinksHouse;
    /** Grants a single Basic Sword skill orb */
    public static boolean enableOrb;
    /** Grants the full set of Kokiri clothing: hat, tunic, trousers, boots */
    public static boolean enableFullSet;
    /** Grants only the Kokiri Tunic */
    public static boolean enableTunic;
    /** Grants the Kokiri sword (a named wooden sword) */
    public static boolean enableSword;
    /** Start the game with Navi in a bottle (you can always acquire her later if false) */
    public static boolean enableNavi;
    /* ================== SKILLS ===================== */
    /** Max number of bonus 1/2 hearts (capped at 80) */
    private static int maxBonusHearts;
    /** [Back Slice] Allow Back Slice to potentially knock off player armor */
    private static boolean allowDisarmorPlayer;
    /** [Parry] Bonus to disarm based on timing: tenths of a percent added per tick remaining on the timer [0-50] */
    private static float disarmTimingBonus;
    /**
     * [Parry] Penalty to disarm chance: percent per Parry level of the opponent, default negates defender's skill bonus
     * so disarm is based entirely on timing [0-20]
     */
    private static float disarmPenalty;
    /**
     * [Magic] Allow Nayru's Love to be activated even when magic bar is unlimited (such as after drinking a Chateau
     * Romani)
     */
    private static boolean allowUnlimitedNayru;
    /** [Magic] Maximum magic points attainable [50-1000] */
    private static int maxMagicPoints;
    /**
     * [SYNC] [Super Spin Attack | Sword Beam] True to require a completely full health bar to use, or false to allow a
     * small amount to be missing per level
     */
    private static boolean requireFullHealth;
    /* ================== SONGS ===================== */
    /** [Song of Storms] Time required between each use of the song (by anybody) [0-24000] */
    private static int minSongIntervalStorm;
    /** [Sun's Song] Time required between each use of the song (by anybody) [0-24000] */
    private static int minSongIntervalSun;
    /* ================== DUNGEON GEN ===================== */
    /** Whether to prevent ZSS structures from generating if any non-vanilla blocks are detected */
    private static boolean avoidModBlocks;
    /** [Boss Dungeon] Whether boss dungeons are allowed to have windows or not */
    private static boolean enableWindows;
    /** [Boss Dungeon] Enable Boss Dungeon generation */
    private static boolean enableBossDungeons;
    /** [Boss Dungeon] Ignore biome settings and randomize boss dungeon / boss key locations */
    private static boolean randomizeBossDungeons;
    /** Minimum number of chunks between Boss Dungeons in the Overworld */
    private static int minBossDistance;
    /** Minimum number of chunks between Boss Dungeons in the Nether */
    private static int minBossDistanceNether;
    /** Minimum number of blocks between land-based secret rooms */
    private static int minLandDistance;
    /** Minimum number of blocks between nether-based secret rooms */
    private static int minDistanceNether;
    /** Minimum number of blocks between ocean-based secret rooms */
    private static int minOceanDistance;
    /** Max number of overworld secret room generation attempts per chunk (capped at 10) */
    private static int genAttemptsPerChunk;
    /** Chance of each iteration to attempt to generate a secret room in the overworld */
    private static float secretRoomChance;
    /** Difficulty level for finding overworld dungeons (1 very easy, 3 very hard) */
    private static int mainDungeonDifficulty;
    /** Max number of Nether secret room generation attempts per chunk (capped at 10) */
    private static int genAttemptsPerChunkNether;
    /** Chance of each iteration to attempt to generate a secret room in the Nether */
    private static float secretRoomChanceNether;
    /** Difficulty level for finding nether dungeons (1 very easy, 3 very hard) */
    private static int netherDungeonDifficulty;
    /** Chance (as a percent) for certain dungeons to have fairy spawners */
    private static float fairySpawnerChance;
    /** Maximum number of days required for fairies to replenish */
    private static int resetSpawnerTime;
    /** [No-Gen] Disable structure and feature generation entirely within a specified zone */
    private static boolean disableStructureGen;
    /** [No-Gen] Starting chunk coordinate X for the structure free zone [max is +/- 1875000] */
    private static int noGenX;
    /** [No-Gen] Starting chunk coordinate Z for the structure free zone [max is +/- 1875000] */
    private static int noGenZ;
    /* ================== WORLD GEN ===================== */
    /** [Ancient Tablet] Chance that a random tablet will spawn when a boss dungeon is defeated */
    private static float ancientTabletGenChance;
    /** [Ancient Tablet] Maximum number of chunks from boss dungeon a tablet may generate [0-8] */
    private static int ancientTabletGenDistance;
    /** [Bomb Flowers] Enable bomb flower generation */
    private static boolean enableBombFlowerGen;
    /** [Ceramic Jars] Allow ceramic jars to generate in water */
    private static boolean allowJarsInWater;
    /** [Ceramic Jars][Surface] Chance of generating a jar cluster in a given chunk */
    private static float jarGenChance;
    /** [Ceramic Jars][Surface] Max number of jars per cluster */
    private static int jarsPerCluster;
    /** [Ceramic Jars][Underground] Chance for each jar cluster to generate */
    private static float jarGenChanceSub;
    /** [Ceramic Jars][Underground] Max number of jars per cluster */
    private static int jarsPerClusterSub;
    /** [Ceramic Jars][Underground] Max number of jar clusters per chunk */
    private static int jarClustersPerChunkSub;
    /** [Ceramic Jars][Nether] Chance for each jar cluster to generate */
    private static float jarGenChanceNether;
    /** [Ceramic Jars][Nether] Max number of jars per cluster */
    private static int jarsPerClusterNether;
    /** [Ceramic Jars][Nether] Max number of jar clusters per chunk */
    private static int jarClustersPerChunkNether;
    /** [Gossip Stones] Chance (1 = 0.1% chance) per chunk of a Gossip Stone generating [0-100] */
    private static float gossipStoneRate;
    /** [Song Pillars] Enable song and broken pillar generation */
    private static boolean enablePillarGen;
    /** [Song Pillars] Maximum search range; reduce if new chunks are loading too slowly [16-64] */
    private static int maxPillarRange;
    /** [Song Pillars] Minimum number of chunks between broken pillars [4-64] */
    private static int minBrokenPillarDistance;
    /** [Song Pillars] Minimum number of chunks between song pillars [8-64] */
    private static int minSongPillarDistance;
    /* ================== LOOT ===================== */
    /** Chance of grass dropping loot (set to zero to disable) */
    private static float grassDropChance;
    /** Chance of empty jars dropping loot (set to zero to disable) */
    private static float jarDropChance;
    /** Chance (as a percent) of a Forest Temple containing a Master Sword [1-100] */
    private static float masterSwordChance;
    /** Chance (as a percent) a chest will be locked */
    private static float lockedChestChance;
    /** Chance that a secret room may have two chests */
    private static float doubleChestChance;
    /** Chance that a secret room's entrance will be barred by some obstacle */
    private static float barredRoomChance;
    /** Chance of a heart piece always appearing in secret room chests */
    private static float heartPieceChance;
    /** Chance of a random boss-level item being added to locked chest loot table */
    private static float randomBossItemChance;
    /** Minimum number of random chest contents for first chest */
    private static int minNumChestItems;
    /** Random loot generation weights for each individual item */
    private static int bombWeight, bombBagWeight, heartPieceWeight, bigKeyWeight, smallKeyWeight;
    /** Loot weight for items in locked chests */
    private static int lockedLootWeight;
    /** [Skill Orbs] Whether this skill may appear as random loot, such as in Boss chests */
    private static Set<Byte> lootableOrbs = new HashSet<Byte>();
    /* ================== TRADES ===================== */
    /** [Bomb Bag] Allow Barnes to sell bomb bags (checked each time Barnes is shown a bomb) */
    private static boolean enableTradeBombBag;
    /** [Bomb Bag] Cost of a bomb bag at Barnes' shop (only applied to new trades) */
    private static int bombBagPrice;
    /** [Bombs] Enable random villager trades for bombs */
    private static boolean enableTradeBomb;
    /** [Hero's Bow] Whether magic arrows (fire, ice, light) can be purchased */
    private static boolean enableArrowTrades;
    /** [Masks] Chance that a villager will be interested in purchasing a random mask */
    private static float maskBuyChance;
    /* ================== MAP MAKING ===================== */
    private static final String WARP_LOCATIONS_KEY = "Default Warp Locations: one per line with format 'song_name:[dimension_id,x,y,z]'";
    /** [Warp Stone] Default warp locations */
    private static Map<AbstractZeldaSong, WarpPoint> warp_defaults = new HashMap<AbstractZeldaSong, WarpPoint>();
    /* ================== MOBS ===================== */
    private static Configuration mobConfig;
    // --- DROPS ---//
    /** Creeper bomb drop chance */
    private static float creeperDrop;
    /** Frequency of small heart and magic jar drops from mobs [zero to disable; 1 = rare, 10 = very common] */
    private static int mobConsumableFrequency;
    /** [Skill Orbs] Enable skill orbs to drop as loot from mobs */
    private static boolean enableOrbDrops;
    /** [Skill Orbs] Chance of dropping random orb */
    private static float randomDropChance;
    /** [Skill Orbs] Chance for unmapped mob to drop an orb */
    private static float genericMobDropChance;
    /** [Skill Orbs] Individual drop chances for skill orbs and heart pieces */
    private static Map<Byte, Float> orbDropChance;
    /** [Piece of Power] Approximate number of enemies you need to kill before a piece of power drops */
    private static int powerDropRate;
    /** [Whip] Chance that loot may be snatched from various vanilla mobs, using a whip (0 to disable)[0-100] */
    private static float vanillaWhipLootChance;
    /**
     * [Whip] All whip-stealing chances are multiplied by this value, as a percentage, including any added by other mods
     * (0 disables ALL whip stealing!)[0-500]
     */
    private static float globalWhipLootChance;
    /**
     * [Whip] Whether to inflict damage to entities when stealing an item (IEntityLootable entities determine this
     * separately)
     */
    private static boolean hurtOnSteal;
    // --- SPAWNING ---//
    /** Chance that a random mob will spawn inside of secret rooms (0 to disable) [0-100] */
    private static float roomSpawnMobChance;
    /** [Keese] Chance of Keese spawning in a swarm */
    private static float keeseSwarmChance;
    /** [Keese] Maximum number of Keese that can spawn in a swarm */
    private static int keeseSwarmSize;
    /** Minimum number of days required to pass before Darknuts may spawn [0-30] */
    private static int minDaysToSpawnDarknut;
    /** Minimum number of days required to pass before Wizzrobes may spawn [0-30] */
    private static int minDaysToSpawnWizzrobe;
    /* ================== WEAPON REGISTRY ===================== */
    private static Configuration weaponConfig;
    /** Items that are considered Swords for all intents and purposes */
    private static String[] swords = new String[0];
    /** Items that are considered Melee Weapons for all intents and purposes */
    private static String[] weapons = new String[0];
    /** Items that are forbidden from being considered as Swords */
    private static String[] forbidden_swords = new String[0];
    /** Items that are forbidden from being considered as Melee Weapons */
    private static String[] forbidden_weapons = new String[0];

    public static void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(
            new File(
                event.getModConfigurationDirectory()
                    .getAbsolutePath() + ModInfo.CONFIG_PATH
                    + "zss_server.cfg"));
        config.load();
        ZSSItems.initConfig(config);
        /* ================== MOD INTER-COMPATIBILITY ===================== */
        enableOffhandMaster = config
            .get("Mod Support", "[BattleGear2] Allow Master Swords to be held in the off-hand", false)
            .getBoolean(false);
        /* ================== GENERAL ===================== */
        enableStunPlayer = config
            .get("General", "Whether players can be stunned; if false, item use is still interrupted", false)
            .getBoolean(false);
        enableSwingSpeed = config
            .get(
                "General",
                "Whether the swing speed timer prevents all left-clicks, or only items that use swing speeds",
                true)
            .getBoolean(true);
        baseSwingSpeed = MathHelper.clamp_int(
            config.get(
                "General",
                "Default swing speed (anti-left-click-spam): Sets base number of ticks between each left-click (0 to disable)[0-20]",
                0)
                .getInt(),
            0,
            20);
        enableSecretStoneLift = config.get(
            "General",
            "Whether regular (i.e. breakable) secret stone blocks can be picked up using appropriate items (e.g. gauntlets)",
            false)
            .getBoolean(false);
        enableVanillaLift = config
            .get("General", "Whether vanilla blocks can be picked up using appropriate items (e.g. gauntlets)", true)
            .getBoolean(true);
        enableVanillaSmash = config
            .get("General", "Whether vanilla blocks can be smashed using appropriate items (e.g. hammers)", true)
            .getBoolean(true);
        alwaysPickupHearts = config.get("General", "Always pick up small hearts regardless of health", false)
            .getBoolean(false);
        enableHardcoreZeldaFanMode = config.get(
            "General",
            "Hardcore Zelda Fan: Start with only 3 hearts (applies a -14 max health modifier, so it can be enabled or disabled at any time)",
            false)
            .getBoolean(false);
        bossHealthFactor = 0.01F * (float) MathHelper.clamp_int(
            config.get(
                "General",
                "[Boss] Boss health multiplier, as a percent increase per difficulty level (does not apply to real bosses) [100-500]",
                250)
                .getInt(),
            100,
            500);
        bossNumber = MathHelper.clamp_int(
            config
                .get(
                    "General",
                    "[Boss] Number of boss mobs to spawn in Boss Dungeons (does not apply to real bosses) [1-8]",
                    4)
                .getInt(),
            1,
            8);
        enableJarUpdates = config.get(
            "General",
            "[Ceramic Jars] Whether ceramic jar tile entities update each tick, allowing them to store dropped items",
            true)
            .getBoolean(true);
        sacredRefreshRate = MathHelper.clamp_int(
            config
                .get("General", "[Sacred Flames] Number of days before flame rekindles itself (0 to disable) [0-30]", 7)
                .getInt(),
            0,
            30);
        skulltulaRewardRate = MathHelper.clamp_int(
            config.get(
                "General",
                "[Skulltula Tokens] Number of days between each recurring reward for completing the quest (0 to disable recurring reward) [0-30]",
                7)
                .getInt(),
            0,
            30);
        disableVanillaBuffs = config
            .get("General", "[Mob Buff] Disable all buffs (resistances and weaknesses) for vanilla mobs", false)
            .getBoolean(false);
        npcsAreInvulnerable = config.get("General", "[NPC] Sets whether Zelda NPCs are invulnerable or not", true)
            .getBoolean(true);
        naviRange = MathHelper.clamp_int(
            config.get("General", "Range at which Navi can sense secret rooms, in blocks (0 to disable) [0-10]", 4)
                .getInt(),
            0,
            10);
        naviFrequency = MathHelper.clamp_int(
            config
                .get(
                    "General",
                    "[NPC][Navi] Frequency with which Navi checks the proximity for secret rooms, in ticks [20-200]",
                    50)
                .getInt(),
            20,
            200);
        /* ================== ITEMS ===================== */
        arrowsConsumeFlame = config
            .get(
                "Item",
                "[Arrows] Whether transforming arrows with the Sacred Flames has a chance to consume the flame",
                true)
            .getBoolean(true);
        bombFuseTime = MathHelper.clamp_int(
            config.get("Item", "[Bombs] Minimum fuse time; set to 0 to disable held bomb ticks [0-128]", 56)
                .getInt(),
            0,
            128);
        onlyBombSecretStone = config
            .get("Item", "[Bombs] Whether bombs are non-griefing, i.e. can only destroy secret stone", false)
            .getBoolean(false);
        bombsGriefAdventure = config
            .get("Item", "[Bombs] Whether bombs can destroy regular blocks in Adventure Mode", false)
            .getBoolean(false);
        enableBoomerangDenude = config
            .get("Item", "[Boomerang] Allow Boomerang to destroy grass and similar blocks", true)
            .getBoolean(true);
        enableDekuDenude = config.get("Item", "[Deku Leaf] Allow Deku Leaf whirlwind to destroy leaves", true)
            .getBoolean(true);
        enableDinIgnite = config.get("Item", "[Din's Fire] Whether Din's Fire can set blocks on fire", false)
            .getBoolean(false);
        enableDinMelt = config.get("Item", "[Din's Fire] Whether Din's Fire can melt unbreakable ice blocks", true)
            .getBoolean(true);
        disableAllUnenchantables = config.get(
            "Item",
            "[Enchantments] Disable the vanilla behavior allowing unenchantable items to be enchanted using the anvil",
            false)
            .getBoolean(false);
        enableMegaSmashQuake = config.get(
            "Item",
            "[Hammer] True to allow the Megaton Hammer to break Quake Stone (also requires player to have Golden Gauntlets in inventory)",
            true)
            .getBoolean(true);
        heroBowUpgradeCost = MathHelper.clamp_int(
            config.get("Item", "[Hero's Bow] Cost (in emeralds) to upgrade, per level [128 - 640]", 192)
                .getInt(),
            128,
            640);
        enableFireArrowIgnite = config
            .get("Item", "[Hero's Bow] Whether the fire arrow can ignite affected blocks", true)
            .getBoolean(true);
        enableFireArrowMelt = config
            .get("Item", "[Hero's Bow] Whether the fire arrow can melt unbreakable ice blocks", false)
            .getBoolean(false);
        enableLightArrowNoClip = config.get("Item", "[Hero's Bow] Whether the light arrow can penetrate blocks", true)
            .getBoolean(true);
        enableAutoBombArrows = config
            .get("Item", "[Hero's Bow] Whether to automate bomb arrow firing when sneaking", true)
            .getBoolean(true);
        hookshotRange = MathHelper.clamp_int(
            config.get("Item", "[Hookshot] Max range of non-extended hookshots [4-16]", 8)
                .getInt(),
            4,
            16);
        enableHookableOnly = config.get(
            "Item",
            "[Hookshot] Whether hookshots are allowed to interact ONLY with IHookable blocks - great for adventure maps!",
            false)
            .getBoolean(false);
        enableHookshotBreakBlocks = config
            .get("Item", "[Hookshot] Whether hookshots are allowed to destroy certain blocks such as glass", true)
            .getBoolean(true);
        medallionsAffectPlayers = config
            .get("Item", "[Magic Medallions] True if Ether and Quake medallions can affect players", true)
            .getBoolean(true);
        rodUpgradeCost = MathHelper.clamp_int(
            config.get(
                "Item",
                "[Magic Rods] Cost (in emeralds) to upgrade (note that the Tornado Rod costs 3/4 this value) [128-1280]",
                768)
                .getInt(),
            128,
            1280);
        rodFireGriefing = config.get("Item", "[Magic Rods] Enable fire rod to set blocks on fire", true)
            .getBoolean(true);
        temperedRequiredKills = MathHelper.clamp_int(
            config
                .get(
                    "Item",
                    "[Master Sword] Number of mobs that need to be killed to upgrade the Tempered Sword [100-1000]",
                    300)
                .getInt(),
            100,
            1000);
        allMasterSwordsProvidePower = config
            .get("Item", "[Master Sword] Whether ALL master swords provide power when placed in a Sword Pedestal", true)
            .getBoolean(true);
        numSkelKeyUses = MathHelper.clamp_int(
            config.get(
                "Item",
                "[Skeleton Key] Number of locked chests which can be opened before key breaks (0 for no limit) [0-500]",
                50)
                .getInt(),
            0,
            500);
        slingshotUpgradeOne = MathHelper.clamp_int(
            config.get("Item", "[Slingshot] Cost (in emeralds) for first upgrade [64-320]", 128)
                .getInt(),
            64,
            320);
        slingshotUpgradeTwo = MathHelper.clamp_int(
            config.get("Item", "[Slingshot] Cost (in emeralds) for second upgrade [128-640]", 320)
                .getInt(),
            128,
            640);
        whipRange = MathHelper.clamp_int(
            config.get("Item", "[Whip] Range, in blocks, of the standard whip [4-12]", 6)
                .getInt(),
            4,
            12);
        /* ================== STARTING GEAR ===================== */
        enableStartingGear = config.get("Bonus Gear", "Enable bonus starting equipment", true)
            .getBoolean(true);
        enableAutoEquip = config.get("Bonus Gear", "Automatically equip starting equipment", true)
            .getBoolean(true);
        enableLinksHouse = config
            .get("Bonus Gear", "Begin the game with Link's House - place it anywhere you like!", true)
            .getBoolean(true);
        enableOrb = config.get("Bonus Gear", "Grants a single Basic Sword skill orb", true)
            .getBoolean(true);
        enableFullSet = config
            .get("Bonus Gear", "Grants a full set of Kokiri clothing: hat, tunic, trousers, boots", true)
            .getBoolean(true);
        enableTunic = config.get("Bonus Gear", "Grants only a Kokiri Tunic (if full set is disabled)", true)
            .getBoolean(true);
        enableSword = config.get("Bonus Gear", "Grants a Kokiri sword", true)
            .getBoolean(true);
        enableNavi = config
            .get(
                "Bonus Gear",
                "Start the game with Navi in a bottle (you can always acquire her later if false)",
                false)
            .getBoolean(false);
        /* ================== SKILLS ===================== */
        maxBonusHearts = MathHelper.clamp_int(
            config.get("Skills", "Max Bonus Hearts [0-50]", 20)
                .getInt(),
            0,
            BonusHeart.MAX_BONUS_HEARTS);
        allowDisarmorPlayer = config
            .get("Skills", "[Back Slice] Allow Back Slice to potentially knock off player armor", true)
            .getBoolean(true);
        disarmTimingBonus = 0.001F * (float) MathHelper.clamp_int(
            config.get(
                "Skills",
                "[Parry] Bonus to disarm based on timing: tenths of a percent added per tick remaining on the timer [0-50]",
                25)
                .getInt(),
            0,
            15);
        disarmPenalty = 0.01F * (float) MathHelper.clamp_int(
            config.get(
                "Skills",
                "[Parry] Penalty to disarm chance: percent per Parry level of the opponent, default negates defender's skill bonus so disarm is based entirely on timing [0-20]",
                10)
                .getInt(),
            0,
            20);
        maxMagicPoints = MathHelper.clamp_int(
            config.get("Skills", "[Magic] Maximum magic points attainable [50-1000]", 250)
                .getInt(),
            50,
            1000);
        allowUnlimitedNayru = config.get(
            "Skills",
            "[Magic] Allow Nayru's Love to be activated even when magic bar is unlimited (such as after drinking a Chateau Romani)",
            false)
            .getBoolean(false);
        requireFullHealth = config.get(
            "Skills",
            "[Super Spin Attack | Sword Beam] True to require a completely full health bar to use, or false to allow a small amount to be missing per level",
            false)
            .getBoolean(false);
        /* ================== DUNGEON GEN ===================== */
        avoidModBlocks = config
            .get(
                "Dungeon Generation",
                "Whether to prevent ZSS structures from generating if any non-vanilla blocks are detected",
                true)
            .getBoolean(true);
        enableWindows = config
            .get("Dungeon Generation", "[Boss Dungeon] Whether boss dungeons are allowed to have windows or not", true)
            .getBoolean(true);
        enableBossDungeons = config.get("Dungeon Generation", "[Boss Dungeon] Enable Boss Dungeon generation", true)
            .getBoolean(true);
        randomizeBossDungeons = config
            .get(
                "Dungeon Generation",
                "[Boss Dungeon] Ignore biome settings and randomize boss dungeon / boss key locations",
                false)
            .getBoolean(false);
        mainDungeonDifficulty = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Overworld] Adjust secret rooms so they are more hidden [1 = less, 3 = most]",
                    2)
                .getInt(),
            1,
            3);
        secretRoomChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Overworld] Chance (as a percent) per iteration of secret room generating [1-100]",
                    80)
                .getInt(),
            1,
            100);
        minLandDistance = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Overworld] Minimum number of blocks between land-based secret rooms [2-16]",
                    6)
                .getInt(),
            2,
            16);
        minOceanDistance = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Overworld] Minimum number of blocks between ocean-based secret rooms [2-32]",
                    6)
                .getInt(),
            2,
            32);
        minBossDistance = MathHelper.clamp_int(
            config.get("Dungeon Generation", "[Overworld] Minimum number of chunks between Boss Dungeons [8-128]", 24)
                .getInt(),
            8,
            128);
        genAttemptsPerChunk = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Overworld] Secret room generation attempts per chunk (0 to disable) [0-20]",
                    12)
                .getInt(),
            0,
            20);
        netherDungeonDifficulty = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Nether] Adjust secret rooms so they are more hidden [1 = less, 3 = most]",
                    2)
                .getInt(),
            1,
            3);
        secretRoomChanceNether = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Nether] Chance (as a percent) per iteration of secret room generating [1-100]",
                    80)
                .getInt(),
            1,
            100);
        minDistanceNether = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Nether] Minimum number of blocks between land-based secret rooms [2-16]",
                    6)
                .getInt(),
            2,
            16);
        minBossDistanceNether = MathHelper.clamp_int(
            config.get("Dungeon Generation", "[Nether] Minimum number of chunks between Boss Dungeons [8-64]", 12)
                .getInt(),
            8,
            64);
        genAttemptsPerChunkNether = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[Nether] Secret room generation attempts per chunk (0 to disable) [0-20]",
                    12)
                .getInt(),
            0,
            20);
        fairySpawnerChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "Chance (as a percent) for certain dungeons to have fairy spawners [0-100]",
                    10)
                .getInt(),
            0,
            100);
        resetSpawnerTime = MathHelper.clamp_int(
            config.get("Dungeon Generation", "Maximum number of days required for fairies to replenish [2-10]", 7)
                .getInt(),
            2,
            10);
        disableStructureGen = config
            .get(
                "Dungeon Generation",
                "[No-Gen] Disable structure and feature generation entirely within a specified zone",
                false)
            .getBoolean(false);
        noGenX = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[No-Gen] Starting chunk coordinate X for the structure free zone [max is +/- 1875000]",
                    0)
                .getInt(),
            -1875000,
            1875000);
        noGenZ = MathHelper.clamp_int(
            config
                .get(
                    "Dungeon Generation",
                    "[No-Gen] Starting chunk coordinate Z for the structure free zone [max is +/- 1875000]",
                    0)
                .getInt(),
            -1875000,
            1875000);
        /* ================== WORLD GEN ===================== */
        ancientTabletGenChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "WorldGen",
                    "[Ancient Tablet] Chance that a random tablet will spawn when a boss dungeon is defeated [0-100]",
                    20)
                .getInt(),
            0,
            100);
        // TODO ancientTabletGenDistance = MathHelper.clamp_int(config.get("WorldGen", "[Ancient Tablet] Maximum number
        // of chunks from boss dungeon a tablet may generate [0-8]", 0).getInt(), 0, 8);
        enableBombFlowerGen = config.get("WorldGen", "[Bomb Flowers] Enable bomb flower generation", true)
            .getBoolean(true);
        allowJarsInWater = config
            .get("WorldGen", "[Ceramic Jars][Surface] Allow ceramic jars to generate in water", true)
            .getBoolean(true);
        jarGenChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "WorldGen",
                    "[Ceramic Jars][Surface] Chance of generating a jar cluster in a given chunk [0-100]",
                    50)
                .getInt(),
            0,
            100);
        jarsPerCluster = MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Surface] Max number of jars per jar cluster [2-20]", 8)
                .getInt(),
            2,
            20);
        jarGenChanceSub = 0.01F * (float) MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Underground] Chance for each jar cluster to generate [0-100]", 65)
                .getInt(),
            0,
            100);
        jarsPerClusterSub = MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Underground] Max number of jars per cluster [2-20]", 8)
                .getInt(),
            2,
            20);
        jarClustersPerChunkSub = MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Underground] Max number of jar clusters per chunk [1-20]", 10)
                .getInt(),
            1,
            20);
        jarGenChanceNether = 0.01F * (float) MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Nether] Chance for each jar cluster to generate [0-100]", 50)
                .getInt(),
            0,
            100);
        jarsPerClusterNether = MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Nether] Max number of jars per cluster [2-20]", 8)
                .getInt(),
            2,
            20);
        jarClustersPerChunkNether = MathHelper.clamp_int(
            config.get("WorldGen", "[Ceramic Jars][Nether] Max number of jar clusters per chunk [1-20]", 8)
                .getInt(),
            1,
            20);
        gossipStoneRate = 0.0001F * (float) MathHelper.clamp_int(
            config
                .get(
                    "WorldGen",
                    "[Gossip Stones] Chance per chunk of a Gossip Stone generating (100 = 1% chance)[0-500]",
                    50)
                .getInt(),
            0,
            500);
        enablePillarGen = config.get("WorldGen", "[Song Pillars] Enable song and broken pillar generation", true)
            .getBoolean(true);
        maxPillarRange = MathHelper.clamp_int(
            config
                .get(
                    "WorldGen",
                    "[Song Pillars] Maximum search range; reduce if new chunks are loading too slowly [16-64]",
                    64)
                .getInt(),
            16,
            64);
        minBrokenPillarDistance = MathHelper.clamp_int(
            config.get("WorldGen", "[Song Pillars] Minimum number of chunks between broken pillars [4-128]", 32)
                .getInt(),
            4,
            128);
        minSongPillarDistance = MathHelper.clamp_int(
            config.get("WorldGen", "[Song Pillars] Minimum number of chunks between song pillars [8-128]", 64)
                .getInt(),
            8,
            128);
        /* ================== LOOT ===================== */
        grassDropChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) of loot dropping from grass [0-100]", 15)
                .getInt(),
            0,
            100);
        jarDropChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) of loot dropping from empty jars when broken [0-100]", 20)
                .getInt(),
            0,
            100);
        masterSwordChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) of a Forest Temple containing a Master Sword [1-100]", 33)
                .getInt(),
            1,
            100);
        lockedChestChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) a chest will be locked [0-100]", 33)
                .getInt(),
            0,
            100);
        doubleChestChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) a secret room may have two chests [0-100]", 10)
                .getInt(),
            0,
            100);
        barredRoomChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance that a secret room's entrance will be barred by some obstacle [0-100]", 25)
                .getInt(),
            0,
            100);
        heartPieceChance = 0.01F * (float) MathHelper.clamp_int(
            config.get("Loot", "Chance (as a percent) of a heart piece generating in secret room chests [0-100]", 15)
                .getInt(),
            0,
            100);
        randomBossItemChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "Loot",
                    "Chance (as a percent) of a random boss-level item being added to locked chest loot table [0-100]",
                    25)
                .getInt(),
            0,
            100);
        minNumChestItems = MathHelper.clamp_int(
            config.get("Loot", "Minimum number of random chest contents for first chest [1-10]", 4)
                .getInt(),
            1,
            10);
        bombWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Bomb [1-10]", 5)
                .getInt(),
            1,
            10);
        bombBagWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Bomb Bag (locked chest weight only) [1-10]", 3)
                .getInt(),
            1,
            10);
        heartPieceWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Heart Piece (vanilla chests only) [1-10]", 1)
                .getInt(),
            1,
            10);
        bigKeyWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Key, Big [1-10]", 4)
                .getInt(),
            1,
            10);
        smallKeyWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Key, Small [1-10]", 4)
                .getInt(),
            1,
            10);
        lockedLootWeight = MathHelper.clamp_int(
            config.get("Loot", "Weight: Locked Chest Content [1-10]", 3)
                .getInt(),
            1,
            10);
        for (SkillBase skill : SkillBase.getSkills()) {
            if (skill.isLoot() && config
                .get(
                    "Loot",
                    "[Skill Orbs] Whether " + skill.getDisplayName()
                        + " orbs may appear as random loot, such as in Boss chests",
                    true)
                .getBoolean(true)) {
                lootableOrbs.add(skill.getId());
            }
        }
        /* ================== TRADES ===================== */
        enableTradeBombBag = config
            .get("Trade", "[Bomb Bag] Allow Barnes to sell bomb bags (checked each time Barnes is shown a bomb)", true)
            .getBoolean(true);
        bombBagPrice = MathHelper.clamp_int(
            config
                .get("Trade", "[Bomb Bag] Cost of a bomb bag at Barnes' shop (only applied to new trades) [32-64]", 64)
                .getInt(),
            32,
            64);
        enableTradeBomb = config.get("Trade", "[Bombs] Enable random villager trades for bombs", true)
            .getBoolean(true);
        enableArrowTrades = config
            .get("Trade", "[Hero's Bow] Whether magic arrows (fire, ice, light) can be purchased", true)
            .getBoolean(true);
        maskBuyChance = 0.01F * (float) MathHelper.clamp_int(
            config
                .get(
                    "Trade",
                    "[Masks] Chance that a villager will be interested in purchasing a random mask [1-100]",
                    15)
                .getInt(),
            1,
            100);
        /* ================== MAP MAKING ===================== */
        config.addCustomCategoryComment(
            "Map Making",
            "Configuration settings related to map making; none of these have any impact on normal play.");
        config.save();
        Config.initSubConfigs(event);
    }

    private static void initSubConfigs(FMLPreInitializationEvent event) {
        /* ================== CLIENT SIDE SETTINGS ===================== */
        clientConfig = new Configuration(
            new File(
                event.getModConfigurationDirectory()
                    .getAbsolutePath() + ModInfo.CONFIG_PATH
                    + "zss_client.cfg"));
        clientConfig.load();
        clientConfig.addCustomCategoryComment(
            "_Client Settings_",
            "Client side settings can be set on a per-player basis and have no effect on a server."
                + "\nNote that HUD elements added by ZSS will dynamically adjust their position so they don't overlap with other ZSS HUD elements."
                + "\nAs such, it is generally recommended to leave the offset x and y at 0 or set them identically for each element with the same alignment.");
        showSecretMessage = clientConfig
            .get("_Client Settings_", "[Chat] Whether to show a chat message when striking secret blocks", false)
            .getBoolean(false);
        allowVanillaControls = clientConfig
            .get(
                "_Client Settings_",
                "[Controls] Whether to use vanilla movement keys to activate skills such as Dodge and Parry",
                true)
            .getBoolean(true);
        requireDoubleTap = clientConfig.get(
            "_Client Settings_",
            "[Controls] Whether Dodge and Parry require double-tap or not (double-tap always required with vanilla control scheme)",
            true)
            .getBoolean(true);
        resetNotesInterval = MathHelper.clamp_int(
            clientConfig
                .get(
                    "_Client Settings_",
                    "[Song GUI] Number of ticks allowed between notes before played notes are cleared [5 - 100]",
                    30)
                .getInt(),
            5,
            100);
        enableHookshotSound = clientConfig
            .get("_Client Settings_", "[Sounds] Whether to play the 'itembreak' sound when the hookshot misses", true)
            .getBoolean(true);
        buffBarMaxIcons = MathHelper.clamp_int(
            clientConfig.get("Buff HUD", "Maximum number of icons to display per row or column [1 - 10]", 5)
                .getInt(),
            1,
            10);
        isBuffBarEnabled = clientConfig.get("Buff HUD", "Whether the buff bar should be displayed at all times", true)
            .getBoolean(true);
        isBuffBarHorizontal = clientConfig
            .get("Buff HUD", "Whether the buff bar should be displayed horizontally", true)
            .getBoolean(true);
        buffBarHAlign = HALIGN.fromString(
            clientConfig.get("Buff HUD", "[Alignment: Horizontal] Alignment on the X axis [left|center|right]", "right")
                .getString());
        buffBarVAlign = VALIGN.fromString(
            clientConfig.get("Buff HUD", "[Alignment: Vertical] Alignment on the Y axis [top|center|bottom]", "top")
                .getString());
        buffBarOffsetX = clientConfig
            .get("Buff HUD", "[Offset: X] Moves the HUD element left (-) or right (+) this number of pixels", 0)
            .getInt();
        buffBarOffsetY = clientConfig
            .get("Buff HUD", "[Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels", 0)
            .getInt();
        isComboHudEnabled = clientConfig
            .get("Combo HUD", "Whether the combo hit counter will display by default (toggle in game: 'v')", true)
            .getBoolean(true);
        hitsToDisplay = MathHelper.clamp_int(
            clientConfig.get("Combo HUD", "Max hits to display in Combo HUD [0-12]", 3)
                .getInt(),
            0,
            12);
        comboHudHAlign = HALIGN.fromString(
            clientConfig.get("Combo HUD", "[Alignment: Horizontal] Alignment on the X axis [left|center|right]", "left")
                .getString());
        comboHudVAlign = VALIGN.fromString(
            clientConfig.get("Combo HUD", "[Alignment: Vertical] Alignment on the Y axis [top|center|bottom]", "top")
                .getString());
        comboHudOffsetX = clientConfig
            .get("Combo HUD", "[Offset: X] Moves the HUD element left (-) or right (+) this number of pixels", 0)
            .getInt();
        comboHudOffsetY = clientConfig
            .get("Combo HUD", "[Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels", 0)
            .getInt();
        isEndingBlowHudEnabled = clientConfig.get(
            "Ending Blow HUD",
            "Enable Ending Blow HUD display (if disabled, there is not any indication that the skill is ready to use))",
            true)
            .getBoolean(true);
        endingBlowHudHAlign = HALIGN.fromString(
            clientConfig
                .get("Ending Blow HUD", "[Alignment: Horizontal] Alignment on the X axis [left|center|right]", "center")
                .getString());
        endingBlowHudVAlign = VALIGN.fromString(
            clientConfig
                .get("Ending Blow HUD", "[Alignment: Vertical] Alignment on the Y axis [top|center|bottom]", "top")
                .getString());
        endingBlowHudOffsetX = clientConfig
            .get("Ending Blow HUD", "[Offset: X] Moves the HUD element left (-) or right (+) this number of pixels", 0)
            .getInt();
        endingBlowHudOffsetY = clientConfig
            .get("Ending Blow HUD", "[Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels", 30)
            .getInt();
        isItemModeEnabled = clientConfig
            .get(
                "Item Mode HUD",
                "Enable item mode HUD display (if disabled, mode may still be viewed in the item's tooltip)",
                true)
            .getBoolean(true);
        itemModeHAlign = HALIGN.fromString(
            clientConfig
                .get("Item Mode HUD", "[Alignment: Horizontal] Alignment on the X axis [left|center|right]", "left")
                .getString());
        itemModeVAlign = VALIGN.fromString(
            clientConfig
                .get("Item Mode HUD", "[Alignment: Vertical] Alignment on the Y axis [top|center|bottom]", "top")
                .getString());
        itemModeOffsetX = clientConfig
            .get("Item Mode HUD", "[Offset: X] Moves the HUD element left (-) or right (+) this number of pixels", 0)
            .getInt();
        itemModeOffsetY = clientConfig
            .get("Item Mode HUD", "[Offset: Y] Moves the HUD element up (-) or down (+) this number of pixels", 0)
            .getInt();
        clientConfig.addCustomCategoryComment(
            "Magic Meter",
            "Magic meter can be configured to display anywhere on the screen using the offset X and Y."
                + "\nOffsets are in relation to its horizontal and vertical alignments."
                + "\n\nDefault maximum width is 75 which is the same width as the hunger bar when the player has"
                + "\ngained magic points equal to or greater than 50 times the number of increments required."
                + "\nE.g., at 2 increments, the meter for a player with 100 mp will be at maximum width."
                + "\n==============================================="
                + "\nCommon Settings"
                + "\n==============================================="
                + "\nAbove Hunger Bar, drains from left to right: x=53, y=-40, orientation=true, orientation:mana=false, halign=center, valign=bottom"
                + "\nAbove Hunger Bar, drains from right to left: x=47, y=-40, orientation=true, orientation:mana=true, halign=center, valign=bottom"
                + "\nAny Corner: x=0, y=0, halign=left|right, valign=top|bottom");
        magicMeterHAlign = HALIGN.fromString(
            clientConfig
                .get("Magic Meter", "[Alignment: Horizontal] Alignment on the X axis [left|center|right]", "center")
                .getString());
        magicMeterVAlign = VALIGN.fromString(
            clientConfig
                .get("Magic Meter", "[Alignment: Vertical] Alignment on the Y axis [top|center|bottom]", "bottom")
                .getString());
        isMagicMeterTextEnabled = clientConfig.get("Magic Meter", "Enable text display of current Magic Points", false)
            .getBoolean(false);
        isMagicMeterEnabled = clientConfig.get("Magic Meter", "Enable the Magic Meter HUD display", true)
            .getBoolean(true);
        magicMeterOffsetX = clientConfig
            .get("Magic Meter", "[Offset: X] Moves the Meter left (-) or right (+) this number of pixels", 47)
            .getInt();
        magicMeterOffsetY = clientConfig
            .get("Magic Meter", "[Offset: Y] Moves the Meter up (-) or down (+) this number of pixels", -40)
            .getInt();
        isMagicMeterHorizontal = clientConfig
            .get("Magic Meter", "[Orientation] True for a horizontal magic meter, or false for a vertical one", true)
            .getBoolean(true);
        isMagicBarLeft = clientConfig.get(
            "Magic Meter",
            "[Orientation: Mana] True to drain mana from right-to-left or top-to-bottom depending on orientation; false for the opposite",
            true)
            .getBoolean(true);
        magicMeterWidth = clientConfig
            .get("Magic Meter", "[Width: Max] Maximum width of the magic meter [25 - 100]", 75)
            .getInt();
        magicMeterIncrements = clientConfig.get(
            "Magic Meter",
            "[Width: Increment] Number of increments required to max out the magic meter, where each increment is 50 magic points [1 - 10]",
            2)
            .getInt();
        targetMobs = clientConfig.get("Targeting", "Prioritize mobs over other entity types when targeting", true)
            .getBoolean(true);
        enableAutoTarget = clientConfig
            .get("Targeting", "Whether auto-targeting is enabled or not (toggle in game: '.')", true)
            .getBoolean(true);
        canTargetPlayers = clientConfig
            .get("Targeting", "Whether players can be targeted (toggle in game: '.' while sneaking)", true)
            .getBoolean(true);
        clientConfig.save();
        /* ================== MOBS ===================== */
        mobConfig = new Configuration(
            new File(
                event.getModConfigurationDirectory()
                    .getAbsolutePath() + ModInfo.CONFIG_PATH
                    + "zss_mobs.cfg"));
        mobConfig.load();
        // --- DROPS ---//
        creeperDrop = 0.01F * (float) MathHelper.clamp_int(
            mobConfig.get("Drops", "Chance (as a percent) for creepers to drop bombs [0-100]", 10)
                .getInt(),
            0,
            100);
        mobConsumableFrequency = MathHelper.clamp_int(
            mobConfig.get(
                "Drops",
                "Frequency of small heart and magic jar drops from mobs [zero to disable; 1 = rare, 10 = very common]",
                5)
                .getInt(),
            0,
            10);
        enableOrbDrops = mobConfig.get("Drops", "[Skill Orbs] Enable skill orbs to drop as loot from mobs", true)
            .getBoolean(true);
        randomDropChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig
                .get("Drops", "[Skill Orbs] Chance (as a percent) for specified mobs to drop a random orb [0-100]", 10)
                .getInt(),
            0,
            100);
        genericMobDropChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig.get("Drops", "[Skill Orbs] Chance (as a percent) for random mobs to drop a random orb [0-100]", 1)
                .getInt(),
            0,
            100);
        orbDropChance = new HashMap<Byte, Float>(SkillBase.getNumSkills());
        for (SkillBase skill : SkillBase.getSkills()) {
            if (skill.canDrop()) {
                int i = MathHelper.clamp_int(
                    mobConfig
                        .get(
                            "Drops",
                            "Chance (in tenths of a percent) for " + skill.getDisplayName()
                                + " to drop (0 to disable) [0-1000]",
                            5)
                        .getInt(),
                    0,
                    1000);
                orbDropChance.put(skill.getId(), (0.001F * (float) i));
            }
        }
        powerDropRate = Math.max(
            mobConfig.get(
                "Drops",
                "[Piece of Power] Approximate number of enemies you need to kill before a piece of power drops [minimum 20]",
                50)
                .getInt(),
            20);
        // TODO playerWhipLootChance = mobConfig.get("Drops", "[Whip] Chance that a random item may be stolen from
        // players, using a whip (0 to disable)[0-100]", 15).getInt();
        vanillaWhipLootChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig.get(
                "Drops",
                "[Whip] Chance that loot may be snatched from various vanilla mobs, using a whip (0 to disable)[0-100]",
                15)
                .getInt(),
            0,
            100);
        globalWhipLootChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig.get(
                "Drops",
                "[Whip] All whip-stealing chances are multiplied by this value, as a percentage, including any added by other mods (0 disables ALL whip stealing!)[0-500]",
                100)
                .getInt(),
            0,
            500);
        hurtOnSteal = mobConfig.get(
            "Drops",
            "[Whip] Whether to inflict damage to entities when stealing an item (IEntityLootable entities determine this separately)",
            true)
            .getBoolean(true);
        // --- SPAWNING ---///
        mobConfig.addCustomCategoryComment(
            "Mob Spawns",
            "Mobs use the 'Biome Type' lists to populate their individual spawn settings the first time the game is loaded.\nChanging the type lists after this point has no effect UNLESS you also delete the mob spawn locations in the\nconfig - this will force them to re-populate the next time the game is loaded.\nAlternatively, you may add new biomes directly to the individual mob spawn entries and completely ignore biome type.");
        roomSpawnMobChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig
                .get(
                    "Mob Spawns",
                    "Chance that a random mob will spawn inside of secret rooms (0 to disable) [0-100]",
                    25)
                .getInt(),
            0,
            100);
        keeseSwarmChance = 0.01F * (float) MathHelper.clamp_int(
            mobConfig.get("Mob Spawns", "[Keese] Chance of Keese spawning in a swarm (0 to disable)[0-100]", 25)
                .getInt(),
            0,
            100);
        keeseSwarmSize = MathHelper.clamp_int(
            mobConfig.get("Mob Spawns", "[Keese] Maximum number of Keese that can spawn in a swarm [4-16]", 6)
                .getInt(),
            4,
            16);
        minDaysToSpawnDarknut = 24000 * MathHelper.clamp_int(
            mobConfig.get("Mob Spawns", "Minimum number of days required to pass before Darknuts may spawn [0-30]", 7)
                .getInt(),
            0,
            30);
        minDaysToSpawnWizzrobe = 24000 * MathHelper.clamp_int(
            mobConfig.get("Mob Spawns", "Minimum number of days required to pass before Wizzrobes may spawn [0-30]", 7)
                .getInt(),
            0,
            30);
        mobConfig.save();
        /* ================== WEAPON REGISTRY ===================== */
        weaponConfig = new Configuration(
            new File(
                event.getModConfigurationDirectory()
                    .getAbsolutePath() + ModInfo.CONFIG_PATH
                    + "zss_weapon_registry.cfg"));
        weaponConfig.load();
        swords = weaponConfig.get(
            "Weapon Registry",
            "[Allowed Swords] Enter items as modid:registered_item_name, each on a separate line between the '<' and '>'",
            new String[0],
            "Register an item so that it is considered a SWORD by ZSS, i.e. it be used with skills that\nrequire swords, as well as other interactions that require swords, such as cutting grass.\nAll swords are also considered WEAPONS.")
            .getStringList();
        Arrays.sort(swords);
        weapons = weaponConfig.get(
            "Weapon Registry",
            "[Allowed Weapons] Enter items as modid:registered_item_name, each on a separate line between the '<' and '>'",
            new String[0],
            "Register an item as a generic melee WEAPON. This means it can be used for all\nskills except those that specifically require a sword, as well as some other things.")
            .getStringList();
        Arrays.sort(weapons);
        // Battlegear2 weapons ALL extend ItemSword, but are not really swords
        String[] forbidden = new String[] { "battlegear2:dagger.wood", "battlegear2:dagger.stone",
            "battlegear2:dagger.gold", "battlegear2:dagger.iron", "battlegear2:dagger.diamond", "battlegear2:mace.wood",
            "battlegear2:mace.stone", "battlegear2:mace.gold", "battlegear2:mace.iron", "battlegear2:mace.diamond",
            "battlegear2:spear.wood", "battlegear2:spear.stone", "battlegear2:spear.gold", "battlegear2:spear.iron",
            "battlegear2:spear.diamond", "battlegear2:waraxe.wood", "battlegear2:waraxe.stone",
            "battlegear2:waraxe.gold", "battlegear2:waraxe.iron", "battlegear2:waraxe.diamond" };
        forbidden_swords = weaponConfig.get(
            "Weapon Registry",
            "[Forbidden Swords] Enter items as modid:registered_item_name, each on a separate line between the '<' and '>'",
            forbidden,
            "Forbid one or more items from acting as SWORDs, e.g. if a mod item extends ItemSword but is not really a sword")
            .getStringList();
        Arrays.sort(forbidden_swords);
        forbidden_weapons = weaponConfig.get(
            "Weapon Registry",
            "[Forbidden Weapons] Enter items as modid:registered_item_name, each on a separate line between the '<' and '>'",
            new String[0],
            "Forbid one or more items from acting as WEAPONs, e.g. if an item is added by IMC and you don't want it to be usable with skills.\nNote that this will also prevent the item from behaving as a SWORD.")
            .getStringList();
        Arrays.sort(forbidden_weapons);
        weaponConfig.save();
    }

    public static void postInit() {
        // load boss types last because they rely on blocks, mobs, etc. to already have been initialized
        // other biome-related stuff just so all biomes can be sure to have loaded
        BossType.postInit(config);
        /* ================== SONGS ===================== */
        minSongIntervalStorm = MathHelper.clamp_int(
            config
                .get("Songs", "[Song of Storms] Time required between each use of the song (by anybody) [0-24000]", 600)
                .getInt(),
            0,
            24000);
        minSongIntervalSun = MathHelper.clamp_int(
            config.get("Songs", "[Sun's Song] Time required between each use of the song (by anybody) [0-24000]", 1200)
                .getInt(),
            0,
            24000);
        for (AbstractZeldaSong song : ZeldaSongs.getRegisteredSongs()) {
            if (!config
                .get(
                    "Songs",
                    "Whether " + song.getDisplayName()
                        + "'s main effect is enabled (does not affect notification of Song Blocks or Entities)",
                    true)
                .getBoolean(true)) {
                song.setIsEnabled(false);
            }
        }
        /* ================== MAP MAKING ===================== */
        String[] warp_locations = config.get("Map Making", Config.WARP_LOCATIONS_KEY, new String[0])
            .getStringList();
        for (String entry : warp_locations) {
            String[] split = entry.split(":");
            if (split.length != 2) {
                ZSSMain.logger.warn("Invalid default warp location entry: " + entry);
            } else {
                AbstractZeldaSong song = ZeldaSongs.getSongByName(split[0]);
                WarpPoint warp = WarpPoint.fromString(split[1]);
                if (song == null) {
                    ZSSMain.logger.warn("Default warp location entry contained invalid song name: " + split[0]);
                } else if (warp == null) {
                    ZSSMain.logger.warn(
                        "Default warp location entry contained invalid warp point entry: " + split[1]
                            + "\nExpected format is [dimension_id, x, y, z]");
                } else {
                    warp_defaults.put(song, warp);
                }
            }
        }
        if (config.hasChanged()) {
            config.save();
        }
        /* ================== MOB SPAWNING ===================== */
        BiomeType.postInit(mobConfig);
        ZSSEntities.postInit(mobConfig);
        if (mobConfig.hasChanged()) {
            mobConfig.save();
        }
        /* ================== WEAPON REGISTRY ===================== */
        // This doesn't modify the weapon registry config, so no need to save
        WeaponRegistry.INSTANCE.registerItems(swords, "Config", true);
        WeaponRegistry.INSTANCE.registerItems(weapons, "Config", false);
        WeaponRegistry.INSTANCE.forbidItems(forbidden_swords, "Config", true);
        WeaponRegistry.INSTANCE.forbidItems(forbidden_weapons, "Config", false);
    }

    /* ================== MOD INTER-COMPATIBILITY ===================== */
    public static boolean allowOffhandMaster() {
        return enableOffhandMaster;
    }

    /* ================== GENERAL ===================== */
    public static boolean canPlayersBeStunned() {
        return enableStunPlayer;
    }

    public static boolean affectAllSwings() {
        return enableSwingSpeed;
    }

    public static int getBaseSwingSpeed() {
        return baseSwingSpeed;
    }

    public static boolean canLiftSecretStone() {
        return enableSecretStoneLift;
    }

    public static boolean canLiftVanilla() {
        return enableVanillaLift;
    }

    public static boolean canSmashVanilla() {
        return enableVanillaSmash;
    }

    public static boolean alwaysPickupHearts() {
        return alwaysPickupHearts;
    }

    public static boolean isHardcoreZeldaFan() {
        return enableHardcoreZeldaFanMode;
    }

    public static float getBossHealthFactor() {
        return bossHealthFactor;
    }

    public static int getNumBosses() {
        return bossNumber;
    }

    public static boolean doJarsUpdate() {
        return enableJarUpdates;
    }

    public static int getSacredFlameRefreshRate() {
        return sacredRefreshRate;
    }

    public static int getSkulltulaRewardRate() {
        return skulltulaRewardRate;
    }

    public static boolean areVanillaBuffsDisabled() {
        return disableVanillaBuffs;
    }

    public static boolean areNpcsInvulnerable() {
        return npcsAreInvulnerable;
    }

    public static int getNaviRange() {
        return naviRange;
    }

    public static int getNaviFrequency() {
        return naviFrequency;
    }

    /* ================== ITEMS ===================== */
    public static boolean getArrowsConsumeFlame() {
        return arrowsConsumeFlame;
    }

    public static boolean onlyBombSecretStone() {
        return onlyBombSecretStone;
    }

    public static boolean canGriefAdventure() {
        return bombsGriefAdventure;
    }

    public static int getBombFuseTime() {
        return bombFuseTime;
    }

    public static boolean canBoomerangDenude() {
        return enableBoomerangDenude;
    }

    public static boolean canDekuDenude() {
        return enableDekuDenude;
    }

    public static boolean isDinIgniteEnabled() {
        return enableDinIgnite;
    }

    public static boolean isDinMeltEnabled() {
        return enableDinMelt;
    }

    public static boolean areUnenchantablesDisabled() {
        return disableAllUnenchantables;
    }

    public static boolean allowMegaSmashQuakeStone() {
        return enableMegaSmashQuake;
    }

    public static int getHeroBowUpgradeCost() {
        return heroBowUpgradeCost;
    }

    public static boolean enableFireArrowIgnite() {
        return enableFireArrowIgnite;
    }

    public static boolean enableFireArrowMelt() {
        return enableFireArrowMelt;
    }

    public static boolean enableLightArrowNoClip() {
        return enableLightArrowNoClip;
    }

    public static boolean enableAutoBombArrows() {
        return enableAutoBombArrows;
    }

    public static int getHookshotRange() {
        return hookshotRange;
    }

    public static boolean allowHookableOnly() {
        return enableHookableOnly;
    }

    public static boolean canHookshotBreakBlocks() {
        return enableHookshotBreakBlocks;
    }

    public static boolean doMedallionsDamagePlayers() {
        return medallionsAffectPlayers;
    }

    public static int getRodUpgradeCost() {
        return rodUpgradeCost;
    }

    public static boolean getRodFireGriefing() {
        return rodFireGriefing;
    }

    public static int getRequiredKills() {
        return temperedRequiredKills - 1;
    }

    public static boolean getMasterSwordsProvidePower() {
        return allMasterSwordsProvidePower;
    }

    public static int getNumSkelKeyUses() {
        return numSkelKeyUses;
    }

    public static int getSlingshotCostOne() {
        return slingshotUpgradeOne;
    }

    public static int getSlingshotCostTwo() {
        return slingshotUpgradeTwo;
    }

    public static int getWhipRange() {
        return whipRange;
    }

    /* ================== SKILLS ===================== */
    public static byte getMaxBonusHearts() {
        return (byte) maxBonusHearts;
    }

    public static boolean canDisarmorPlayers() {
        return allowDisarmorPlayer;
    }

    public static float getDisarmPenalty() {
        return disarmPenalty;
    }

    public static float getDisarmTimingBonus() {
        return disarmTimingBonus;
    }

    public static boolean allowUnlimitedNayru() {
        return allowUnlimitedNayru;
    }

    public static int getMaxMagicPoints() {
        return maxMagicPoints;
    }

    /** Returns amount of health that may be missing and still be able to activate certain skills (e.g. Sword Beam) */
    public static float getHealthAllowance(int level) {
        return (requireFullHealth ? 0.0F : (0.6F * level));
    }

    /* ================== SONGS ===================== */
    public static int getMinIntervalStorm() {
        return minSongIntervalStorm;
    }

    public static int getMinIntervalSun() {
        return minSongIntervalSun;
    }

    /* ================== DUNGEON GEN ===================== */
    public static boolean avoidModBlocks() {
        return avoidModBlocks;
    }

    public static boolean areWindowsEnabled() {
        return enableWindows;
    }

    public static boolean areBossDungeonsEnabled() {
        return enableBossDungeons;
    }

    public static boolean areBossDungeonsRandom() {
        return randomizeBossDungeons;
    }

    public static int getMinBossDistance() {
        return minBossDistance;
    }

    public static int getMinLandDistance() {
        return minLandDistance;
    }

    public static int getMinOceanDistance() {
        return minOceanDistance;
    }

    public static int getAttemptsPerChunk() {
        return genAttemptsPerChunk;
    }

    public static float getSecretRoomChance() {
        return secretRoomChance;
    }

    public static int getMainDungeonDifficulty() {
        return mainDungeonDifficulty;
    }

    public static int getNetherMinBossDistance() {
        return minBossDistanceNether;
    }

    public static int getNetherMinDistance() {
        return minDistanceNether;
    }

    public static int getNetherAttemptsPerChunk() {
        return genAttemptsPerChunkNether;
    }

    public static float getNetherSecretRoomChance() {
        return secretRoomChanceNether;
    }

    public static int getNetherDungeonDifficulty() {
        return netherDungeonDifficulty;
    }

    public static float getFairySpawnerChance() {
        return fairySpawnerChance;
    }

    public static int getDaysToRespawn() {
        return resetSpawnerTime;
    }

    /**
     * Returns true if structure/feature generation is enabled for the given chunk coordinates
     */
    public static boolean isGenEnabledAt(int chunkX, int chunkZ) {
        if (disableStructureGen) {
            return chunkX < noGenX || chunkZ < noGenZ;
        }
        return true;
    }

    /* ================== WORLD GEN ===================== */
    public static float getAncientTabletGenChance() {
        return ancientTabletGenChance;
    }

    public static int getAncientTabletGenDistance() {
        return ancientTabletGenDistance;
    }

    public static boolean doBombFlowerGen() {
        return enableBombFlowerGen;
    }

    public static boolean genJarsInWater() {
        return allowJarsInWater;
    }

    public static float getJarGenChance() {
        return jarGenChance;
    }

    public static int getJarsPerCluster() {
        return jarsPerCluster;
    }

    public static float getJarGenChanceSub() {
        return jarGenChanceSub;
    }

    public static int getJarClustersPerChunkSub() {
        return jarClustersPerChunkSub;
    }

    public static int getJarsPerClusterSub() {
        return jarsPerClusterSub;
    }

    public static float getJarGenChanceNether() {
        return jarGenChanceNether;
    }

    public static int getJarClustersPerChunkNether() {
        return jarClustersPerChunkNether;
    }

    public static int getJarsPerClusterNether() {
        return jarsPerClusterNether;
    }

    public static float getGossipStoneRate() {
        return gossipStoneRate;
    }

    public static boolean doPillarGen() {
        return enablePillarGen;
    }

    public static int getPillarRange() {
        return maxPillarRange;
    }

    public static int getBrokenPillarMin() {
        return minBrokenPillarDistance;
    }

    public static int getSongPillarMin() {
        return minSongPillarDistance;
    }

    /* ================== LOOT ===================== */
    public static float getGrassDropChance() {
        return grassDropChance;
    }

    public static float getJarDropChance() {
        return jarDropChance;
    }

    public static float getMasterSwordChance() {
        return masterSwordChance;
    }

    public static float getLockedChestChance() {
        return lockedChestChance;
    }

    public static float getDoubleChestChance() {
        return doubleChestChance;
    }

    public static float getBarredRoomChance() {
        return barredRoomChance;
    }

    public static float getHeartPieceChance() {
        return heartPieceChance;
    }

    public static float getRandomBossItemChance() {
        return randomBossItemChance;
    }

    public static int getMinNumItems() {
        return minNumChestItems;
    }

    public static int getBombWeight() {
        return bombWeight;
    }

    public static int getBombBagWeight() {
        return bombBagWeight;
    }

    public static int getHeartWeight() {
        return heartPieceWeight;
    }

    public static int getBigKeyWeight() {
        return bigKeyWeight;
    }

    public static int getSmallKeyWeight() {
        return smallKeyWeight;
    }

    public static int getLockedLootWeight() {
        return lockedLootWeight;
    }

    public static boolean isLootableSkill(SkillBase skill) {
        return lootableOrbs.contains(skill.getId());
    }

    /* ================== MOB DROPS ===================== */
    public static float getCreeperDropChance() {
        return creeperDrop;
    }

    public static int getMobConsumableFrequency() {
        return mobConsumableFrequency;
    }

    public static boolean areOrbDropsEnabled() {
        return enableOrbDrops;
    }

    public static float getChanceForRandomDrop() {
        return randomDropChance;
    }

    public static float getRandomMobDropChance() {
        return genericMobDropChance;
    }

    public static float getDropChance(int orbID) {
        return (orbDropChance.containsKey((byte) orbID) ? orbDropChance.get((byte) orbID) : 0.0F);
    }

    public static int getPowerDropRate() {
        return powerDropRate;
    }

    public static float getVanillaWhipLootChance() {
        return vanillaWhipLootChance;
    }

    public static float getWhipLootMultiplier() {
        return globalWhipLootChance;
    }

    public static boolean getHurtOnSteal() {
        return hurtOnSteal;
    }

    /* ================== MOB SPAWNING ===================== */
    public static float getRoomSpawnMobChance() {
        return roomSpawnMobChance;
    }

    public static float getKeeseSwarmChance() {
        return keeseSwarmChance;
    }

    public static int getKeeseSwarmSize() {
        return keeseSwarmSize;
    }

    public static int getTimeToSpawnDarknut() {
        return minDaysToSpawnDarknut;
    }

    public static int getTimeToSpawnWizzrobe() {
        return minDaysToSpawnWizzrobe;
    }

    /* ================== TRADES ===================== */
    public static boolean enableTradeBomb() {
        return enableTradeBomb;
    }

    public static boolean enableTradeBombBag() {
        return enableTradeBombBag;
    }

    public static int getBombBagPrice() {
        return bombBagPrice;
    }

    public static boolean areArrowTradesEnabled() {
        return enableArrowTrades;
    }

    public static float getMaskBuyChance() {
        return maskBuyChance;
    }

    /**
     * Returns the default warp point for the given song, if any
     */
    public static WarpPoint getDefaultWarpPoint(AbstractZeldaSong song) {
        return warp_defaults.get(song);
    }

    /**
     * Sets the default warp point for the given song, returning the previous default warp point, if any
     */
    public static WarpPoint setDefaultWarpPoint(AbstractZeldaSong song, WarpPoint warp) {
        Integer meta = BlockWarpStone.reverseLookup.get(song);
        if (meta != null) { // make sure song has a Warp Stone mapping
            WarpPoint previous = warp_defaults.put(song, warp);
            if (warp.equals(previous)) {
                return null; // nothing has changed
            }
            Config.saveDefaultWarpPoints();
            return previous;
        } else {
            ZSSMain.logger.warn("Attempted to set default warp point for non-warp song: " + song.getDisplayName());
        }
        return null;
    }

    /**
     * Saves default warp point map to the config file
     */
    private static void saveDefaultWarpPoints() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<AbstractZeldaSong, WarpPoint> entry : warp_defaults.entrySet()) {
            builder.append(
                entry.getKey()
                    .getUnlocalizedName())
                .append(":")
                .append(
                    entry.getValue()
                        .toString())
                .append("\n");
        }
        Property prop = config.get("Map Making", Config.WARP_LOCATIONS_KEY, new String[0]);
        prop.set(
            builder.toString()
                .split("\n"));
        config.getCategory("Map Making")
            .put(Config.WARP_LOCATIONS_KEY, prop);
        if (config.hasChanged()) {
            config.save();
        }
    }

    /**
     * Updates client settings from server packet
     */
    public static void syncClientSettings(SyncConfigPacket msg) {
        if (!msg.isMessageValid()) {
            ZSSMain.logger.error("Invalid SyncConfigPacket attempting to process!");
            return;
        }
        Config.enableOffhandMaster = msg.enableOffhandMaster;
        Config.enableStunPlayer = msg.enableStunPlayer;
        Config.enableSwingSpeed = msg.enableSwingSpeed;
        Config.enableSecretStoneLift = msg.enableSecretStoneLift;
        Config.enableVanillaLift = msg.enableVanillaLift;
        Config.enableVanillaSmash = msg.enableVanillaSmash;
        Config.disableAllUnenchantables = msg.disableAllUnenchantables;
        Config.enableHookableOnly = msg.enableHookableOnly;
        Config.requireFullHealth = msg.requireFullHealth;
        Config.baseSwingSpeed = msg.baseSwingSpeed;
        Config.bombFuseTime = msg.bombFuseTime;
        Config.hookshotRange = msg.hookshotRange;
        Config.whipRange = msg.whipRange;
        Config.allMasterSwordsProvidePower = msg.allMasterSwordsProvidePower;
    }
}
