package com.bomcodigo.navinha.game.enums;



public enum MeteorSpeed {
    Slow(1f),Normal(1.3f),Fast(1.6f),SuperFast(2f),ExtremFast(3f);

    private float scale;

    MeteorSpeed(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
