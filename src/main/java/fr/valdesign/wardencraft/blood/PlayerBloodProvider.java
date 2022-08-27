package fr.valdesign.wardencraft.blood;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBloodProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerBlood> PLAYER_BLOOD = CapabilityManager.get(new CapabilityToken<PlayerBlood>() { });

    private PlayerBlood blood = null;
    private final LazyOptional<PlayerBlood> optional = LazyOptional.of(this::createPlayerBlood);

    private PlayerBlood createPlayerBlood() {
        if(this.blood == null) {
            this.blood = new PlayerBlood();
        }

        return this.blood;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_BLOOD) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBlood().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerBlood().loadNBTData(nbt);
    }
}
