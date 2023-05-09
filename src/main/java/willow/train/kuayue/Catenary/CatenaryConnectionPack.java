package willow.train.kuayue.Catenary;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class CatenaryConnectionPack {
    private ArrayList<Vec3> vectors = new ArrayList<>();
    private ArrayList<Vec3[]> hangerLineVectors = new ArrayList<>();
    private Vec3 posA , posB;
    private String catenaryType;

    public CatenaryConnectionPack(Vec3 posA , Vec3 posB , String catenaryType , ArrayList<Vec3> vectors){
        this.posA = posA;
        this.posB = posB;
        this.catenaryType = catenaryType;
        this.vectors = vectors;
    }

    public void setCatenaryType(String catenaryType) {
        this.catenaryType = catenaryType;
    }

    public void setPosA(Vec3 posA) {
        this.posA = posA;
    }

    public void setPosB(Vec3 posB) {
        this.posB = posB;
    }

    public void setVectors(ArrayList<Vec3[]> vectors) {
        this.hangerLineVectors = vectors;
    }

    public void setHangerLineVectors(ArrayList<Vec3> vectors) {
        this.vectors = vectors;
    }
    public ArrayList<Vec3> getVectors() {
        return vectors;
    }

    public ArrayList<Vec3[]> getHangerLineVectors() {
        return hangerLineVectors;
    }

    public Vec3 getPosA() {
        return posA;
    }

    public Vec3 getPosB() {
        return posB;
    }

    public String getCatenaryType() {
        return catenaryType;
    }

    public void appendVector(Vec3 vector){
        vectors.add(vector);
    }

    public void appendHangerLineVector(Vec3[] vectors){
        hangerLineVectors.add(vectors);
    }

    public CatenaryConnectionPack mergePacket(CatenaryConnectionPack ccp){
        for(Vec3 vec3 : ccp.getVectors()){
            this.vectors.add(vec3);
        }
        for(Vec3 vec3[] : ccp.getHangerLineVectors()){
            this.hangerLineVectors.add(vec3);
        }
        return this;
    }
}
