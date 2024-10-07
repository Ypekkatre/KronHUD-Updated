package io.github.darkkronicle.kronhud.gui.hud.item;

import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import io.github.darkkronicle.kronhud.KronHUD;
import io.github.darkkronicle.kronhud.config.KronConfig;
import io.github.darkkronicle.kronhud.gui.entry.TextHudEntry;
import io.github.darkkronicle.kronhud.util.DrawPosition;
import io.github.darkkronicle.kronhud.util.ItemUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;

import java.util.List;

public class ArmorHud extends TextHudEntry {

    public static final Identifier ID = new Identifier("kronhud", "armorhud");

    public ArmorHud() {
        super(20, 100, true);
    }

    @Override
    public void renderComponent(DrawContext context, float delta) {
        DrawPosition pos = getPos();
        int lastY = 2 + (4 * 20);
        renderMainItem(context, client.player.getInventory().getMainHandStack(), pos.x() + 2, pos.y() + lastY);
        lastY = lastY - 20;
        for (int i = 0; i <= 3; i++) {
            ItemStack item = client.player.getInventory().armor.get(i);
            renderItem(context, item, pos.x() + 2, lastY + pos.y());
            lastY = lastY - 20;
        }
    }

    public void renderItem(DrawContext context, ItemStack stack, int x, int y) {
        RenderUtil.drawItem(context, stack, x, y);
        context.drawItemInSlot(client.textRenderer, stack, x, y, null);
    }

    public void renderMainItem(DrawContext context, ItemStack stack, int x, int y) {
        RenderUtil.drawItem(context, stack, x, y);
        int total = ItemUtil.getTotal(client, stack);;

        if (stack.getMaxCount() == 1) {
            Item item = stack.getItem();
            if (item instanceof PotionItem potionitem){
                total = 0;
                List<StatusEffectInstance> effects = PotionUtil.getPotion(stack).getEffects();
                boolean isSplash = potionitem == Items.SPLASH_POTION;
                boolean isLingering = potionitem == Items.LINGERING_POTION;
                for (ItemStack st : ItemUtil.getItems(client)){
                    if (st == null) continue;
                    Item it = st.getItem();
                    if (it == null) continue;
                    if (it instanceof PotionItem potionitem2){
                        boolean isSplash2 = potionitem2 == Items.SPLASH_POTION;
                        boolean isLingering2 = potionitem2 == Items.LINGERING_POTION;
                        if (PotionUtil.getPotion(st).getEffects().equals(effects)
                                && isSplash == isSplash2
                                && isLingering == isLingering2) total += 1;
                    }
                }
            }
        }

        context.drawItemInSlot(client.textRenderer, stack, x, y, total > 1 ? String.valueOf(total) : null);
    }

    @Override
    public void renderPlaceholderComponent(DrawContext context, float delta) {
        DrawPosition pos = getPos();
        int lastY = 2 + (4 * 20);
        RenderUtil.drawItem(context, new ItemStack(Items.GRASS_BLOCK), pos.x() + 2, pos.y() + lastY);
        context.drawItemInSlot(client.textRenderer, new ItemStack(Items.GRASS_BLOCK), pos.x() + 2,
                pos.y() + lastY, "90");
    }

    @Override
    public boolean movable() {
        return true;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public List<KronConfig<?>> getConfigurationOptions() {
        List<KronConfig<?>> options = super.getConfigurationOptions();
        options.remove(textColor);
        options.remove(shadow);
        return options;
    }
}
