package de.melanx.utilitix.content.wireless;

import de.melanx.utilitix.registration.ModBlocks;
import de.melanx.utilitix.registration.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.ticks.TickPriority;
import org.moddingx.libx.base.tile.BlockEntityBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class TileLinkedRepeater extends BlockEntityBase {

    private ItemStack link = ItemStack.EMPTY;

    public TileLinkedRepeater(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.link = ItemStack.of(nbt.getCompound("Link"));
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        compound.put("Link", this.link.serializeNBT());
    }

    public ItemStack getLink() {
        return this.link.copy();
    }

    public void setLink(ItemStack link) {
        UUID oldId = this.getLinkId();
        this.link = link.copy();
        UUID newId = this.getLinkId();
        if (oldId != newId && this.level != null && !this.level.isClientSide) {
            WirelessStorage storage = WirelessStorage.get(this.level);
            storage.remove(this.level, oldId, GlobalPos.of(this.level.dimension(), this.worldPosition));
            if (newId != null) {
                storage.update(this.level, newId, GlobalPos.of(this.level.dimension(), this.worldPosition), BlockLinkedRepeater.inputStrength(this.level, this.getBlockState(), this.worldPosition));
            }
            BlockState state = this.getBlockState().setValue(BlockStateProperties.EYE, newId != null);
            this.level.setBlock(this.worldPosition, state, 3);
            this.level.scheduleTick(this.worldPosition, ModBlocks.linkedRepeater, 1, TickPriority.EXTREMELY_HIGH);
        }
        this.setChanged();
    }
    
    @Nullable
    public UUID getLinkId() {
        if (!this.link.isEmpty() && this.link.getItem() == ModItems.linkedCrystal) {
            return ItemLinkedCrystal.getId(this.link);
        } else {
            return null;
        }
    }
}
