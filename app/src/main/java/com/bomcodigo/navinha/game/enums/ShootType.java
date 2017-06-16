package com.bomcodigo.navinha.game.enums;


import com.bomcodigo.navinha.game.Assets;

public enum ShootType {
    Green(Assets.SHOOTGREEN, Type.Green), Red(Assets.SHOOT, Type.Red);
    private String asset;
    private Type type;

    ShootType(String asset,Type type) {
        this.asset = asset;
        this.type = type;
    }
    public String getAsset(){
        return this.asset;
    }
    public Type getType(){
        return this.type;
    }
}
