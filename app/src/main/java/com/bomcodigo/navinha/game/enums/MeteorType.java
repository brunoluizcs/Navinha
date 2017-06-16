package com.bomcodigo.navinha.game.enums;

import com.bomcodigo.navinha.game.Assets;

public enum MeteorType {
    Green(Assets.FIREGREENBALL,Type.Green), Red(Assets.FIREBALL,Type.Red);
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
