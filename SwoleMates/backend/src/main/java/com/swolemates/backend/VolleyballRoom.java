package com.swolemates.backend;

import java.util.ArrayList;

public class VolleyballRoom extends Room {

    @Override
    boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    void sort(ArrayList<SwoleUser> list) {
        // TODO Auto-generated method stub
        list.sort(null);
    }

}
