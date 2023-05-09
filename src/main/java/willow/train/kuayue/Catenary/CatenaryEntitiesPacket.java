package willow.train.kuayue.Catenary;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import willow.train.kuayue.Entity.CatenaryBaseEntity;

import java.util.ArrayList;

public class CatenaryEntitiesPacket {
    private ArrayList<AbstractArrow> entities = new ArrayList<>();

    public CatenaryEntitiesPacket(){}

    public void discardEntities() {
        for (AbstractArrow cbe : entities){
            cbe.discard();
        }
    }

    public void appendEntities(CatenaryBaseEntity... entities){
        for(CatenaryBaseEntity cbe : entities){
            this.entities.add(cbe);
        }
    }

    public CatenaryBaseEntity[] getEntities(){
        return entities.toArray(new CatenaryBaseEntity[0]);
    }

    public CatenaryEntitiesPacket mergePacket(CatenaryEntitiesPacket... packets) {
        for(CatenaryEntitiesPacket cep : packets){
            this.appendEntities(cep.getEntities());
        }
        return this;
    }

    public void setTag(CompoundTag ct){
        for(AbstractArrow aa : entities){
            aa.readAdditionalSaveData(ct);
        }
    }
}
