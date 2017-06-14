package com.bomcodigo.navinha;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bomcodigo.navinha.game.scenes.TitleScreen;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CCGLSurfaceView ccglSurfaceView = new CCGLSurfaceView(this);
        setContentView(ccglSurfaceView);
        CCDirector.sharedDirector().attachInView(ccglSurfaceView);
        CCDirector.sharedDirector().setScreenSize(320,480);

        CCScene scene = new TitleScreen().scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }
}
