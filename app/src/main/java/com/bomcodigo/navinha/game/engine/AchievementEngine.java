package com.bomcodigo.navinha.game.engine;


import com.bomcodigo.navinha.NavinhaApplication;
import com.bomcodigo.navinha.R;
import com.bomcodigo.navinha.game.interfaces.AchievementEngineDelegate;

public class AchievementEngine {
    private AchievementEngineDelegate delegate;
    private static AchievementEngine instance = null;

    private String achievement_triumphant_entry;
    private String lets_play_a_game;
    private String aprendice_ice_meteor_slayer;
    private String aprendice_fire_meteor_slayer;
    private String you_cant_break_me;
    private String intermediate_ice_meteor_slayer;
    private String intermediate_fire_meteor_slayer;
    private String its_a_trap;
    private String professional_ice_meteor_slayer;
    private String professional_fire_meteor_slayer;
    private String expert_ice_meteor_slayer;
    private String expert_fire_meteor_slayer;
    private String hello_leaderboard;
    private String highway_to_hell;

    private AchievementEngine() {
        this.achievement_triumphant_entry = NavinhaApplication.getContext().getString(R.string.achievement_triumphant_entry);
        this.lets_play_a_game = NavinhaApplication.getContext().getString(R.string.achievement_lets_play_a_game);
        this.aprendice_ice_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_aprendice_ice_meteor_slayer);
        this.aprendice_fire_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_aprendice_fire_meteor_slayer);
        this.you_cant_break_me = NavinhaApplication.getContext().getString(R.string.achievement_you_cantt_break_me);
        this.intermediate_ice_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_intermediate_ice_meteor_slayer);
        this.intermediate_fire_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_intermediate_fire_meteor_slayer);
        this.its_a_trap = NavinhaApplication.getContext().getString(R.string.achievement_its_a_trap);
        this.professional_ice_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_professional_ice_meteor_slayer);
        this.professional_fire_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_professional_fire_meteor_slayer);
        this.expert_ice_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_expert_ice_meteor_slayer);
        this.expert_fire_meteor_slayer = NavinhaApplication.getContext().getString(R.string.achievement_expert_fire_meteor_slayer);
        this.hello_leaderboard = NavinhaApplication.getContext().getString(R.string.achievement_hello_leaderboard);
        this.highway_to_hell = NavinhaApplication.getContext().getString(R.string.achievement_highway_to_hell);

    }

    public static AchievementEngine sharedAchievementEngine(){
        if (instance == null){
            instance = new AchievementEngine();
        }
        return instance;
    }

    public void setDelegate(AchievementEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void unlock_triumphant_entry(){
        this.delegate.unlock(this.achievement_triumphant_entry);
    }

    public void unlock_lets_play_a_game(){
        this.delegate.unlock(this.lets_play_a_game);
    }

    public void unlock_aprendice_ice_meteor_slayer(){
        this.delegate.unlock(this.aprendice_ice_meteor_slayer);
    }

    public void unlock_aprendice_fire_meteor_slayer() {
        this.delegate.unlock(this.aprendice_fire_meteor_slayer);
    }

    public void unlock_you_cant_break_me() {
        this.delegate.unlock(this.you_cant_break_me);
    }

    public void unlock_intermediate_ice_meteor_slayer(){
        this.delegate.unlock(this.intermediate_ice_meteor_slayer);
    }

    public void unlock_intermediate_fire_meteor_slayer(){
        this.delegate.unlock(this.intermediate_fire_meteor_slayer);
    }

    public void unlock_its_a_trap(){
        this.delegate.unlock(this.its_a_trap);
    }

    public void unlock_professional_ice_meteor_slayer(){
        this.delegate.unlock(this.professional_ice_meteor_slayer);
    }

    public void unlock_professional_fire_meteor_slayer(){
        this.delegate.unlock(this.professional_fire_meteor_slayer);
    }

    public void unlock_expert_ice_meteor_slayer(){
        this.delegate.unlock(this.expert_ice_meteor_slayer);
    }

    public void unlock_expert_fire_meteor_slayer(){
        this.delegate.unlock(this.expert_fire_meteor_slayer);
    }

    public void unlock_hello_leaderboard(){
        this.delegate.unlock(this.hello_leaderboard);
    }

    public void unlock_highway_to_hell(){
        this.delegate.unlock(this.highway_to_hell);
    }


}
