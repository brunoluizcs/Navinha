package com.bomcodigo.navinha.game.scenes;


import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.GameButtons;
import com.bomcodigo.navinha.game.engine.MeteorsEngine;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.interfaces.ShootEngineDelegate;
import com.bomcodigo.navinha.game.object.Meteor;
import com.bomcodigo.navinha.game.object.Player;
import com.bomcodigo.navinha.game.object.Shoot;
import com.bomcodigo.navinha.game.screens.ScreenBackground;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class GameScene extends CCLayer
        implements MeteorsEngineDelegate, ShootEngineDelegate{
    private final String TAG = GameScene.class.getSimpleName();

    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;
    private CCLayer playerLayer;
    private Player player;
    private CCLayer shootsLayer;
    private ArrayList shootsArray;
    private List playerArray;

    private GameScene(){
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
        this.addChild(this.background);
        this.playerLayer = CCLayer.node();
        this.addChild(playerLayer);
        GameButtons gameButtonsLayer = GameButtons.gameButtons();
        gameButtonsLayer.setDelegate(this);
        this.addChild(gameButtonsLayer);
        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);
        this.shootsLayer = CCLayer.node();
        this.addChild(this.shootsLayer);

        this.addGameObjects();
    }

    public static CCScene createGame(){
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }

    private void addGameObjects(){
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine();
        this.player = new Player();
        this.playerLayer.addChild(this.player);
        this.shootsArray = new ArrayList();
        this.player.setDelegate(this);
        this.playerArray = new ArrayList();
        this.playerArray.add(this.player);
    }

    public CGRect getBoarders(CCSprite object){
        CGRect rect = object.getBoundingBox();
        CGPoint GLpoint = rect.origin;
        CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y,rect.size.width, rect.size.height);
        return GLrect;
    }

    private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1,
                                           List<? extends CCSprite> array2,
                                           GameScene gameScene, String hit) {

        boolean result = false;
        for (int i = 0; i < array1.size(); i++) {
            CGRect rect1 = getBoarders(array1.get(i));
            for (int j = 0; j < array2.size(); j++) {
                CGRect rect2 = getBoarders(array2.get(j));
                if (CGRect.intersects(rect1, rect2)) {
                    Log.d(TAG, "Colision Detected: " + hit);
                    result = true;
                    Method method;
                    try{
                        method = GameScene.class.getMethod(hit,CCSprite.class,CCSprite.class);
                        method.invoke(gameScene,array1.get(i),array2.get(j));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return result;
    }

    public void meteoroHit(CCSprite meteor, CCSprite shoot){
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();
    }

    public void playerHit(CCSprite meteor, CCSprite player){

    }

    public void checkHits(float dt){
        this.checkRadiusHitsOfArray(this.meteorsArray,this.shootsArray,this,"meteoroHit");

        this.checkRadiusHitsOfArray(this.meteorsArray,this.playerArray,this,"playerHit");
    }

    private void startEngines(){
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    public boolean shoot(){
        player.shoot();
        return true;
    }

    public void moveLeft(){
        player.moveLeft();
    }

    public void moveRight(){
        player.moveRight();

    }

    @Override
    public void onEnter() {
        super.onEnter();
        this.schedule("checkHits");
        this.startEngines();
    }

    @Override
    public void createMeteor(Meteor meteor) {
        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        this.meteorsArray.add(meteor);
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        this.meteorsArray.remove(meteor);
    }


    @Override
    public void createShoot(Shoot shoot) {
        this.shootsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shoot.start();
        this.shootsArray.add(shoot);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        this.shootsArray.remove(shoot);
    }
}
