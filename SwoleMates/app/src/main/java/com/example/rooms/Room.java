package com.example.rooms;

import com.example.swolemates.SwoleUser;

import java.util.ArrayList;

/**
 * @author Samuel Patterson
 *         <p>
 *         Class to maintain list of users in each 'room'. Can add and remove users, as well as detect matchings.
 *         Also maintains user count so you can know if room is popular or not.
 */

public abstract class Room {
    private ArrayList<SwoleUser> userList = new ArrayList<SwoleUser>();

    public void addUser(SwoleUser user) {
        if (!userList.contains(user))
            userList.add(user);

    }

    public void removeUser(SwoleUser user) {
        if (userList.contains(user))
            userList.remove(user);
    }

    public int getUserCount() {
        return userList.size();
    }

    public ArrayList getUserMatches(SwoleUser user) {
        ArrayList<SwoleUser> near = new ArrayList<SwoleUser>();

		/*int current_long = user.getLong();
        int current_lat = user.getLat();*/

        for (SwoleUser u : this.userList) {
			/* TODO: Implement algorithm to find nearby users with similar skill level

			if (isWithinProperDistance(user, u) && isWithinProperSkillLevel(user, u)
				near.add(u);
		 	*/
        }

		/*
		sort(near);
		*/

        return near;
    }

    /* 10 miles ~ 0.143 in long/lat; currently looking within 10 mile square for user matches */
    private boolean isWithinProperDistance(SwoleUser current, SwoleUser check) {

        double current_long = current.getLong();
        double current_lat = current.getLat();

        double check_long = current.getLong();
        double check_lat = current.getLat();

        return (Math.abs(check_long - current_long) <= 0.143 && Math.abs(check_lat - current_lat) <= 0.143);

    }

	/* each of the following are implemented differently depending on room type */

    abstract boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check);

    abstract void sort(ArrayList<SwoleUser> list);

}
