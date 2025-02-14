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

package zeldaswordskills.api.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Interface for items that should zoom in like the bow when in use
 *
 */
public interface IZoom {

    /**
     * The number of ticks required before maximum zoom is reached,
     * as a float value (vanilla bow is 20.0F)
     */
    @SideOnly(Side.CLIENT)
    float getMaxZoomTime();

    /**
     * The factor by which the field of view will be modified;
     * vanilla bow uses 0.15F, with higher values giving higher magnification
     */
    @SideOnly(Side.CLIENT)
    float getZoomFactor();

}
