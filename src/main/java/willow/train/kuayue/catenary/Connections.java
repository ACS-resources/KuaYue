package willow.train.kuayue.Catenary;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.init.EntityInit;

import java.util.ArrayList;

public class Connections {

    private static final double ATTDIFERENCE = 0.90;
    private static final double DEFAULTBIAS = 0.10;

    public static final double MAXWIRELENGTH = 33.0;

    public static final double DISCARDAREAWIDTH = MAXWIRELENGTH + 3.0;

    public static final double ALLOWANCE = 0.05;

    public static CatenaryConnectionPack hyperbolicConnect(Vec3 posA , Vec3 posB , double slack , String catenaryType , double wireLength){
        Vec3 posXZ = new Vec3(posB.x , posA.y, posB.z);
        Vec3 deltaXZVec = posXZ.subtract(posA);
        double deltaXZ = posA.distanceTo(posXZ);
        double deltaY = posB.distanceTo(posXZ);
        double originalStep = 5.0;

        // 直上直下的特殊情况
        if(deltaXZ == 0) return straightConnect(posA , posB , catenaryType , wireLength);

        // 确认拟合方向
        if(posB.y - posA.y < 0) originalStep = -originalStep;

        // 初始值
        double resultY = 0.0;
        double x1 = - 0.5 * deltaXZ;
        double largerSteppingX = x1;
        double smallerSteppingX = x1;
        double x2 = 0.0;

        // 确定起点和终点
        boolean isSteping = true;
        boolean largerThan = false;

        int iX = 0;
        while(iX<5000){
            iX++;
            x2 = x1 + deltaXZ;
            resultY = Math.abs(slack * (Math.cosh(x2/slack) - Math.cosh(x1/slack)));
            System.out.println(resultY);

            if(Math.abs(resultY - deltaY) < ALLOWANCE) break;

            if(resultY > deltaY) {
                if(isSteping){
                    smallerSteppingX = largerSteppingX;
                    largerSteppingX = x1;
                } else {
                    x1 = (x1 + smallerSteppingX)/2;
                    continue;
                }
                isSteping = false;
            }else if(!isSteping){
                x1 = (x1 + largerSteppingX)/2;
                continue;
            }

            if(isSteping){
                largerSteppingX = x1;
                x1 += originalStep;
                continue;
            }
        }
        
        if(iX >= 5000){
            return new CatenaryConnectionPack(posA , posB , "error" , null);
        }

        double angle = Math.atan(deltaXZVec.x/deltaXZVec.z);

        double step = 0.02;
        double stepX = step*Math.sin(angle);
        double stepZ = step*Math.cos(angle);

        //xz分段归档
        ArrayList<Vec3> xyzList = new ArrayList<>();

        if((stepX > 0 && posB.x < posA.x) || (stepX < 0 && posB.x > posA.x)){
            stepX = -stepX;
        }
        if((stepZ > 0 && posB.z < posA.z) || (stepZ < 0 && posB.z > posA.z)){
            stepZ = -stepZ;
        }

        xyzList.add(posA);
        for(double i = step ; i <= deltaXZ/step ; i += step){
            double x = posA.x + i * stepX;
            double y = posA.y + slack * (Math.cosh((x1+i*step)/slack) - Math.cosh(x1/slack));
            double z = posA.z + i * stepZ;
            Vec3 functionPoint = new Vec3(x,y,z);
            Vec3 lastPoint = xyzList.get(xyzList.size()-1);
            if(functionPoint.distanceTo(lastPoint) >=0.75 && functionPoint.distanceTo(lastPoint) <= 0.78){
                //System.out.println("Step:" + i + " , dx:" + x + " , dy:" + (slack*(i*i) + constant*(i)) + " , dz:" + z + " , distance:" + functionPoint.distanceTo(lastPoint));
                xyzList.add(functionPoint);
                continue;
            }
            if(i+step > deltaXZ/step && lastPoint.closerThan(posB , 0.75)){
                Vec3 lastForwardPoint = xyzList.get(xyzList.size()-2);
                double deltaXT = posB.x - lastForwardPoint.x;
                double deltaYT = posB.y - lastForwardPoint.y;
                double deltaZT = posB.z - lastForwardPoint.z;
                double distanceT = lastForwardPoint.distanceTo(posB);
                double times = 0.75/distanceT;
                xyzList.set(xyzList.size() - 1, new Vec3(posB.x - deltaXT*times , posB.y - deltaYT*times , posB.z - deltaZT*times));
                break;
            }
        }
        xyzList.add(posB);

        return new CatenaryConnectionPack(posA , posB , catenaryType , xyzList);
    }

    public static CatenaryConnectionPack parabolaConnect(Vec3 posA , Vec3 posB , double slack , String catenaryType){

        //计算xz距离
        Vec3 deltaXZ = new Vec3(posB.x() - posA.x() , 0.0 , posB.z() - posA.z());
        //y距离
        double deltaY = posB.y() - posA.y();
        //分段
        double deltaX = deltaXZ.length();

        //获得角度数据
        double angle = Math.atan(deltaXZ.x/deltaXZ.z);

        //xz分段归档
        ArrayList<Vec3> xyzList = new ArrayList<>();

        //构造抛物线 slack * x ^ 2 + constant * x = y
        double constant = deltaY/deltaX - slack*deltaX;

        double step = 0.02;
        double stepX = step*Math.sin(angle);
        double stepZ = step*Math.cos(angle);

        if((stepX > 0 && posB.x < posA.x) || (stepX < 0 && posB.x > posA.x)){
            stepX = -stepX;
        }
        if((stepZ > 0 && posB.z < posA.z) || (stepZ < 0 && posB.z > posA.z)){
            stepZ = -stepZ;
        }

        xyzList.add(posA);
        for(double i = step ; i <= deltaX/step ; i += step){
            double x = posA.x + i * stepX;
            double y = posA.y + slack*(i*i)*step*step + constant*(i*step);
            double z = posA.z + i * stepZ;
            Vec3 functionPoint = new Vec3(x,y,z);
            Vec3 lastPoint = xyzList.get(xyzList.size()-1);
            if(functionPoint.distanceTo(lastPoint) >=0.75 && functionPoint.distanceTo(lastPoint) <= 0.78){
                //System.out.println("Step:" + i + " , dx:" + x + " , dy:" + (slack*(i*i) + constant*(i)) + " , dz:" + z + " , distance:" + functionPoint.distanceTo(lastPoint));
                xyzList.add(functionPoint);
                continue;
            }
            if(i+step > deltaX/step && lastPoint.closerThan(posB , 0.75)){
                Vec3 lastForwardPoint = xyzList.get(xyzList.size()-2);
                double deltaXT = posB.x - lastForwardPoint.x;
                double deltaYT = posB.y - lastForwardPoint.y;
                double deltaZT = posB.z - lastForwardPoint.z;
                double distanceT = lastForwardPoint.distanceTo(posB);
                double times = 0.75/distanceT;
                xyzList.set(xyzList.size() - 1, new Vec3(posB.x - deltaXT*times , posB.y - deltaYT*times , posB.z - deltaZT*times));
                break;
            }
        }
        xyzList.add(posB);

        if(catenaryType != "catenary") {
            return new CatenaryConnectionPack(posA, posB, catenaryType, xyzList);
        }

        CatenaryConnectionPack ccpResult = new CatenaryConnectionPack(posA, posB, catenaryType, xyzList);
        double time = 2;
        if(deltaX > 8){time = 4;}
        if(deltaX > 16){time = 6;}
        if(deltaX > 24){time = 8;}

        return ccpResult.mergePacket(hangerConnect(posA , posB , angle , slack , constant , time));
    }

    public static CatenaryConnectionPack hangerConnect(Vec3 posA , Vec3 posB , double angle , double slack , double constant , double time){
        Vec3 deltaXZ = new Vec3(posB.x() - posA.x() , 0.0 , posB.z() - posA.z());
        double deltaX = deltaXZ.length();
        double deltaY = posB.y() - posA.y();
        double stepHanger = deltaX/time;

        CatenaryConnectionPack ccpResult = new CatenaryConnectionPack(posA , posB , "hanger" , new ArrayList<>());

        for(double x = stepHanger ; x < deltaX ; x += stepHanger) {
            double y = posA.y + slack*(x*x) + constant*x;
            double stepHangerX = x*Math.sin(angle);
            double stepHangerY = posA.y + (deltaY/time) * x/stepHanger - ATTDIFERENCE - 0.03;
            double stepHangerZ = x*Math.cos(angle);

            if((stepHangerX > 0 && posB.x < posA.x) || (stepHangerX < 0 && posB.x > posA.x)){
                stepHangerX = -stepHangerX;
            }
            if((stepHangerZ > 0 && posB.z < posA.z) || (stepHangerZ < 0 && posB.z > posA.z)){
                stepHangerZ = -stepHangerZ;
            }
            CatenaryConnectionPack ccp = straightConnect(new Vec3(posA.x + stepHangerX , y-0.1 , posA.z + stepHangerZ) , new Vec3(posA.x + stepHangerX , stepHangerY , posA.z + stepHangerZ) , "straight" , 0.28);
            ccpResult.appendHangerLineVector(ccp.getVectors().toArray(new Vec3[0]));
        }

        return ccpResult;
    }

    public static CatenaryConnectionPack straightConnect(Vec3 posA , Vec3 posB , String catenaryType , double wireLength){
        double length = posA.distanceTo(posB);
        int sequent = (int)Math.ceil(length/wireLength);
        double deltaX = posB.x() - posA.x();
        double deltaY = posB.y() - posA.y();
        double deltaZ = posB.z() - posA.z();

        ArrayList<Vec3> xyzList = new ArrayList<>(sequent+1);
        for(double i = 0 ; i < sequent-1 ; i++) {
            xyzList.add(new Vec3(posA.x()+deltaX*i/(double)sequent , posA.y() + deltaY*i/(double)sequent, posA.z()+deltaZ*i/(double) sequent));
        }
        xyzList.add(new Vec3(posB.x - deltaX*wireLength/length , posB.y - deltaY*wireLength/length ,posB.z - deltaZ*wireLength/length));
        xyzList.add(posB);
        return new CatenaryConnectionPack(posA , posB , catenaryType , xyzList);
    }

    public static CatenaryEntitiesPacket generateEntities(CatenaryConnectionPack ccp , Level level , Player player){
        ArrayList<Vec3> xyzList = ccp.getVectors();
        Vec3 posA = xyzList.get(0);
        Vec3 posB = xyzList.get(xyzList.size()-1);
        double deltaX = posB.x - posA.x;
        double deltaZ = posB.z - posA.z;
        double entireLength = posB.distanceTo(posA);
        Vec3 defaultBias = new Vec3(DEFAULTBIAS * deltaX/entireLength , 0 , DEFAULTBIAS * deltaZ/entireLength);

        ArrayList<Vec3[]> hangerLists = ccp.getHangerLineVectors();
        if(player.level.isClientSide()) return null;

        CatenaryEntitiesPacket cep = new CatenaryEntitiesPacket();

        for(int i = 0 ; i< xyzList.size() - 1 ; i++){
            Vec3 vec3 = xyzList.get(i);
            double length = xyzList.get(i+1).distanceTo(xyzList.get(i));
            CatenaryBaseEntity test = new CatenaryBaseEntity(EntityInit.CATENARY_BASE.get(), player , player.level);
            test.setDeltaMovement(0,0,0);
            test.setPos(vec3.add(defaultBias));
            if(xyzList.get(i+1).z() < xyzList.get(i).z()) {
                test.setYRot(360f+(float) Math.toDegrees(Math.atan((xyzList.get(i+1).x() - xyzList.get(i).x())/(xyzList.get(i+1).z() - xyzList.get(i).z()))));
            } else {
                test.setYRot(180f + (float) Math.toDegrees(Math.atan((xyzList.get(i + 1).x() - xyzList.get(i).x()) / (xyzList.get(i + 1).z() - xyzList.get(i).z()))));
            }
            test.setXRot(-(float) Math.toDegrees(Math.asin((xyzList.get(i + 1).y() - xyzList.get(i).y()) / length)));
            player.level.addFreshEntity(test);
            cep.appendEntities(test);
        }

        for(int x = 0 ; x< hangerLists.size() ; x++) {
            for (int i = 0; i < hangerLists.get(x).length - 1; i++) {
                Vec3 vec3 = hangerLists.get(x)[i];
                double length = hangerLists.get(x)[i+1].distanceTo(hangerLists.get(x)[i]);
                CatenaryBaseEntity test = new CatenaryBaseEntity(EntityInit.SMALL_CATENARY_BASE.get(), player, player.level);
                test.setDeltaMovement(0, 0, 0);
                test.setPos(vec3.add(defaultBias));
                if (hangerLists.get(x)[i+1].z() < hangerLists.get(x)[i].z()) {
                    test.setYRot(360f + (float) Math.toDegrees(Math.atan((hangerLists.get(x)[i+1].x() - hangerLists.get(x)[i].x()) / (hangerLists.get(x)[i+1].z() - hangerLists.get(x)[i].z()))));
                } else {
                    test.setYRot(180f + (float) Math.toDegrees(Math.atan((hangerLists.get(x)[i+1].x() - hangerLists.get(x)[i].x()) / (hangerLists.get(x)[i+1].z() - hangerLists.get(x)[i].z()))));
                }
                test.setXRot(-(float) Math.toDegrees(Math.asin((hangerLists.get(x)[i+1].y() - hangerLists.get(x)[i].y()) / length)));
                player.level.addFreshEntity(test);
                cep.appendEntities(test);
            }
        }
        return cep;
    }

    public static CatenaryEntitiesPacket generateCatenaryEntities(Vec3 posA , Vec3 posB , Level level , Player player){
        Vec3 offset = new Vec3(0,ATTDIFERENCE,0);
        CatenaryConnectionPack ccpParabola = parabolaConnect(posA.add(offset) , posB.add(offset) , 0.002 , "catenary");
        CatenaryConnectionPack ccpStraight = straightConnect(posA , posB , "straight" , 0.75);
        CatenaryEntitiesPacket resultPacket = generateEntities(ccpParabola , level , player);
        CatenaryEntitiesPacket resultPacket2 = generateEntities(ccpStraight , level , player);
        CatenaryBaseEntity[] entities = resultPacket.getEntities();

        return resultPacket.mergePacket(resultPacket2);
    }
}
