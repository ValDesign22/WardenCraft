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

public class WardianLootModifier extends LootModifier {

    public static final Codec<WardianLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst)
            .apply(inst, WardianLootModifier::new));

    protected WardianLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        int dropCount = (int) (Math.random() * 5) + 1;
        int chanceTen = (int) (Math.random() * 10) + 1;
        generatedLoot.add(new ItemStack(ModItems.WARDEN_HEART.get(), dropCount));

        if (chanceTen < 1) {
            generatedLoot.add(new ItemStack(ModItems.WARDIAN_STAFF.get(), 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<WardianLootModifier> codec() {
        return CODEC;
    }
}