package com.bomcodigo.navinha.game.object;

import android.util.Log;

import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.enums.MeteorType;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.screens.Runner;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.Random;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Meteor extends CCSprite{
    private final String TAG = Meteor.class.getSimpleName();

    private float x,y;
    private MeteorsEngineDelegate delegate;
    private MeteorType meteorType;

    public Meteor(MeteorType meteorType){
        super(meteorType.getAsset());
        this.meteorType = meteorType;
        this.animationShooted();
        x = new Random().nextInt(Math.round(screenWidth() -10));
        y = screenHeight();
    }

    public void start(){
        this.schedule("update");
    }

    public void update(float dt){
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            this.y -= 1;
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

    public void animationShooted(){
        CCAnimation animation = CCAnimation.animation("",2 / 20f);
        CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet(Assets.EXPLOSION);
        CCSpriteFrame sprite = CCSpriteFrame.frame(spriteSheet.getTexture(),CGRect.make(0,0,60,60),CGPoint.ccp(280,280));
        Log.d(TAG,"Sprite Sheet: " + spriteSheet.getChildren().size());

        animation.addFrame(sprite);
        animation.addFrame(Assets.FIREBALL);
        CCIntervalAction scrPprAction = CCAnimate.action(1f, animation, false);
        this.runAction(scrPprAction);
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
        this.runAction(CCSequence.actions(s1,c1));
    }

    public void removeMe(){
        this.removeFromParentAndCleanup(true);
    }

    public MeteorType getType() {
        return meteorType;
    }
}
