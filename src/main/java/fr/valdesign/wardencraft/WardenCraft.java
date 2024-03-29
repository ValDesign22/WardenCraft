package fr.valdesign.wardencraft;

import com.mojang.logging.LogUtils;
import fr.valdesign.wardencraft.block.ModBlocks;
import fr.valdesign.wardencraft.enchantment.ModEnchantments;
import fr.valdesign.wardencraft.entities.ModEntities;
import fr.valdesign.wardencraft.entities.client.WardianRenderer;
import fr.valdesign.wardencraft.item.ModItems;
import fr.valdesign.wardencraft.networking.ModMessages;
import fr.valdesign.wardencraft.villager.ModPOIs;
import fr.valdesign.wardencraft.world.dimension.ModDimensions;
import fr.valdesign.wardencraft.world.feature.ModConfiguredFeatures;
import fr.valdesign.wardencraft.world.feature.ModPlacedFeatures;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(WardenCraft.MOD_ID)
public class WardenCraft
{
    public static final String MOD_ID = "wardencraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WardenCraft()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDimensions.register();

        ModEntities.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModPOIs.register(modEventBus);
        ModEnchantments.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModMessages::register);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("WardenCraft loaded successfully");

            EntityRenderers.register(ModEntities.WARDIAN.get(), WardianRenderer::new);
        }
    }
}
