package com.bomcodigo.navinha.game.object;


import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ShootEngineDelegate;

import org.cocos2d.nodes.CCSprite;

import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Player extends CCSprite{
    public float positionX = screenWidth()/2;
    public float positionY = 110;
    private  ShootEngineDelegate delegate;

    public Player(){
        super(Assets.NAVE);
        setPosition(positionX,positionY);
    }

    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }

    public void shoot(){
        delegate.createShoot(new Shoot(positionX,positionY));
    }

    public void moveLeft(){
        if (positionX > 30){
            positionX -= 10;
        }
        setPosition(positionX,positionY);
    }

    public void moveRight(){
        if (positionX < screenWidth() - 30){
            positionX += 10;
        }
        setPosition(positionX,positionY);
    }

}
