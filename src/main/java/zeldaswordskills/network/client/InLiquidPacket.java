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

package zeldaswordskills.network.client;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import cpw.mods.fml.relauncher.Side;
import zeldaswordskills.network.AbstractMessage.AbstractClientMessage;

/**
 * 
 * Getting around derpiness of Minecraft: client-side world.getMaterial returns
 * incorrect value and is unable to detect liquids until player is submerged,
 * regardless of position parameters passed.
 * <br>
 * <br>
 * This packet should be received on the client side when the player is standing
 * in liquid wearing Heavy Boots or something with an equivalent effect. Client
 * will trust that the server sent the packet in good faith.
 * <br>
 * <br>
 * Causes player to sink quickly and struggle to swim upward, as well as to have
 * increased movement speed when in liquid but touching the ground.
 *
 */
public class InLiquidPacket extends AbstractClientMessage<InLiquidPacket> {

    /** If this is true, the magnitude of velocity increase will be higher */
    private boolean inLava;

    public InLiquidPacket() {}

    public InLiquidPacket(boolean inLava) {
        this.inLava = inLava;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        inLava = buffer.readBoolean();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeBoolean(inLava);
    }

    @Override
    protected void process(EntityPlayer player, Side side) {
        if (player.onGround) {
            double d = (this.inLava ? 1.75D : 1.125D);
            player.motionX *= d;
            player.motionZ *= d;
        } else if (player.motionY < 0 && !Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed()) {
            player.motionY *= 1.5D;
            if (player.motionY < -0.35D) {
                player.motionY = -0.35D;
            }
        } else {
            player.motionY *= 0.7D;
        }
    }
}
