package com.bomcodigo.navinha;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bomcodigo.navinha.game.DeviceSettings;
import com.bomcodigo.navinha.game.scenes.TitleScreen;
import com.bomcodigo.navinha.game.services.GameService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameUtils;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private final String TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

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

        GameService.sharedGameService().init(this,this);
        mGoogleApiClient = GameService.sharedGameService().getGoogleApiClient();
        CCScene scene = new TitleScreen().scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GameService.sharedGameService().connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        GameService.sharedGameService().disconnect();
    }

    public void configSensorManager(){
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        DeviceSettings.setSensorManager(sensorManager);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"Player Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"Attempt Reconnect");
        GameService.sharedGameService().connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure){
            Log.d(TAG,"Connection failure already resolving");
            return;
        }

        if (mSignInClicked || mAutoStartSignInflow){
            mAutoStartSignInflow= false;
            mSignInClicked =false;
            mResolvingConnectionFailure= true;
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient,connectionResult,
                    RC_SIGN_IN,R.string.sign_in_other_error)){
                mResolvingConnectionFailure = false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if  (requestCode == RC_SIGN_IN){
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK){
                GameService.sharedGameService().connect();
            }else{
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.sign_in_failed);
            }
        }
    }
}
