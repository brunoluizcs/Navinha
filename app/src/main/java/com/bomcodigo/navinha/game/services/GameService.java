package com.bomcodigo.navinha.game.services;


import com.bomcodigo.navinha.NavinhaApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GameService {
    private final String TAG = GameService.class.getSimpleName();

    private static GameService instance = null;

    private GoogleApiClient googleApiClient;

    public void init(GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                      GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){
        this.googleApiClient = new GoogleApiClient.Builder(NavinhaApplication.getContext())
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
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
        this.googleApiClient.connect();
    }
}
