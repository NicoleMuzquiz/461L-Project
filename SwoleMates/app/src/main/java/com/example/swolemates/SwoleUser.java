package com.example.swolemates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by akkau on 3/18/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwoleUser implements Comparable<SwoleUser> {
    private String rooms;
    private String photoUrl;
    private String email;
    private String name;
    private String playStyle;
    private String id;
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

    public String getPlayStyle() {
        return playStyle;
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

    public void setPlayStyle(String playStyle) {
        this.playStyle = playStyle;
    }

    public void addRoom(String sport) {
        rooms = sport;
    }

    public void removeRoom(String sport) {
        rooms = "";
    }

    public String getRoom() {
        return rooms;
    }

    public List<Integer> getSportRankings(String sport) {
        List<Integer> list = new ArrayList<>();
        if (sport.equals("Basketball")) {
            list.add(getBaseball_rank());
            list.add(getBasketball_skill());
        } else if (sport.equals("Football")) {
            list.add(getFootball_rank());
            list.add(getFootball_skill());
        } else if (sport.equals("Baseball")) {
            list.add(getBaseball_rank());
            list.add(getBaseball_skill());
        } else if (sport.equals("Volleyball")) {
            list.add(getVolleyball_rank());
            list.add(getVolleyball_skill());
        } else if (sport.equals("Swimming")) {
            list.add(getSwimming_rank());
            list.add(getSwimming_skill());
        } else if (sport.equals("Soccer")) {
            list.add(getSoccer_rank());
            list.add(getSoccer_skill());
        } else if (sport.equals("Running")) {
            list.add(getRunning_rank());
            list.add(getRunning_skill());
        } else if (sport.equals("Weightlifting")) {
            list.add(getWeightlifting_rank());
            list.add(getWeightlifting_skill());
        }
        return list;
    }

    @Override
    public int compareTo(SwoleUser otherUser) {

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SwoleUser) {
            SwoleUser p = (SwoleUser) o;
            if (p.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String desc = "";

        /* build user description to be displayed under user profile */
        desc = (name == null) ? desc : desc + "Name: " + name + "\n";
        desc = (height == null) ? desc : desc + "Height: " + height + "\n";
        desc = (weight == null) ? desc : desc + "Weight: " + weight + "\n";
        desc = (age == null) ? desc : desc + "Age: " + age + "\n";
        desc = (playStyle == null) ? desc : desc + "Style: " + playStyle + "\n";
        desc = (weightlifting_skill == null) ? desc : desc + "Skill: " + weightlifting_skill + "\n";
        desc = (basketball_skill == null) ? desc : desc + "Skill: " + baseball_skill + "\n";
        desc = (football_skill == null) ? desc : desc + "Skill: " + football_skill + "\n";
        desc = (volleyball_skill == null) ? desc : desc + "Skill: " + volleyball_skill + "\n";
        desc = (swimming_skill == null) ? desc : desc + "Skill: " + swimming_skill + "\n";
        desc = (baseball_skill == null) ? desc : desc + "Skill: " + baseball_skill + "\n";
        desc = (soccer_skill == null) ? desc : desc + "Skill: " + soccer_skill + "\n";
        desc = (running_skill == null) ? desc : desc + "Skill: " + running_skill + "\n";

        return desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}