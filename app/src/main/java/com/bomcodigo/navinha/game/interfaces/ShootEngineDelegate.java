package com.bomcodigo.navinha.game.interfaces;


import com.bomcodigo.navinha.game.object.Shoot;

public interface ShootEngineDelegate {
    void createShoot(Shoot shoot);
    void removeShoot(Shoot shoot);
}
