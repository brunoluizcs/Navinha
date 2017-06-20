package com.bomcodigo.navinha.game.scenes;

import com.bomcodigo.navinha.NavinhaApplication;
import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.Button;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.object.Score;
import com.bomcodigo.navinha.game.screens.ScreenBackground;
import com.bomcodigo.navinha.game.services.GameService;
import com.google.android.gms.games.Games;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;


public class FinalScreen extends CCLayer
        implements ButtonDelegate{
    private ScreenBackground background;
    private Button beginButton;
    private Button titleButton;
    private CCBitmapFontAtlas text;

    public CCScene scene(){
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }

    public FinalScreen() {
        this.background = new ScreenBackground(Assets.BACKGROUND1);
        this.background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
        this.addChild(this.background);

        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.finalend);
        SoundEngine.sharedEngine().pauseSound();
        //SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(),R.raw.finalend,true);

        /*
        CCSprite title = CCSprite.sprite(Assets.FINALEND);
        title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2f,
                                screenHeight() - 130 )));
        this.addChild(title);
        */
        if (GameService.sharedGameService().isConnected()) {
            String leaderboard_id = NavinhaApplication.getContext().getString(R.string.leaderboard_id);
            Games.Leaderboards.submitScore(GameService.sharedGameService().getGoogleApiClient(), leaderboard_id, Score.sharedScore().getScore());
        }

        this.text = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(Score.sharedScore().getScore()),"UniSansSemiBold_Numbers_240.fnt");
        this.text.setScale(1.0f);
        this.text.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2f,screenHeight() - 130 )));
        this.addChild(this.text);


        this.setIsTouchEnabled(true);
        this.beginButton = new Button(Assets.PLAY);
        this.beginButton.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2,
                        screenHeight() -250)));
        this.beginButton.setDelegate(this);
        this.addChild(this.beginButton);

        this.titleButton = new Button(Assets.EXIT);
        this.titleButton.setPosition(CGPoint.ccp(screenWidth() / 2, screenHeight() -300));
        this.titleButton.setDelegate(this);
        this.addChild(this.titleButton);
    }


    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.beginButton)){
            SoundEngine.sharedEngine().pauseSound();
            CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1.0f,GameScene.createGame()));
        }

        if (sender.equals(this.titleButton)){
            SoundEngine.sharedEngine().pauseSound();
            CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1.0f,new TitleScreen().scene()));
        }
    }
}
