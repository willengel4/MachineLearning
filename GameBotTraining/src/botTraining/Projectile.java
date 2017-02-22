package botTraining;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile extends Actor
{
	public int xChange, yChange;
	public int direction;
	public boolean doneActing;
	
	public Projectile(int x, int y, World w, int xChange, int yChange) 
	{
		super(x, y, w);
		
		this.xChange = xChange;
		this.yChange = yChange;
		this.direction = 0;
		doneActing = false;
		
		boolean done = false;
		for(int i = -1; i <= 1 && !done; i++)	
		{
			for(int f = -1; f <= 1; f++, direction++)
			{
				if(xChange == i && yChange == f)
				{
					done = true;
					break;
				}
			}
		}
	}

	public void act() 
	{		
		doneActing = (getX() < 0 || getX() > getWorld().WORLD_WIDTH || getY() < 0 || getY() > getWorld().WORLD_HEIGHT);
		this.changePosition(xChange, yChange);
	}

	public void draw(Graphics g) 
	{
		g.setColor(Color.red);
		g.fillRect(getX(), getY(), ACTOR_SIZE, ACTOR_SIZE);
	}
}
