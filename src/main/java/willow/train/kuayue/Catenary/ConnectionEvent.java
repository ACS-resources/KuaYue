package willow.train.kuayue.Catenary;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Entity.SmallCatenaryBaseEntity;
import willow.train.kuayue.Main;
import willow.train.kuayue.Util.ComponentTranslationTool;

import static willow.train.kuayue.Catenary.Connections.MAXWIRELENGTH;

public class ConnectionEvent {
    Vec3 posA = null , posB = null;

    BlockPos blockA = null , blockB = null;
    Player player;
    Level level;

    String catenaryType;

    public ConnectionEvent(Player player , Level level , String catenaryType){
        this.player = player;
        this.level = level;
        this.catenaryType = catenaryType;
    }

    public CatenaryEntitiesPacket connect(){
        if(posA != null && posB != null  && catenaryType != null){

            CatenaryConnectionPack ccp;
            CatenaryEntitiesPacket cep = null;
            CompoundTag tag = new CompoundTag();
            tag.putString("blockA" , blockA.getX() + "," + blockA.getY() + "," + blockA.getZ());
            tag.putString("blockB" , blockB.getX() + "," + blockB.getY() + "," + blockB.getZ());
            switch (catenaryType){
                case "hyperbolic":
                    ccp = Connections.hyperbolicConnect(posA , posB , 80 , "hyperbolic" , 0.75);
                    if(ccp.getCatenaryType().equals("error")){
                        ComponentTranslationTool.showMsg(player, ".uncovergence" , true);
                        cep = null;
                        break;
                    }
                    cep = Connections.generateEntities(ccp , level , player);
                    break;
                case "parabola":
                    ccp = Connections.parabolaConnect(posA , posB , 0.002 ,"parabola");
                    cep = Connections.generateEntities(ccp , level , player);
                    break;
                case "catenary":
                    cep = Connections.generateCatenaryEntities(posA , posB , level , player);
                    break;
                default:
                    ccp = Connections.straightConnect(posA , posB , "straight" , 0.75);
                    cep = Connections.generateEntities(ccp , level , player);
            }
            cep.setTag(tag);
            return cep;
        }
        return null;
    }

    public void setPos(Vec3 pos , BlockPos block){
        String posStr = "(" + pos.x + "," + pos.y + "," + pos.z + ")";
        if(posA == null){
            posA = pos;
            blockA = block;
            player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".first_block_chosen").copy().append(posStr) , true);
            return;
        }else {
            blockB = block;
            if(blockA.equals(blockB)){
                player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".same_block") , true);
                return;
            }
            if(!posA.closerThan(pos , MAXWIRELENGTH)){
                player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".too_far_away") , true);
                return;
            }
            Vec3 posX = new Vec3(pos.x , posA.y, pos.z);
            if( !catenaryType.equals("straight") && !catenaryType.equals("hyperbolic") && pos.distanceTo(posX)/posA.distanceTo(posX) > 1.1){
                player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".excessive_slope") , true);
                return;
            }
            posB = pos;

            Vec3 posAX = new Vec3(posA.x - 5 , posA.y - 5, posA.z - 5);
            Vec3 posBX = new Vec3(posB.x + 5 , posB.y + 5, posB.z + 5);
            CatenaryBaseEntity[] entities = level.getEntitiesOfClass(CatenaryBaseEntity.class ,new AABB(posAX , posBX)).toArray(new CatenaryBaseEntity[0]);
            for(CatenaryBaseEntity entity : entities){
                CompoundTag tag = new CompoundTag();
                entity.addAdditionalSaveData(tag);
                if((tag.get("blockA").getAsString()).equals(blockA.getX() + "," + blockA.getY() + "," + blockA.getZ()) &&
                        (tag.get("blockB").getAsString()).equals(blockB.getX() + "," + blockB.getY() + "," +blockB.getZ())
                        || (tag.get("blockB").getAsString()).equals(blockA.getX() + "," + blockA.getY() + "," + blockA.getZ()) &&
                        (tag.get("blockA").getAsString()).equals(blockB.getX() + "," + blockB.getY() + "," +blockB.getZ()))
                {
                    player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".redundant_operation") , true);
                    posB = null;
                    return;
                }
            }

            SmallCatenaryBaseEntity[] hangerEntities = level.getEntitiesOfClass(SmallCatenaryBaseEntity.class ,new AABB(posAX , posBX)).toArray(new SmallCatenaryBaseEntity[0]);
            for(SmallCatenaryBaseEntity entity : hangerEntities){
                CompoundTag tag = new CompoundTag();
                entity.addAdditionalSaveData(tag);
                if((tag.get("blockA").getAsString()).equals(blockA.getX() + "," + blockA.getY() + "," + blockA.getZ()) &&
                        (tag.get("blockB").getAsString()).equals(blockB.getX() + "," + blockB.getY() + "," +blockB.getZ())
                        || (tag.get("blockB").getAsString()).equals(blockA.getX() + "," + blockA.getY() + "," + blockA.getZ()) &&
                        (tag.get("blockA").getAsString()).equals(blockB.getX() + "," + blockB.getY() + "," +blockB.getZ()))
                {
                    player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".redundant_operation") , true);
                    posB = null;
                    return;
                }
            }
            player.displayClientMessage(Component.translatable("msg." + Main.MOD_ID + ".second_block_chosen").copy().append(posStr) , true);
        }
    }

    public boolean canConnect(){
        return posA != null && posB != null;
    }
}
