package com.bomcodigo.navinha.game.object;

import android.util.Log;

import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.animations.ExplosionAnimation;
import com.bomcodigo.navinha.game.enums.MeteorSpeed;
import com.bomcodigo.navinha.game.enums.MeteorType;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.screens.Runner;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCFlipY;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import java.util.Random;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Meteor extends CCSprite{
    private final String TAG = Meteor.class.getSimpleName();

    private float x,y;
    private MeteorsEngineDelegate delegate;
    private MeteorType meteorType;
    private ExplosionAnimation explosionAnimation;
    private MeteorSpeed meteorSpeed;

    public Meteor(MeteorType meteorType){
        super(meteorType.getAsset());

        randomScaleMeteor();
        setMeteorSpeed();
        this.meteorType = meteorType;
        this.explosionAnimation = new ExplosionAnimation();
        x = new Random().nextInt(Math.round(screenWidth() -10));
        y = screenHeight();

    }

    private void setMeteorSpeed() {
        int factor = new Random().nextInt(16);
        if (factor >= 0 && factor < 2){
            this.meteorSpeed = MeteorSpeed.Slow;
        }else if (factor >= 2 && factor < 7){
            this.meteorSpeed = MeteorSpeed.Normal;
        }else if (factor >= 7 && factor < 13){
            this.meteorSpeed = MeteorSpeed.Fast;
        }else if (factor >= 13 && factor <=14 ){
            this.meteorSpeed = MeteorSpeed.SuperFast;
        }else if (factor == 15){
            this.meteorSpeed = MeteorSpeed.ExtremFast;
        }else{
            this.meteorSpeed = MeteorSpeed.Normal;
        }
    }

    public void randomScaleMeteor() {
        float finalX = (2f - 0.5f) * new Random().nextFloat() + 0.5f;
        this.setScale(finalX);
    }

    public void start(){
        this.schedule("update");
    }

    public void update(float dt){
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            this.y -= this.meteorSpeed.getScale();

            this.setPosition(screenResolution(CGPoint.ccp(x,y)));

            if (y <= -1){
                this.delegate.removeMeteor(this);
                this.delegate.clearMeteor(this);
                this.runAction(CCCallFunc.action(this,"removeMe"));
                Log.d(TAG,"Clear Meteor");
            }
        }
    }


    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void shooted(){
        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(),R.raw.bang
        );

        this.delegate.removeMeteor(this);
        this.unschedule("update");


        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1,a2);
        CCCallFunc c1 = CCCallFunc.action(this,"removeMe");
        this.runAction(CCSequence.actions(this.explosionAnimation.getAction(),s1,c1));
    }

    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }

    public MeteorType getType() {
        return meteorType;
    }
}
