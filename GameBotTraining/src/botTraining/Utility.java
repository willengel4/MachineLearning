package botTraining;

import java.util.Random;

public class Utility 
{
	public static Random random = new Random();
	
	public static int getRandom(int low, int high)
	{
		return random.nextInt(high - low) + low;
	}
	
	public static enum ActionState { RANDOM, TRAINED }
}
