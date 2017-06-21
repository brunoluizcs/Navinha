package com.bomcodigo.navinha.game.control;

import android.util.Log;

import com.bomcodigo.navinha.NavinhaApplication;
import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.scenes.GameScene;
import com.bomcodigo.navinha.game.services.GameService;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class MenuButtons extends CCLayer
        implements ButtonDelegate {
    private static final String TAG = MenuButtons.class.getSimpleName();

    private Button playButton;
    private Button highscoreButton;
    private Button achievementsButton;
    //private Button helpButton;
    private Button soundButton;

    public MenuButtons(){
        this.setIsTouchEnabled(true);
        this.playButton = new Button(Assets.PLAY);
        this.playButton.setDelegate(this);

        this.highscoreButton = new Button(Assets.HIGHSCORE);
        this.highscoreButton.setDelegate(this);

        this.achievementsButton = new Button(Assets.ACHIEVEMENTS);
        this.achievementsButton.setDelegate(this);

        //TODO: Remover o help button
        //this.helpButton = new Button(Assets.HELP);
        //this.helpButton.setDelegate(this);

        this.soundButton = new Button(Assets.SOUND);
        this.soundButton.setDelegate(this);

        setButtonsPosition();

        addChild(playButton);
        addChild(highscoreButton);
        addChild(achievementsButton);
        addChild(soundButton);
    }

    public void setButtonsPosition(){
        playButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 200)));
        achievementsButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 250)));
        highscoreButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 300)));
        soundButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 -100 , screenHeight() - 390)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.playButton)){
            Log.d(TAG,"Button clicked: Play");
            if (! GameService.sharedGameService().isConnected()){
                GameService.sharedGameService().connect();
            }
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(1.0f,GameScene.createGame()));

        }
        if (sender.equals(this.highscoreButton)){
            Log.d(TAG,"Button clicked: Highscore");
            GameService.sharedGameService().connect();

            if (GameService.sharedGameService().isConnected()) {
                GameService.sharedGameService()
                        .showLeaderBoard(CCDirector.sharedDirector().getActivity());
            }else{
                String error = NavinhaApplication.getContext()
                        .getString(R.string.google_play_game_connection_error);

                //TODO: Criar uma activity para exibir a mensagem de erro
                //Toast.makeText(NavinhaApplication.getContext(),error,Toast.LENGTH_LONG).show();
            }
        }
        if (sender.equals(this.achievementsButton)){
            Log.d(TAG,"Button clicked: Achievements");
            GameService.sharedGameService().connect();

            if (GameService.sharedGameService().isConnected()) {
                GameService.sharedGameService().showAchievements(CCDirector.sharedDirector().getActivity());
            }else{
                String error = NavinhaApplication.getContext()
                        .getString(R.string.google_play_game_connection_error);
                //TODO: Criar uma activity para exibir a mensagem de erro
                //Toast.makeText(NavinhaApplication.getContext(),error,Toast.LENGTH_LONG).show();
            }

        }
        if (sender.equals(this.soundButton)){
            Log.d(TAG,"Button Clicked: Sound");
        }
    }
}
