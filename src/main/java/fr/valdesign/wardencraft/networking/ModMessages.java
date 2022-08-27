package fr.valdesign.wardencraft.networking;

import fr.valdesign.wardencraft.WardenCraft;
import fr.valdesign.wardencraft.networking.packet.DrinkBloodC2SPacket;
import fr.valdesign.wardencraft.networking.packet.BloodDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(WardenCraft.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;
        net.messageBuilder(DrinkBloodC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkBloodC2SPacket::new)
                .encoder(DrinkBloodC2SPacket::toBytes)
                .consumerMainThread(DrinkBloodC2SPacket::handle)
                .add();

        net.messageBuilder(BloodDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BloodDataSyncS2CPacket::new)
                .encoder(BloodDataSyncS2CPacket::toBytes)
                .consumerMainThread(BloodDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}