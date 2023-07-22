package willow.train.kuayue.Util;

import net.minecraft.util.StringRepresentable;

public enum PanelTypes implements StringRepresentable {

    P25B,
    P25G;
    /*
    P25Z,
    P25T,
    P25KA,
    P25KB,
    M25B,
    M25G,
    M25Z,
    M25T,
    M25KA,
    M25KB;
     */

    public String toString() {
        return this.getSerializedName();
    } 

    public String getSerializedName() {
        switch (this){
            case P25B -> {return "p25b";}
            case P25G -> {return "p25g";}
            /*
            case P25KA -> {return "p25ka";}
            case P25KB -> {return "p25kb";}
            case P25T -> {return "p25t";}
            case M25G -> {return "m25b";}
            case M25Z -> {return "m25z";}
            case M25T -> {return "m25t";}
            case M25KA -> {return "m25ka";}// 细线
            */
            default -> {return  "p25b";}
        }
    }
}
