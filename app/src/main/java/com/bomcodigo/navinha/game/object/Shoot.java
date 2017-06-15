package com.bomcodigo.navinha.game.object;

import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ShootEngineDelegate;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;


public class Shoot extends CCSprite {
    private static final String TAG = Shoot.class.getSimpleName();

    private ShootEngineDelegate delegate;
    float positionX, positionY;

    public Shoot(float positionX, float positionY){
        super(Assets.SHOOT);
        this.positionX = positionX;
        this.positionY = positionY;
        setPosition(positionX,positionY);
        this.schedule("update");
    }

    public void update(float dt){
        positionY += 2;
        this.setPosition(screenResolution(CGPoint.ccp(positionX,positionY)));
    }

    public void explode(){
        this.delegate.removeShoot(this);
        this.unschedule("update");
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt,2f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1,a2);

        CCCallFunc c1 = CCCallFunc.action(this,"removeMe");

        this.runAction(CCSequence.actions(s1,c1));
    }

    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }

    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }

    public void start(){
        Log.d(TAG,"Shoot Moving!");
    }
}
