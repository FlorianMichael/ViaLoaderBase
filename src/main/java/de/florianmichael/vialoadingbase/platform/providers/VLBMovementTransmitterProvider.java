/*
 * This file is part of ViaLoadingBase - https://github.com/FlorianMichael/ViaLoadingBase
 * Copyright (C) 2022-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.vialoadingbase.platform.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class VLBMovementTransmitterProvider extends MovementTransmitterProvider {
    @Override
    public Object getFlyingPacket() {
        return null;
    }

    @Override
    public Object getGroundPacket() {
        return null;
    }

    @Override
    public void sendPlayer(UserConnection userConnection) {
        if (userConnection.getProtocolInfo().getState() != State.PLAY) return;

        final int playerId = userConnection.getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
        if (playerId == -1) return; // Only apply if the player is initialized

        final MovementTracker movementTracker = userConnection.get(MovementTracker.class);
        movementTracker.incrementIdlePacket();

        userConnection.getChannel().eventLoop().submit(() -> { // Sync
            final PacketWrapper c03 = PacketWrapper.create(ServerboundPackets1_8.PLAYER_MOVEMENT, userConnection);
            c03.write(Type.BOOLEAN, movementTracker.isGround());

            try {
                c03.sendToServer(Protocol1_9To1_8.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}