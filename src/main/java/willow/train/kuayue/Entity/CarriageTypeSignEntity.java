package willow.train.kuayue.Entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import willow.train.kuayue.init.BlockEntitiesInit;

import javax.annotation.Nullable;
import java.util.function.Function;

public class CarriageTypeSignEntity extends BlockEntity {

    public static final int LINES = 4;

    private boolean isEditable = true;
    @Nullable
    private FormattedCharSequence[] renderMessages;

    private static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4"};
    private static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};

    private DyeColor color = DyeColor.YELLOW;

    private final Component[] messages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};

    private final Component[] filteredMessages = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
    private boolean renderMessagedFiltered;

    public CarriageTypeSignEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesInit.CarriageTypeSign.get(), pPos, pBlockState);
    }

    public Component getMessage(int pIndex, boolean pFiltered) {
        return this.getMessages(pFiltered)[pIndex];
    }

    private Component[] getMessages(boolean pFiltered) {
        return pFiltered ? this.filteredMessages : this.messages;
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        for(int i = 0; i < 4; ++i) {
            Component component = this.messages[i];
            String s = Component.Serializer.toJson(component);
            pTag.putString(RAW_TEXT_FIELD_NAMES[i], s);
            Component component1 = this.filteredMessages[i];
            if (!component1.equals(component)) {
                pTag.putString(FILTERED_TEXT_FIELD_NAMES[i], Component.Serializer.toJson(component1));
            }
        }

        pTag.putString("Color", this.color.getName());
    }

    public void load(CompoundTag pTag) {
        this.isEditable = false;
        super.load(pTag);
        this.color = DyeColor.byName(pTag.getString("Color"), DyeColor.BLACK);

        for(int i = 0; i < 4; ++i) {
            String s = pTag.getString(RAW_TEXT_FIELD_NAMES[i]);
            Component component = this.loadLine(s);
            this.messages[i] = component;
            String s1 = FILTERED_TEXT_FIELD_NAMES[i];
            if (pTag.contains(s1, 8)) {
                this.filteredMessages[i] = this.loadLine(pTag.getString(s1));
            } else {
                this.filteredMessages[i] = component;
            }
        }

        this.renderMessages = null;
    }

    private Component loadLine(String pLine) {
        Component component = this.deserializeTextSafe(pLine);
        return component;
    }

    private Component deserializeTextSafe(String pText) {
        try {
            Component component = Component.Serializer.fromJson(pText);
            if (component != null) {
                return component;
            }
        } catch (Exception exception) {
        }

        return TextComponent.EMPTY;
    }

    public FormattedCharSequence[] getRenderMessages(boolean pRenderMessagedFiltered, Function<Component, FormattedCharSequence> pMessageTransformer) {
        if (this.renderMessages == null || this.renderMessagedFiltered != pRenderMessagedFiltered) {
            this.renderMessagedFiltered = pRenderMessagedFiltered;
            this.renderMessages = new FormattedCharSequence[4];

            for(int i = 0; i < 4; ++i) {
                this.renderMessages[i] = pMessageTransformer.apply(this.getMessage(i, pRenderMessagedFiltered));
            }
        }

        return this.renderMessages;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public boolean setColor(DyeColor pColor) {
        if (pColor != this.getColor()) {
            this.color = pColor;
            this.markUpdated();
            return true;
        } else {
            return false;
        }
    }

    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean hasGlowingText() {
        return false;
    }
}
