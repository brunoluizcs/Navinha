package com.bomcodigo.navinha.game.object;


import android.content.IntentFilter;
import android.util.Log;

import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.Accelerometer;
import com.bomcodigo.navinha.game.interfaces.AccelerometerDelegate;
import com.bomcodigo.navinha.game.interfaces.ShootEngineDelegate;
import com.bomcodigo.navinha.game.screens.Runner;

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
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Player extends CCSprite
    implements AccelerometerDelegate{
    private final String TAG = Player.class.getSimpleName();
    private static final double NOISE = 1f;


    public float positionX = screenWidth()/2;
    public float positionY = 110;
    private  ShootEngineDelegate delegate;
    private Accelerometer accelerometer;
    private float currentAccelX;
    private float currentAccelY;

    public Player(){
        super(Assets.NAVE);
        setPosition(positionX,positionY);
    }

    public void start(){
        this.schedule("update");
    }

    public void setDelegate(ShootEngineDelegate delegate){
        this.delegate = delegate;
    }

    public void shoot(){
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            String[] assets = {Assets.SHOOT,Assets.SHOOTGREEN};
            int rand = new Random().nextInt(2);
            delegate.createShoot(new Shoot(positionX,positionY+30,assets[rand]));
        }
    }

    public void moveLeft(){
        if (positionX > 30){
            float fator = Math.round(currentAccelX);
            fator = fator >= 4 ? 4 : fator;
            positionX -= fator;

        }
        setPosition(positionX,positionY);
    }

    public void moveRight(){
        if (positionX < screenWidth() - 30){
            float fator = Math.round(currentAccelX) * -1;
            fator = fator >= 4 ? 4 : fator;
            positionX += fator;
        }
        setPosition(positionX,positionY);
    }

    public void moveTop(){
        if (positionY < screenHeight() - 30){
            float fator = Math.round(currentAccelY) * -1;
            fator = fator >= 6 ? 6 : fator;
            float y = this.positionY + fator;
            if (y < screenHeight() - 30 && fator > NOISE){
                this.positionY = y;
            }
        }
    }

    private void moveBottom() {
        if (positionY >= 100) {
            float fator = Math.round(currentAccelY);
            fator = fator >= 6 ? 6 : fator;
            float y = this.positionY - fator;
            if (y > 100) {
                this.positionY = y;
            } else {
                this.positionY = 100;
            }
        }
    }

    public void explode(){
        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.over);
        SoundEngine.sharedEngine().pauseSound();

        this.unschedule("update");
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt,2f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 =  CCSpawn.actions(a1,a2);
        this.runAction(CCSequence.actions(s1));
    }

    public void catchAccelerometer(){
        Accelerometer.sharedAccelerometer().catchAccelerometer();
        this.accelerometer = Accelerometer.sharedAccelerometer();
        this.accelerometer.setDelegate(this);
    }

    public void update(float dt){
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            if (this.currentAccelX<NOISE){
                moveRight();
            }

            if (this.currentAccelX>NOISE){
                moveLeft();
            }

            if (this.currentAccelY<NOISE){
                moveTop();
            }

            if (this.currentAccelY>NOISE){
                moveBottom();
            }
            this.setPosition(CGPoint.ccp(this.positionX,this.positionY));
        }
    }



    @Override
    public void accelerometerDidAccelerate(float x, float y) {
        //Log.d(TAG,"X: " + x);
        //Log.d(TAG,"Y: " + y);

        this.currentAccelX = x;
        this.currentAccelY = y;
    }
}
