/*
 * This file is part of ViaLoadingBase - https://github.com/FlorianMichael/ViaLoadingBase
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
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
package de.florianmichael.vialoadingbase.defaults.viaversion;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import de.florianmichael.vialoadingbase.netty.NettyConstants;

public class VLBViaInjector implements ViaInjector {

    @Override
    public void inject() {
        // Implemented by Mixins
    }

    @Override
    public void uninject() {
        // ICM
    }

    @Override
    public String getEncoderName() {
        return NettyConstants.HANDLER_ENCODER_NAME;
    }

    @Override
    public String getDecoderName() {
        return NettyConstants.HANDLER_DECODER_NAME;
    }

    @Override
    public IntSortedSet getServerProtocolVersions() {
        final IntSortedSet versions = new IntLinkedOpenHashSet();
        for (ProtocolVersion value : ProtocolVersion.getProtocols()) {
            if (value.getVersion() >= ProtocolVersion.v1_7_1.getVersion()) {
                versions.add(value.getVersion());
            }
        }

        return versions;
    }

    @Override
    public int getServerProtocolVersion() {
        return this.getServerProtocolVersions().firstInt();
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}