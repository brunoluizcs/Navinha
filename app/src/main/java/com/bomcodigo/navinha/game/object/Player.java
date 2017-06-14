package com.bomcodigo.navinha.game.object;


import com.bomcodigo.navinha.game.Assets;

import org.cocos2d.nodes.CCSprite;

import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Player extends CCSprite{
    public float positionX = screenWidth()/2;
    public float positionY = 110;

    public Player(){
        super(Assets.NAVE);
        setPosition(positionX,positionY);
    }
    /*
    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }
    */

}
