package com.example.rooms;

import java.util.ArrayList;
import java.util.Collections;

public class VolleyballRoom extends Room {

    @Override
    boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    void sort(ArrayList<SwoleUser> list) {
        // TODO Auto-generated method stub
        Collections.sort(list);
    }

}
