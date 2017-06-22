package com.bomcodigo.navinha.game.scenes;


import android.util.Log;

import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.control.GameButtons;
import com.bomcodigo.navinha.game.engine.AchievementEngine;
import com.bomcodigo.navinha.game.engine.ColisionEngine;
import com.bomcodigo.navinha.game.engine.MeteorsEngine;
import com.bomcodigo.navinha.game.enums.MeteorType;
import com.bomcodigo.navinha.game.enums.ShootType;
import com.bomcodigo.navinha.game.enums.Type;
import com.bomcodigo.navinha.game.interfaces.AchievementEngineDelegate;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.interfaces.PauseDelegate;
import com.bomcodigo.navinha.game.interfaces.ShootEngineDelegate;
import com.bomcodigo.navinha.game.object.Meteor;
import com.bomcodigo.navinha.game.object.Player;
import com.bomcodigo.navinha.game.object.Score;
import com.bomcodigo.navinha.game.object.Shoot;
import com.bomcodigo.navinha.game.screens.ParallaxBackground;
import com.bomcodigo.navinha.game.screens.Runner;
import com.bomcodigo.navinha.game.screens.ScreenBackground;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends CCLayer
        implements MeteorsEngineDelegate, ShootEngineDelegate, PauseDelegate{
    private final String TAG = GameScene.class.getSimpleName();
    private AchievementEngineDelegate achievementEngineDelegate;

    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;
    private CCLayer playerLayer;
    private Player player;
    private CCLayer shootsLayer;
    private ArrayList shootsArray;
    private List playerArray;
    private Score score;
    private CCLayer scoreLayer;
    private PauseScreen pauseScreen;
    private CCLayer layerTop;
    private ParallaxBackground parallaxBackground;
    private ColisionEngine colisionEngine;
    private int comboIceMeteor;
    private int comboFireMeteor;


    private GameScene(){
        preloadCache();
        SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(),R.raw.music,true);
        this.colisionEngine = new ColisionEngine();
        this.achievementEngineDelegate = (AchievementEngineDelegate) CCDirector.sharedDirector().getActivity();
        AchievementEngine.sharedAchievementEngine().setDelegate(this.achievementEngineDelegate);

        this.comboIceMeteor = 0;
        this.comboFireMeteor = 0;

        //TODO: Remover este background
        //this.background = new ScreenBackground(Assets.BACKGROUND);
        //this.background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
        //this.addChild(this.background);
        this.parallaxBackground = new ParallaxBackground();
        this.addChild(parallaxBackground);
        this.schedule("scrollScreen");


        this.playerLayer = CCLayer.node();
        this.addChild(playerLayer);
        GameButtons gameButtonsLayer = GameButtons.gameButtons();
        gameButtonsLayer.setDelegate(this);
        this.addChild(gameButtonsLayer);
        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);
        this.shootsLayer = CCLayer.node();
        this.addChild(this.shootsLayer);
        this.layerTop = CCLayer.node();
        this.addChild(this.layerTop);
        this.addGameObjects();

        AchievementEngine.sharedAchievementEngine().unlock_triumphant_entry();
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
        this.player.start();
        this.player.catchAccelerometer();
        this.playerLayer.addChild(this.player);
        this.shootsArray = new ArrayList();
        this.player.setDelegate(this);
        this.playerArray = new ArrayList();
        this.playerArray.add(this.player);
        this.score = Score.sharedScore();
        this.score.clear();
        this.score.setDelegate(this);
        this.scoreLayer = CCLayer.node();
        this.scoreLayer.addChild(this.score);
        this.addChild(this.scoreLayer);
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
            CGRect aux1 = CGRect.make(rect1.origin.x,rect1.origin.y,rect1.size.width,rect1.size.height);
            for (int j = 0; j < array2.size(); j++) {
                CGRect rect2 = getBoarders(array2.get(j));
                CGRect aux2 = CGRect.make(rect2.origin.x,rect2.origin.y,rect2.size.width,rect2.size.height);
                if  ("playerHit".equals(hit)){
                    aux1 = colisionEngine.reduceRadiosArea(aux1,40);
                    aux2 = colisionEngine.reduceRadiosArea(aux2,40);
                }
                if (CGRect.intersects(aux1, aux2)) {
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
        ShootType shootType = ((Shoot) shoot).getType();
        MeteorType meteorType = ((Meteor) meteor).getType();

        ((Shoot) shoot).explode();
        if (shootType.getType().equals(meteorType.getType())){
            ((Meteor) meteor).shooted();
            this.score.increase();
            if (this.score.getCombo() > 15){
                player.enableShield();
            }
            AchievementEngine.sharedAchievementEngine().unlock_lets_play_a_game();
            checkMeteorHitAchievement(meteorType);

        }
    }

    private void checkMeteorHitAchievement(MeteorType meteorType) {
        if (meteorType.getType() == Type.Ice){
            comboIceMeteor++;
            switch (comboIceMeteor){
                case 10: AchievementEngine.sharedAchievementEngine().unlock_aprendice_ice_meteor_slayer();break;
                case 50: AchievementEngine.sharedAchievementEngine().unlock_intermediate_ice_meteor_slayer();break;
                case 100: AchievementEngine.sharedAchievementEngine().unlock_professional_ice_meteor_slayer();break;
                case 200: AchievementEngine.sharedAchievementEngine().unlock_expert_ice_meteor_slayer();break;
            }
        }else{
            comboFireMeteor++;
            switch (comboFireMeteor){
                case 10: AchievementEngine.sharedAchievementEngine().unlock_aprendice_fire_meteor_slayer();break;
                case 50: AchievementEngine.sharedAchievementEngine().unlock_intermediate_fire_meteor_slayer();break;
                case 100: AchievementEngine.sharedAchievementEngine().unlock_professional_fire_meteor_slayer();break;
                case 200: AchievementEngine.sharedAchievementEngine().unlock_expert_fire_meteor_slayer();break;
            }
        }
    }

    public void playerHit(CCSprite meteor, CCSprite player){
        if (! this.player.isShieldEnable()) {
            checkScoreAchievement();
            ((Meteor) meteor).shooted();
            ((Player) player).explode();
            this.startFinalScreen();
        }else{
            ((Meteor) meteor).shooted();
            this.player.disableShield();
        }
    }

    private void checkScoreAchievement() {
        if (Score.sharedScore().getScore() > 10){
            AchievementEngine.sharedAchievementEngine().unlock_you_cant_break_me();
        }
        if (Score.sharedScore().getScore() > 50){
            AchievementEngine.sharedAchievementEngine().unlock_its_a_trap();
        }
        if (Score.sharedScore().getScore() > 100){
            AchievementEngine.sharedAchievementEngine().unlock_hello_leaderboard();
        }
        if (Score.sharedScore().getScore() > 180){
            AchievementEngine.sharedAchievementEngine().unlock_highway_to_hell();
        }
    }

    public void checkHits(float dt){
        this.checkRadiusHitsOfArray(this.meteorsArray,this.shootsArray,this,"meteoroHit");

        this.checkRadiusHitsOfArray(this.meteorsArray,this.playerArray,this,"playerHit");
    }

    public void scrollScreen(float dt){
        parallaxBackground.scroll(dt);
    }

    private void startEngines(){
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    public boolean shoot(){
        player.shoot();
        return true;
    }

    public boolean shootGreen(){
        player.shootIce();
        return true;
    }

    public void moveLeft(){
        player.moveLeft();
    }

    public void moveRight(){
        player.moveRight();
    }

    public void preloadCache(){
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.shoot);
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.bang);
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.over);
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(),
                R.raw.finalend);


    }

    public void startFinalScreen(){
        CCDirector.sharedDirector().replaceScene(new FinalScreen().scene());
    }

    private void pauseGame(){
        if (!Runner.check().isGamePaused() && Runner.check().isGamePlaying()){
            SoundEngine.sharedEngine().setEffectsVolume(0f);
            SoundEngine.sharedEngine().setSoundVolume(0f);
            Runner.setIsGamePaused(true);
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Runner.check().setIsGamePlaying(true);
        Runner.check().setIsGamePaused(false);

        SoundEngine.sharedEngine().setEffectsVolume(1f);
        SoundEngine.sharedEngine().setSoundVolume(0.3f);

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
    public void clearMeteor(Meteor meteor) {
        this.score.decrease();
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

    @Override
    public void resumeGame() {
        if (Runner.check().isGamePaused() ||
                ! Runner.check().isGamePlaying()){
            SoundEngine.sharedEngine().setEffectsVolume(1f);
            SoundEngine.sharedEngine().setSoundVolume(0.3f);
            this.pauseScreen = null;
            Runner.check().setIsGamePaused(false);
            this.setIsTouchEnabled(true);
        }
    }

    @Override
    public void quitGame() {
        SoundEngine.sharedEngine().setEffectsVolume(0f);
        SoundEngine.sharedEngine().setSoundVolume(0f);
        CCDirector.sharedDirector().replaceScene(new TitleScreen().scene());
    }

    @Override
    public void pauseGameAndShowLayer() {
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            this.pauseGame();
        }
        if (Runner.check().isGamePaused() && Runner.check().isGamePlaying()
                && this.pauseScreen == null){
            this.pauseScreen = new PauseScreen();
            this.layerTop.addChild(this.pauseScreen);
            this.pauseScreen.setDelegate(this);
        }
    }

}
