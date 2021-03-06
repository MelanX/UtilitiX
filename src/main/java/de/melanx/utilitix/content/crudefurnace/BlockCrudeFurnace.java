package de.melanx.utilitix.content.crudefurnace;

import de.melanx.utilitix.registration.ModBlocks;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.moddingx.libx.base.tile.MenuBlockBE;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockCrudeFurnace extends MenuBlockBE<TileCrudeFurnace, ContainerMenuCrudeFurnace> {

    public BlockCrudeFurnace(ModX mod, MenuType<ContainerMenuCrudeFurnace> menu, Properties properties) {
        super(mod, TileCrudeFurnace.class, menu, properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(AbstractFurnaceBlock.LIT, false));
    }

    @Override
    public void registerClient(SetupContext ctx) {
        MenuScreens.register(ModBlocks.crudeFurnace.menu, ScreenCrudeFurnace::new);
    }

    @Override
    protected boolean shouldDropInventory(Level level, BlockPos pos, BlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState blockState, @Nonnull Level level, @Nonnull BlockPos pos) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof TileCrudeFurnace) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(((BaseItemStackHandler) ((TileCrudeFurnace) tile).getUnrestricted()).toVanilla());
        }

        return super.getAnalogOutputSignal(blockState, level, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING, AbstractFurnaceBlock.LIT);
    }
}
