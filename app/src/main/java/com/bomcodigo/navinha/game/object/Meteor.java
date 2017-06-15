package com.bomcodigo.navinha.game.object;

import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Meteor extends CCSprite{
    private float x,y;
    private MeteorsEngineDelegate delegate;

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

    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void shooted(){
        this.delegate.removeMeteor(this);
        this.unschedule("update");

        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1,a2);
        CCCallFunc c1 = CCCallFunc.action(this,"removeMe");
        this.runAction(CCSequence.actions(s1,c1));
    }

    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }
}
