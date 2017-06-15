package com.bomcodigo.navinha.game.interfaces;


import com.bomcodigo.navinha.game.object.Meteor;

public interface MeteorsEngineDelegate {
    void createMeteor(Meteor meteor);
    void removeMeteor(Meteor meteor);
}
