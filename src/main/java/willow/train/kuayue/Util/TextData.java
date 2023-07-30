package willow.train.kuayue.Util;

import net.minecraft.core.BlockPos;

public class TextData{
    public BlockPos pos;
    public String[] data;

    public TextData(BlockPos pos, String[] data){
        this.pos = pos;
        this.data = data;
    }
}
