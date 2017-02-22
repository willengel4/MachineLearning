package botTraining;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class MainInterface extends Applet
{
	private Image bufferedImage;
	private Graphics g;
	private Random rng;
	private World world;
	private LearningSystem learningSystem;
	
	public void init()
	{
		/* Applet stuff */
		setSize(400, 400);
		setBackground(Color.white);
		bufferedImage = createImage(this.getWidth(), this.getHeight());
		g = bufferedImage.getGraphics();
		rng = new Random();
		
		learningSystem = new LearningSystem();
		
		/* World stuff */
		world = new World(learningSystem);
	}
	
	public void paint(Graphics gr)
	{		
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		world.act();
		world.draw(g);
		
		gr.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), this);
		
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		repaint();
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void shoot(int x, int y)
	{
		Projectile newProjectile = new Projectile(x, 0, world, 0, 1);
		world.addActor(newProjectile);
	}
	
	public boolean mouseDown(Event e, int x, int y)
	{
		shoot(x, y);
		return true;
	}
}
