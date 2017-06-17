package com.bomcodigo.navinha.game.enums;

import com.bomcodigo.navinha.game.Assets;

public enum MeteorType {
    Ice(Assets.ICEBALL,Type.Ice), Fire(Assets.FIREBALL,Type.Fire);
    private String asset;
    private Type type;

    MeteorType(String asset,Type type) {
        this.asset = asset;
        this.type = type;
    }
    public String getAsset(){
        return this.asset;
    }

    public Type getType() {
        return type;
    }
}
