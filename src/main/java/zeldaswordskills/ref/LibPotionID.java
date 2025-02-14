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

package zeldaswordskills.ref;

public enum LibPotionID {

    /*
     * POTION DATA VALUES from http://www.minecraftwiki.net/wiki/Potions
     * EXT means "Extended" version of potion
     * REV means "Reverted" version of potion
     */
    AWKWARD(16),
    THICK(32),
    MUNDANE(128),
    MUNDANE_EXT(64),

    /*
     * HELPFUL POTIONS
     */
    REGEN(8193),
    REGEN_II(8225),
    REGEN_EXT(8257),
    REGEN_II_EXT(8289),
    REGEN_SPLASH(16385),
    REGEN_SPLASH_II(16417),
    REGEN_SPLASH_EXT(16449),

    SWIFTNESS(8194),
    SWIFTNESS_II(8226),
    SWIFTNESS_EXT(8258),
    SWIFTNESS_II_EXT(8290),
    SWIFTNESS_SPLASH(16386),
    SWIFTNESS_SPLASH_II(16418),
    SWIFTNESS_SPLASH_EXT(16450),

    FIRERESIST(8195),
    FIRERESIST_REV(8227),
    FIRERESIST_EXT(8259),
    FIRERESIST_SPLASH(16387),
    FIRERESIST_SPLASH_REV(16419),
    FIRERESIST_SPLASH_EXT(16451),

    HEALING(8197),
    HEALING_II(8229),
    HEALING_REV(8261),
    HEALING_SPLASH(16389),
    HEALING_SPLASH_II(16421),
    HEALING_SPLASH_REV(16453),

    NIGHTVISION(8198),
    NIGHTVISION_REV(8230),
    NIGHTVISION_EXT(8262),
    NIGHTVISION_SPLASH(16390),
    NIGHTVISION_SPLASH_REV(16422),
    NIGHTVISION_SPLASH_EXT(16454),

    STRENGTH(8201),
    STRENGTH_II(8233),
    STRENGTH_EXT(8265),
    STRENGTH_II_EXT(8292),
    STRENGTH_SPLASH(16393),
    STRENGTH_SPLASH_II(16425),
    STRENGTH_SPLASH_EXT(16457),

    INVISIBILITY(8206),
    INVISIBILITY_REV(8238),
    INVISIBILITY_EXT(8270),
    INVISIBILITY_SPLASH(16398),
    INVISIBILITY_SPLASH_REV(16430),
    INVISIBILITY_SPLASH_EXT(16462),

    WATER_BREATHING(8205),
    WATER_BREATHING_EXT(8269),
    WATER_BREATHING_SPLASH(16397),
    WATER_BREATHING_SPLASH_EXT(16461),

    /*
     * HARMFUL POTIONS
     */
    POISON(8196),
    POISON_II(8228),
    POISON_EXT(8260),
    POISON_SPLASH(16388),
    POISON_SPLASH_II(16420),
    POISON_SPLASH_EXT(16452),

    WEAKNESS(8200),
    WEAKNESS_REV(8232),
    WEAKNESS_EXT(8264),
    WEAKNESS_SPLASH(16392),
    WEAKNESS_SPLASH_REV(16424),
    WEAKNESS_SPLASH_EXT(16456),

    SLOWNESS(8202),
    SLOWNESS_REV(8234),
    SLOWNESS_EXT(8266),
    SLOWNESS_SPLASH(16394),
    SLOWNESS_SPLASH_REV(16426),
    SLOWNESS_SPLASH_EXT(16458),

    HARM(8204),
    HARM_II(8236),
    HARM_REV(8268),
    HARM_SPLASH(16396),
    HARM_SPLASH_II(16428),
    HARM_SPLASH_REV(16460);

    public final int id;

    private LibPotionID(int id) {
        this.id = id;
    }
}
