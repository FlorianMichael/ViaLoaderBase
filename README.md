# ViaProtocolHack
Universal ViaVersion standalone implementation

## Projects where this is used:
1. ViaForge: Clientside ViaVersion for Forge

## Example implementation:
```java
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import de.florianmichael.viaprotocolhack.INativeProvider;
import de.florianmichael.viaprotocolhack.ViaProtocolHack;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Test implements INativeProvider {

    public void main() throws Exception {
        ViaProtocolHack.instance().init(this, () -> {
            System.out.println("ViaProtocolHack is finished");
        });
    }

    @Override
    public boolean isSinglePlayer() {
        return Minecraft.getMinecraft().isInSingleplayer(); // for VersionList
    }

    @Override
    public int realClientsideVersion() {
        return 47; // the target version you want to connect
    }

    @Override
    public String[] nettyOrder() {
        return new String[] {
                "compress",
                "decompress"
        }; // namings of minecrafts compressing and decompressing from the pipeline
    }

    @Override
    public File run() {
        return Minecraft.getMinecraft().mcDataDir; // data dir for via
    }

    @Override
    public JsonObject createDump() {
        return new JsonObject(); // not important since commands aren't implemented by default
    }

    @Override
    public void createProviders(ViaProviders providers) {
        providers.register(VersionProvider.class, new BaseVersionProvider() {
            @Override
            public int getClosestServerProtocol(UserConnection connection) throws Exception {
                return ViaProtocolHack.instance().provider().realClientsideVersion();
            }
        }); // simple version provider without anything special

        // this is completely unlegit and removed in my private implementation lmao
        providers.register(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
    }

    @Override
    public Optional<ViaCommandHandler> commandHandler() {
        return Optional.empty(); // in case you want commands
    }

    @Override
    public List<ProtocolVersion> optionalVersions() {
        return null; // in case you want custom protocols like 1.7
    }
}
```
