package botTraining;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class World 
{
	private ArrayList<Actor> actors;
	private double [][] inputMatrix;
	public final int MAX_MOVES = 100;
	public final int INPUT_SIZE = 5;
	public final int WORLD_WIDTH = 370;
	public final int WORLD_HEIGHT = 350;
	public final int LENIENCE = 0;
	public int currMoveNum, currMaxMoveNum;
	private LearningSystem learningSystem;
	
	/* For ease of access */
	private ArrayList<Platform> obstacles;
	private Person person;
	private Star star;
	private ArrayList<Projectile> projectiles;
	
	public World(LearningSystem ls)
	{
		actors = new ArrayList<Actor>();
		inputMatrix = new double[MAX_MOVES][INPUT_SIZE];
		currMoveNum = 0;
		currMaxMoveNum = 0;
		obstacles = new ArrayList<Platform>();
		projectiles = new ArrayList<Projectile>();
		learningSystem = ls;
		setUpWorld();
	}
	
	/* Tells all of the actors to act */
	public void act()
	{
		if(currMoveNum >= currMaxMoveNum)
		{
			setUpWorld();
		}
		
		ArrayList<Actor> removeThese = new ArrayList<Actor>();
		boolean resetWorld = false;
		/* Goes through all of the actors, telling each one to run it's act method */
		for(Actor a : actors)
		{
			/* When it is the person's turn, the world's current state is appended to the current
			 * input matrix. After the person acts, the input row is labeled */
			if(a instanceof Person)
			{
				Person p = (Person)a;
				
				double [] world = getWorld();
				a.act();
				if(p.didSomething())
				{
					world[world.length - 1] = p.getLastMove();
					
					for(int i = 0; i < world.length; i++)
						inputMatrix[currMoveNum][i] = world[i];
					
					currMoveNum++;
				}
				
				if (p.getDone())
				{
					learningSystem.appendInput(inputMatrix, currMoveNum);
					resetWorld = true;
				}
				if(p.offGrid())
					resetWorld = true;
			}
			else if(a instanceof Projectile)
			{
				a.act();
				
				if(((Projectile)a).doneActing)
					removeThese.add(a);
			}
			else
			{
				a.act();
			}
		}
		
		for(Actor a : removeThese)
			actors.remove(a);
		
		if(resetWorld)
			setUpWorld();
	}
	
	public double[] getJustWorld()
	{		
		double[] world = new double[INPUT_SIZE - 1];
	
		world[0] = this.person.getX();
		world[1] = this.person.getY();
		world[2] = this.star.getX();
		world[3] = this.star.getY();
		
		return world;
	}
	
	public double[] getWorld()
	{
		double[] world = new double[INPUT_SIZE];
		
		world[0] = this.person.getX();
		world[1] = this.person.getY();
		world[2] = this.star.getX();
		world[3] = this.star.getY();
		
		return world;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.blue);
		g.drawString("" + currMoveNum, 10, 10);
		g.drawString("" + currMaxMoveNum, 10, 25);

		for(Actor a : actors)
			a.draw(g);
	}
	
	public void setUpWorld()
	{
		actors.clear();
		obstacles.clear();
		projectiles.clear();
		person = null;
		star = null;
		for(int i = 0; i < inputMatrix.length; i++)
			for(int f = 0; f < inputMatrix[i].length; f++)
				inputMatrix[i][f] = 0;
		
		/* First things first, the ground */
		for(int i = 0; i < 20; i++)
		{
			Ground ground = new Ground(i * 20, WORLD_HEIGHT, this);
			addActor(ground);
		}
		
//		/* Add some platforms */
//		for(int i = 0; i < 1; i++)
//		{
//			Platform newPlatform = new Platform(Utility.random.nextInt(WORLD_WIDTH), Utility.random.nextInt(WORLD_HEIGHT), this);
//			addActor(newPlatform);
//		}
		
		/* Add the star */
		Star star = new Star(Utility.random.nextInt(WORLD_WIDTH), Utility.random.nextInt(WORLD_HEIGHT), this);
		addActor(star);
		
		/* Add the person */
		Person person = new Person(Utility.random.nextInt(WORLD_WIDTH), Utility.random.nextInt(WORLD_HEIGHT), this, learningSystem);
		addActor(person);
		
		currMaxMoveNum = (Math.abs(person.getX() - star.getX()) / 10) + LENIENCE;
		if(person.getY() < star.getY())
			currMaxMoveNum += (person.getY() - star.getY()) / 10;
		
		currMoveNum = 0;
	}
	
	public void setMaxMoves()
	{
		currMaxMoveNum = (Math.abs(person.getX() - star.getX()) / 10) + LENIENCE;
		if(person.getY() < star.getY())
			currMaxMoveNum += (person.getY() - star.getY()) / 10;
	}
	
	public void handleCollisions(Actor checkActor, Actor actor)
	{
		/* Person-Star collisions*/
		if(checkActor instanceof Person && actor instanceof Star)
			((Person)checkActor).setDone(true);
		else if(checkActor instanceof Star && actor instanceof Person)
			((Person)actor).setDone(true);
		
		/* Person-Projectile collisions */
		if(checkActor instanceof Person && actor instanceof Projectile)
			((Person)checkActor).setDone(true);
		else if(checkActor instanceof Projectile && actor instanceof Person)
			((Person)actor).setDone(true);
		
		/* Other projectile collisions */
		if(checkActor instanceof Projectile)
			((Projectile)checkActor).doneActing = true;
		else if(actor instanceof Projectile)
			((Projectile)actor).doneActing = true;
	}
	
	/* Checks if the actor is colliding with any other actors */
	public boolean collision(Actor actor)
	{	
		for(Actor checkActor : actors)
		{
			if(checkActor != actor && checkActor.getCollisionSpace().intersects(actor.getCollisionSpace()))
			{	
				handleCollisions(checkActor, actor);
				return true;
			}
		}
		
		return false;
	}
	
	public void addActor(Actor a)
	{
		while(collision(a))
		{
			a.setX(Utility.random.nextInt(WORLD_WIDTH));
			a.setY(Utility.random.nextInt(WORLD_HEIGHT));
		}
		actors.add(a);
		
		if(a instanceof Person)
			this.person = (Person)a;
		else if(a instanceof Star)
			this.star = (Star)a;
		else if(a instanceof Platform)
			obstacles.add((Platform)a);
		else if(a instanceof Projectile)
			projectiles.add((Projectile)a);
		else
			System.out.println("none of those!");
	}
}
