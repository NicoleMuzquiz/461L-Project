import java.util.ArrayList;

public class WeightLiftingRoom extends Room
{
	@Override 
	boolean isWithinProperSkillLevel(SwoleUser current, SwoleUser check)
	{
		/*
		 * TODO: algo to detect whether users are compatable, adjusted to weightlifting skill
		 */
		return false;
	}
	
	@Override 
	void sort(ArrayList<SwoleUser> list)
	{
		/* 
		 * TODO: algo to sort users by best pairings, returned in order of best to worst
		 */
	}
}
