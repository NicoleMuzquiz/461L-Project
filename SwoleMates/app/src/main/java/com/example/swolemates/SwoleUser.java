package com.example.swolemates;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by akkau on 3/18/2017.
 */

public class SwoleUser {
    private FirebaseUser user;
    private int weight;
    private int height;
    private int weightlifting_skill;
    private int weightlifting_rank;
    private int basketball_skill;
    private int basketball_rank;
    private int football_skill;
    private int football_rank;
    private int volleyball_skill;
    private int volleyball_rank;
    private int swimming_skill;
    private int swimming_rank;
    private int baseball_skill;
    private int baseball_rank;
    private int soccer_skill;
    private int soccer_rank;
    private int running_skill;
    private int running_rank;


    // Getting Variables
    public int getWeightlifting_skill(){
        return weightlifting_skill;
    }
    public int getWeightlifting_rank(){
        return weightlifting_rank;
    }
    public int getWeight(){
        return weight;
    }
    public int getHeight(){
        return height;
    }
    public FirebaseUser getFirebaseUser(){
        return user;
    }
    public int getBasketball_skill(){
        return basketball_skill;
    }
    public int getBasketball_rank(){
        return basketball_rank;
    }
    public int getFootball_skill(){
        return football_skill;
    }
    public int getFootball_rank(){
        return football_rank;
    }
    public int getVolleyball_skill(){
        return volleyball_skill;
    }
    public int getVolleyball_rank(){
        return volleyball_rank;
    }
    public int getSwimming_skill(){
        return swimming_skill;
    }
    public int getSwimming_rank(){
        return swimming_rank;
    }
    public int getBaseball_skill(){
        return baseball_skill;
    }
    public int getBaseball_rank(){
        return baseball_rank;
    }
    public int getSoccer_skill(){
        return soccer_skill;
    }
    public int getSoccer_rank(){
        return soccer_rank;
    }
    public int getRunning_skill(){
        return running_skill;
    }
    public int getRunning_rank(){
        return running_rank;
    }

    //Setting variables
    public void setWeightlifting_skill(int skill){
        weightlifting_skill = skill;
    }
    public void setWeightlifting_rank(int rank){
        weightlifting_rank = rank;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setFirebaseUser(FirebaseUser user){
        this.user = user;
    }
    public void setBasketball_skill(int skill){
        basketball_skill = skill;
    }
    public void setBasketball_rank(int rank){
        basketball_rank = rank;
    }
    public void setFootball_skill(int skill){
        football_skill = skill;
    }
    public void setFootball_rank(int rank){
        football_rank = rank;
    }
    public void setVolleyball_skill(int skill){
        volleyball_skill = skill;
    }
    public void setVolleyball_rank(int rank){
        volleyball_rank = rank;
    }
    public void setSwimming_skill(int skill){
        swimming_skill = skill;
    }
    public void setSwimming_rank(int rank){
        swimming_rank = rank;
    }
    public void setBaseball_skill(int skill){
        baseball_skill = skill;
    }
    public void setBaseball_rank(int rank){
        baseball_rank = rank;
    }
    public void setSoccer_skill(int skill){
        soccer_skill = skill;
    }
    public void setSoccer_rank(int rank){
        soccer_rank = rank;
    }
    public void setRunning_skill(int skill){
        running_skill = skill;
    }
    public void setRunning_rank(int rank){
        running_rank = rank;
    }
}
