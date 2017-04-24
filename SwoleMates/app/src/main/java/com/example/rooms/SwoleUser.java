package com.example.rooms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by akkau on 3/18/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwoleUser implements Comparable<SwoleUser> {

    private String photoUrl;
    private String email;
    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
    private Integer weightlifting_skill;
    private Integer weightlifting_rank;
    private Integer basketball_skill;
    private Integer basketball_rank;
    private Integer football_skill;
    private Integer football_rank;
    private Integer volleyball_skill;
    private Integer volleyball_rank;
    private Integer swimming_skill;
    private Integer swimming_rank;
    private Integer baseball_skill;
    private Integer baseball_rank;
    private Integer soccer_skill;
    private Integer soccer_rank;
    private Integer running_skill;
    private Integer running_rank;

    /* location variables */
    private Double latitude;
    private Double longitude;

    /* get & set location variables */
    public Double getLat() {
        return this.latitude;
    }

    public Double getLong() {
        return this.longitude;
    }

    public void setLat(Double latitude) {
        this.latitude = latitude;
    }

    public void setLong(Double longitude) {
        this.longitude = longitude;
    }

    // Getting Variables
    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getWeightlifting_skill() {
        return weightlifting_skill;
    }

    public Integer getWeightlifting_rank() {
        return weightlifting_rank;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getBasketball_skill() {
        return basketball_skill;
    }

    public Integer getBasketball_rank() {
        return basketball_rank;
    }

    public Integer getFootball_skill() {
        return football_skill;
    }

    public Integer getFootball_rank() {
        return football_rank;
    }

    public Integer getVolleyball_skill() {
        return volleyball_skill;
    }

    public Integer getVolleyball_rank() {
        return volleyball_rank;
    }

    public Integer getSwimming_skill() {
        return swimming_skill;
    }

    public Integer getSwimming_rank() {
        return swimming_rank;
    }

    public Integer getBaseball_skill() {
        return baseball_skill;
    }

    public Integer getBaseball_rank() {
        return baseball_rank;
    }

    public Integer getSoccer_skill() {
        return soccer_skill;
    }

    public Integer getSoccer_rank() {
        return soccer_rank;
    }

    public Integer getRunning_skill() {
        return running_skill;
    }

    public Integer getRunning_rank() {
        return running_rank;
    }

    //Setting variables
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setEmail(String eMail) {
        email = eMail;
    }

    public void setName(String n) {
        name = n;
    }

    public void setAge(Integer a) {
        age = a;
    }

    public void setWeightlifting_skill(Integer skill) {
        weightlifting_skill = skill;
    }

    public void setWeightlifting_rank(Integer rank) {
        weightlifting_rank = rank;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setBasketball_skill(Integer skill) {
        basketball_skill = skill;
    }

    public void setBasketball_rank(Integer rank) {
        basketball_rank = rank;
    }

    public void setFootball_skill(Integer skill) {
        football_skill = skill;
    }

    public void setFootball_rank(Integer rank) {
        football_rank = rank;
    }

    public void setVolleyball_skill(Integer skill) {
        volleyball_skill = skill;
    }

    public void setVolleyball_rank(Integer rank) {
        volleyball_rank = rank;
    }

    public void setSwimming_skill(Integer skill) {
        swimming_skill = skill;
    }

    public void setSwimming_rank(Integer rank) {
        swimming_rank = rank;
    }

    public void setBaseball_skill(Integer skill) {
        baseball_skill = skill;
    }

    public void setBaseball_rank(Integer rank) {
        baseball_rank = rank;
    }

    public void setSoccer_skill(Integer skill) {
        soccer_skill = skill;
    }

    public void setSoccer_rank(Integer rank) {
        soccer_rank = rank;
    }

    public void setRunning_skill(Integer skill) {
        running_skill = skill;
    }

    public void setRunning_rank(Integer rank) {
        running_rank = rank;
    }

    @Override
    public int compareTo(SwoleUser otherUser) {

        return 0;
    }
}