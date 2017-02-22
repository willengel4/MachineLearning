package botTraining;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import botTraining.Utility.*;

public class Person extends Actor
{
	private int jumping;
	private int jumpDir;
	private boolean didSomething;
	private boolean offGrid;
	private boolean done;
	private int fallScreen;
	private int lastMove;
	private LearningSystem learningSystem;
	private ActionState actionState;

	public Person(int x, int y, World w, LearningSystem ls)
	{
		super(x, y, w);
		jumping = 0;
		jumpDir = 0;
		didSomething = false;
		offGrid = false;
		done = false;
		fallScreen = 40;
		learningSystem = ls;
		actionState = ActionState.TRAINED;
	}
	
	public void act() 
	{		
		/* 0 -> 1 */
		didSomething = false;
		
		offGrid = getX() > getWorld().WORLD_WIDTH || getX() < 0 || getY() < 0 || getY() > getWorld().WORLD_HEIGHT;
		
		if(fallScreen == 0)
		{
			getWorld().setMaxMoves();
		}
		
		if(jumping > 0)
		{
			changePosition(0, -1);
			jumping--;
			if(jumping == 0)
				changePosition(jumpDir, 0);
		}
		else
		{
			/* If we are on the ground
			 * changePosition(0, 1) returns false if there is an object below the actor */
			if(!changePosition(0, 1) && fallScreen <= 0)
			{
				/* Set didSomehting to true because we are making decisions now */
				didSomething = true;
				
				int action = 0;
				
				if(actionState == ActionState.TRAINED)
				{
					try 
					{
						action = learningSystem.classify(getWorld().getJustWorld());
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				else
				{
					action = Utility.random.nextInt(2);
					
					if(action == 0)
						action = 1;
					else
						action = 3;
				}

				
				switch(action)
				{
				case 0:
					stayStill();
					break;
				case 1:
					moveRight();
					break;
				case 2:
					moveLeft();
					break;
				case 3:
					jumpRight();
					break;
				case 4:
					jumpLeft();
					break;
				case 5:
					jumpUp();
					break;
				}
			}
			
			/* If we aren't on the ground, continue falling, no user action is allowed */
			else 
			{ }
			
			fallScreen -= 1;
		}
	}
	
	public void draw(Graphics g) 
	{
		g.setColor(Color.green);
		g.fillRect(getX(), getY(), getCollisionSpace().width, getCollisionSpace().height);
	}
	
	public void setDone(boolean d)
	{
		done = d;
	}
	
	public boolean getDone()
	{
		return done;
	}
	
	public boolean offGrid()
	{
		return offGrid;
	}
	
	public boolean didSomething()
	{
		return didSomething;
	}
	
	public void stayStill()
	{
		lastMove = 0;
	}
	
	public void moveRight()
	{
		lastMove = 1;
		changePosition(1, 0);
	}
	
	public void moveLeft()
	{
		lastMove = 2;
		changePosition(-1, 0);
	}
	
	public void jumpRight()
	{
		jumping = 3;
		jumpDir = 1;
		lastMove = 3;
	}
	
	public void jumpLeft()
	{
		jumping = 3;
		jumpDir = -1;
		lastMove = 4;
	}
	
	public void jumpUp()
	{
		jumping = 3;
		jumpDir = 0;
		lastMove = 5;
	}
	
	public void shootLeft()
	{
		
	}
	
	public void shootRight()
	{
		
	}
	
	public void shootUp()
	{
		
	}
	
	public void shootUpLeft()
	{
		
	}
	
	public void shootUpRight()
	{
		
	}
	
	public int getLastMove()
	{
		return lastMove;
	}
}
