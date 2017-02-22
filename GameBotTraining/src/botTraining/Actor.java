package botTraining;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Actor 
{
	private int x, y;
	private Rectangle collisionSpace;
	private World world;
	protected final int MOVEMENT_MULTIPLIER = 10;
	protected final int ACTOR_SIZE = 20;
	
	public Actor(int x, int y, World w)
	{
		this.x = x;
		this.y = y;
		world = w;
		collisionSpace = new Rectangle(x, y, ACTOR_SIZE, ACTOR_SIZE);
	}
	
	public abstract void act();
	public abstract void draw(Graphics g);
	
	/* Changes the position of the actor
	 * returns true if there was no blockage
	 * returns false if there was a blockage */
	protected boolean changePosition(int xChange, int yChange)
	{
		/* Note the original position in case it needs to be reverted */
		int originalX = x;
		int originalY = y;
		
		/* Make the change */
		x += xChange * MOVEMENT_MULTIPLIER;
		y += yChange * MOVEMENT_MULTIPLIER;
		collisionSpace.x = x;
		collisionSpace.y = y;
		
		if(world.collision(this))
		{
			boolean foundSpot = false;
			int maxBackIterations = Math.max(x, y) * MOVEMENT_MULTIPLIER;
			
			for(int i = 0; i < maxBackIterations && !foundSpot; i++)
			{
				x -= xChange;
				y -= yChange;
				collisionSpace.x = x;
				collisionSpace.y = y;
				
				foundSpot = !world.collision(this);
			}
			
			if(!foundSpot)
			{
				x = originalX;
				y = originalY;
			}
			
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public World getWorld()
	{
		return world;
	}
	
	protected void setX(int x)
	{
		this.x = x;
		this.collisionSpace.x = x;
	}
	
	protected void setY(int y)
	{
		this.y = y;
		this.collisionSpace.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Rectangle getCollisionSpace()
	{
		return collisionSpace;
	}
}
