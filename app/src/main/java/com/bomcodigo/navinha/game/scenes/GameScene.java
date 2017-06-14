package com.bomcodigo.navinha.game.scenes;


import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.GameButtons;
import com.bomcodigo.navinha.game.engine.MeteorsEngine;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.object.Meteor;
import com.bomcodigo.navinha.game.object.Player;
import com.bomcodigo.navinha.game.screens.ScreenBackground;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class GameScene extends CCLayer
        implements MeteorsEngineDelegate{

    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;
    private CCLayer playerLayer;
    private Player player;

    private GameScene(){
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
        this.addChild(this.background);
        this.playerLayer = CCLayer.node();
        this.addChild(playerLayer);
        GameButtons gameButtonsLayer = GameButtons.gameButtons();
        this.addChild(gameButtonsLayer);
        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);
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
    }

    private void startEngines(){
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    @Override
    public void createMeteor(Meteor meteor) {
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        this.meteorsArray.add(meteor);
    }
}
