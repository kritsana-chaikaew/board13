package com.d13.board13;

public class Path{
    public static final String[] NAMES = {
        "objects/mon1/mon1.g3db"
    };

    public static String getPathByName(String name){
        if(name == "mon1") return NAMES[0];
        return null;
    }
}
