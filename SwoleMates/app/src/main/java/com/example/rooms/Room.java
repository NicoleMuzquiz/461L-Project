package com.example.rooms;

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
			/* TODO: Implement algorithm to find nearby users

			if (isWithinProperDistance(user, u) && isWithinProperSkillLevel(user, u)
				near.add(u);
		 	*/
        }

		/*
		sort(near);
		*/

        return near;
    }

    private boolean isWithinProperDistance(SwoleUser current, SwoleUser check) {
		/*
		int current_long = current.getLong();
		int current_lat = current.getLat();

		int check_long = check.getLong();
		int check_lat = check.getLat();


		return (Math.abs(check_long - current_long) <= 50 && Math.abs(check_lat - current_lat) <= 50);
		*/

		/*TODO: remove comments & final 'return false' statement */
        return false;
    }

	/* each of the following are implemented differently depending on room type */

    abstract boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check);

    abstract void sort(ArrayList<SwoleUser> list);

}
