package fr.valdesign.wardencraft.networking.packet;

import fr.valdesign.wardencraft.client.ClientWardenBloodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BloodDataSyncS2CPacket {
    private final int blood;

    public BloodDataSyncS2CPacket(int blood) {
        this.blood = blood;
    }

    public BloodDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.blood = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(blood);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientWardenBloodData.set(blood);
        });
        return true;
    }
}
