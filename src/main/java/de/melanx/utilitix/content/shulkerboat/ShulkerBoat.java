package de.melanx.utilitix.content.shulkerboat;

import de.melanx.utilitix.registration.ModEntities;
import de.melanx.utilitix.registration.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShulkerBoat extends ChestBoat {

    public ShulkerBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public ShulkerBoat(Level level, Vec3 pos) {
        this(ModEntities.shulkerBoat, level);
        this.setPos(pos);
        this.xo = pos.x;
        this.yo = pos.y;
        this.zo = pos.z;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @Nonnull Inventory inventory, @Nonnull Player player) {
        if (this.getLootTable() != null && player.isSpectator()) {
            return null;
        }

        this.unpackLootTable(player);
        return new ShulkerBoxMenu(id, inventory, this);
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }

        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        }

        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + amount * 10);
        this.markHurt();
        this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
        boolean creative = source.getEntity() instanceof Player player && player.getAbilities().instabuild;
        if (creative || this.getDamage() > 40) {
            if ((!creative || this.hasItems()) && (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS) || this.hasItems())) {
                this.destroy(source);
            }

            this.discard();
        }

        return true;
    }

    private boolean hasItems() {
        for (ItemStack item : this.getItemStacks()) {
            if (!item.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        return switch (this.getVariant()) {
            case SPRUCE -> ModItems.spruceShulkerBoat;
            case BIRCH -> ModItems.birchShulkerBoat;
            case JUNGLE -> ModItems.jungleShulkerBoat;
            case ACACIA -> ModItems.acaciaShulkerBoat;
            case CHERRY -> ModItems.cherryShulkerBoat;
            case DARK_OAK -> ModItems.darkOakShulkerBoat;
            case MANGROVE -> ModItems.mangroveShulkerBoat;
            case BAMBOO -> ModItems.bambooShulkerRaft;
            default -> ModItems.oakShulkerBoat;
        };
    }

    @Override
    public void destroy(@Nonnull DamageSource source) {
        ItemStack drop = new ItemStack(this.getDropItem());
        CompoundTag itemTag = drop.getOrCreateTag();
        CompoundTag items = new CompoundTag();
        ContainerHelper.saveAllItems(items, this.getItemStacks());
        itemTag.put("Items", items);
        if (this.hasCustomName()) {
            //noinspection ConstantConditions
            itemTag.putString("CustomName", this.getCustomName().getString());
        }
        this.spawnAtLocation(drop);
    }

    @Override
    public void remove(@Nonnull RemovalReason reason) {
        this.setRemoved(reason);
        this.invalidateCaps();
    }
}
