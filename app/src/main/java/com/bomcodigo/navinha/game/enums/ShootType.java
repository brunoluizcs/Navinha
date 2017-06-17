package com.bomcodigo.navinha.game.enums;


import com.bomcodigo.navinha.game.Assets;

public enum ShootType {
    Ice(Assets.ICESHOOT, Type.Ice), Fire(Assets.SHOOT, Type.Fire);
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
