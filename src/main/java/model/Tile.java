package model;

public enum Tile {
    EMPTY(0,false,false),
    SOLID(1,true,false),
    SPIKE(2,false,true),
    GOAL(3,false,false),
    WEB(4,false, false),
    SPEED_BOOST(5, true,false),
    JUMP_BOOST(6,true,false);


    public final int code; public final boolean solid; public final boolean hazard;
    Tile(int code, boolean solid, boolean hazard){ this.code=code; this.solid=solid; this.hazard=hazard; }


    public static Tile fromCode(int c){
        for (Tile t: values()) if (t.code==c) return t;
        return EMPTY;
    }
}
