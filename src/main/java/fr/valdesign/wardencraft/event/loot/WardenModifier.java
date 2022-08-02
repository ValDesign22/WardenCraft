package fr.valdesign.wardencraft.event.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.valdesign.wardencraft.item.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class WardenModifier extends LootModifier {

    public static final Codec<WardenModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst)
            .apply(inst, WardenModifier::new));

    protected WardenModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(ModItems.WARDEN_HEART.get(), 1));
        return generatedLoot;
    }

    @Override
    public Codec<WardenModifier> codec() {
        return CODEC;
    }
}