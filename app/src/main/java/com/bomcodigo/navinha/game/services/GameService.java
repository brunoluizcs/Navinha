package com.bomcodigo.navinha.game.services;


import android.app.Activity;

import com.bomcodigo.navinha.NavinhaApplication;
import com.bomcodigo.navinha.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import static com.google.android.gms.common.api.GoogleApiClient.SIGN_IN_MODE_OPTIONAL;

public class GameService {
    private final String TAG = GameService.class.getSimpleName();

    private static GameService instance = null;

    private GoogleApiClient googleApiClient;

    public void init(Activity activity, GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                      GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestServerAuthCode(NavinhaApplication.getContext().getString(R.string.webclientid))
                .build();

        this.googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Games.API)
                //.addScope(Games.SCOPE_GAMES)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .build();
    }

    private GameService() {
    }

    public static GameService sharedGameService(){
        if (instance == null){
            instance = new GameService();
        }
        return instance;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void connect(){
        if (this.googleApiClient != null && ! this.googleApiClient.isConnected() && ! this.googleApiClient.isConnecting()) {
            this.googleApiClient.connect(SIGN_IN_MODE_OPTIONAL);
        }
    }

    public void disconnect(){
        if (this.googleApiClient != null && this.googleApiClient.isConnected()){
            this.googleApiClient.disconnect();
        }
    }

    public boolean isConnected(){
        return googleApiClient != null && googleApiClient.isConnected();
    }

    public boolean hasConnectGameApi(){
        return this.googleApiClient.hasConnectedApi(Games.API);
    }

    public void showLeaderBoard(Activity activity) {
        if (hasConnectGameApi()) {
            String leaderboard_id = NavinhaApplication.getContext().getString(R.string.leaderboard_top_meteor_killers);
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                    this.getGoogleApiClient(),leaderboard_id), 9002);
        }
    }

    public void showAchievements(Activity activity) {
        if (hasConnectGameApi()) {
            activity.startActivityForResult(Games.Achievements.getAchievementsIntent(
                    this.getGoogleApiClient()), 9003);
        }
    }

    public void unlockAchievement(String achievement_id){
        if (isConnected() && hasConnectGameApi()) {
            Games.Achievements.unlock(this.getGoogleApiClient(), achievement_id);
        }
    }
}
