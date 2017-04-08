import java.util.ArrayList;

/**
 * @author Samuel Patterson
 * 
 * Class to maintain list of users in each 'room'. Can add and remove users, as well as detect matchings.
 * Also maintains user count so you can know if room is popular or not.
 */

public class Room
{
	private ArrayList<SwoleUser> userList = new ArrayList<SwoleUser>();
	
	public void addUser(SwoleUser user)
	{
		if (!userList.contains(user))
			userList.add(user);
		
	}
	
	public void removeUser(SwoleUser user)
	{
		if (userList.contains(user))
			userList.remove(user);
	}
	
	public int getUserCount()
	{
		return userList.size();
	}
	
	public ArrayList getUserMatches(SwoleUser user)
	{
		ArrayList<SwoleUser> near = new ArrayList<SwoleUser>();
		
		/*int current_long = user.getLong();
		int current_lat = user.getLat();*/
		
		for (SwoleUser u : this.userList)
		{
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
	
	private boolean isWithinProperDistance(SwoleUser current, SwoleUser check)
	{
		/*int current_long = current.getLong();
		int current_lat = current.getLat();
		
		int check_long = current.getLong();
		int check_lat = current.getLat();
		
		
		return (Math.abs(check_long - current_long) <= 50 && Math.abs(check_lat - current_lat) <= 50);
		*/
		
		/*TODO: remove comments & final 'return false' statement */
		return false;
	}
	
	private boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check)
	{
		/*
		 * TODO: algo to detect whether users are compatable 
		 */
		return false;
	}
	
	private void sort(ArrayList<SwoleUser> list)
	{
		/* 
		 * TODO: algo to sort users by best pairings, returned in order of best to worst
		 */
	}
	
}
