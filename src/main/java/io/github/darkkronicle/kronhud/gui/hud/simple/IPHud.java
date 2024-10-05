package io.github.darkkronicle.kronhud.gui.hud.simple;

import io.github.darkkronicle.kronhud.gui.entry.SimpleTextHudEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class IPHud extends SimpleTextHudEntry {

    public static final Identifier ID = Identifier.of("kronhud", "iphud");

    public IPHud() {
        super(115, 13);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public String getValue() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.isInSingleplayer()) {
            return "singleplayer";
        }
        if (client.getCurrentServerEntry() == null) {
            return "none";
        }
        return client.getCurrentServerEntry().address;
    }

    @Override
    public String getPlaceholder() {
        return "singleplayer";
    }
}
