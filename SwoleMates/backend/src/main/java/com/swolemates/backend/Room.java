package com.swolemates.backend;

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

    public int getUserCount()
    {
        return userList.size();
    }

    public boolean containsUser(SwoleUser user)
    {
        return userList.contains(user);
    }

    public ArrayList getUserMatches(SwoleUser user)
    {
        ArrayList<SwoleUser> near = new ArrayList<SwoleUser>();

		double current_long = user.getLong();
        double current_lat = user.getLat();*/

        for (SwoleUser u : this.userList) {
			/* TODO: Implement algorithm to find nearby users with similar skill level */

			if (!u.equals(user))
            {
                if (isWithinProperDistance(user, u))
                    near.add(u);
            }
        }
		
		/*
         * This can be done using a comparator, don't need to implement the algorithm
         * sort(near);
		*/

        return near;
    }

    /* 10 miles ~ 0.143 in long/lat; currently looking within 10 mile square for user matches */
    private boolean isWithinProperDistance(SwoleUser current, SwoleUser check) {

        double current_long = current.getLong();
        double current_lat = current.getLat();

        double check_long = check.getLong();
        double check_lat = check.getLat();

        return (Math.abs(check_long - current_long) <= 0.143 && Math.abs(check_lat - current_lat) <= 0.143);

    }
	
	/* each of the following are implemented differently depending on room type */

    abstract boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check);
}
