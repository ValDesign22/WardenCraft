package fr.valdesign.wardencraft.blood;

import net.minecraft.nbt.CompoundTag;

public class PlayerBlood {
    private int blood;

    public int getBlood() {
        return blood;
    }

    public void addBlood(int add) {
        int MAX_BLOOD = 10;
        this.blood = Math.min(blood + add, MAX_BLOOD);
    }

    public void subBlood(int sub) {
        int MIN_BLOOD = 0;
        this.blood = Math.max(blood - sub, MIN_BLOOD);
    }

    public void copyFrom(PlayerBlood source) {
        this.blood = source.blood;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("blood", blood);
    }

    public void loadNBTData(CompoundTag nbt) {
        blood = nbt.getInt("blood");
    }
}
