package com.bomcodigo.navinha;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bomcodigo.navinha.game.DeviceSettings;
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

        configSensorManager();

        CCScene scene = new TitleScreen().scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

    public void configSensorManager(){
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        DeviceSettings.setSensorManager(sensorManager);
    }

}
