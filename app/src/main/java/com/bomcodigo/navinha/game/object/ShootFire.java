package com.bomcodigo.navinha.game.object;


import com.bomcodigo.navinha.game.enums.ShootType;

public class ShootFire extends Shoot {

    public ShootFire(float positionX, float positionY) {
        super(positionX, positionY, ShootType.Fire);
    }
}
