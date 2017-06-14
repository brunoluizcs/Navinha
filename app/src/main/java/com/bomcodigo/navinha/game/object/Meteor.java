package com.bomcodigo.navinha.game.object;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Meteor extends CCSprite{
    private float x,y;

    public Meteor(String image){
        super(image);
        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    public void start(){
        this.schedule("update");
    }

    public void update(float dt){
        y -= 1;
        this.setPosition(screenResolution(CGPoint.ccp(x,y)));
    }

}
