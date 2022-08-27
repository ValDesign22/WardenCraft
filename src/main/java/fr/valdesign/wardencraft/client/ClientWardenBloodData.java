package fr.valdesign.wardencraft.client;

public class ClientWardenBloodData {
    private static int playerWardenBlood;

    public static void set(int blood) {
        ClientWardenBloodData.playerWardenBlood = blood;
    }

    public static int getPlayerWardenBlood() {
        return playerWardenBlood;
    }
}
