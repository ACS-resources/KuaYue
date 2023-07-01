package willow.train.kuayue.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import willow.train.kuayue.Main;

import java.io.*;
import java.util.HashSet;

public class Save {
    public static void saveText(Level level, TextData textData){
        File f = new File("/saves/" + "test" + "/" + Main.MOD_ID + "/kuayue");
        if (!f.exists()){
            f.mkdir();
        }

        File file = new File("savepoint.dat");
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){}
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeInt(3 + textData.data.length);
            oos.writeInt(textData.pos.getX());
            oos.writeInt(textData.pos.getY());
            oos.writeInt(textData.pos.getZ());
            for (String str : textData.data){
                oos.writeObject(str);
            }
            oos.flush();
            oos.close();
        }catch (Exception e){}
    }

    public static TextData[] readText(Level level){
        File file = new File("savepoint.dat");
        if (!file.exists()){
            return null;
        }

        HashSet<TextData> datas = new HashSet<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            int pointer = 0;

            while(true) {
                int length = ois.readInt();
                int x = ois.readInt();
                int y = ois.readInt();
                int z = ois.readInt();
                BlockPos blockPos = new BlockPos(x, y, z);
                String[] dataList = new String[length-3];

                for(int i = 3 + pointer; i<length + pointer; i++){
                    dataList[i - pointer - 3] = (String) ois.readObject();
                }

                datas.add(new TextData(blockPos, dataList));

            }
        }catch (Exception e){}

        return datas.toArray(new TextData[0]);
    }
}
