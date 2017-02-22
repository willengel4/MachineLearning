package botTraining;

import java.awt.Color;
import java.awt.Graphics;

public class Platform extends Actor
{

	public Platform(int x, int y, World w) 
	{
		super(x, y, w);
	}
	
	public void act() 
	{
		changePosition(0, 1);
	}

	public void draw(Graphics g) 
	{
		g.setColor(Color.black);
		g.fillRect(getX(), getY(), ACTOR_SIZE, ACTOR_SIZE);
	}
}
