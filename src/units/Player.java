package units;

import main.G;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import world.Square;
import world.World;



/**
 * Created by Tim on 17/12/14.
 */
public class Player extends BaseUnit {

    private int direction;
    private Image sprite;
    private Animation animation;
    public double  maxSpeed = 0.5;
    public double speed = 0;
    public double accel = 0.05;
    public double fireRate = 3;
    public long timeSinceLastFire;

    public Player(int xCood, int yCood, World owner) {
        super(xCood, yCood, owner);
        this.direction = 1;
        this.sprite = this.owner.spriteSheet.getSprite(0,0);
        this.owner.setPlayer(this);
        Image[] animationFrames = new Image[] { owner.spriteSheet.getSprite(0,0), owner.spriteSheet.getSprite(1,0), owner.spriteSheet.getSprite(2,0)};
        this.animation = new Animation(animationFrames, 250);
//        sprite = new Image();
    }

    public void fire(){
    	if((System.currentTimeMillis() - timeSinceLastFire) > (1000 / fireRate)){
            Projectile p = new Projectile(xCood + 16, yCood + 16, 0, this.owner);
            owner.addRenderable(p);
            timeSinceLastFire = System.currentTimeMillis();
    	}
    }

    @Override
    public void render(Graphics g, int xOffset, int yOffset){
        animation.draw((float) xCood, (float) yCood);
    }

    @Override
    public void update(int xDelta, int yDelta){
        this.xCood += xDelta;
        this.yCood += yDelta;
    }
    
    public int[] handlePlayerMove(int xDelta, int yDelta, int timeDelta){
    	int[] output = new int[2];
    	int squareSize = this.owner.squareSize;
    	int xOffset = this.owner.xOffset;
    	int yOffset = this.owner.yOffset;
    	
    	// Find Player Speed    	
    	updateSpeed(xDelta, yDelta, timeDelta);
    	
        xDelta = (int) (xDelta * this.speed * timeDelta);
        yDelta = (int) (yDelta * this.speed * timeDelta);
    	
        // SHOULD MOVE LOGIC
        double newX = this.getxCood() + xDelta - xOffset;
        double newY = this.getyCood() + yDelta - yOffset;
        double newXPlus = newX + 64;
        double newYPlus = newY + 64;

        // System.out.println(newY + ", " + newYPlus + ", " + (int) newY / squareSize);

        // Find intersecting Corner Squares
        Square ul = World.getSquare((int) (newX / squareSize), (int) (newY / squareSize));
        Square ur = World.getSquare((int) (newXPlus / squareSize), (int) (newY / squareSize));
        Square ll = World.getSquare((int) (newX / squareSize), (int) (newYPlus / squareSize));
        Square lr = World.getSquare((int) (newXPlus / squareSize), (int) (newYPlus / squareSize));
        
        if(xDelta < 0){
        	// Figure out if you can move right
        	if(lr.solid || ur.solid){
        		xDelta = 0;
        	}
        } else {
        	// Figure out if you can move left
        	if(ll.solid || ul.solid){
        		xDelta = 0;
        	}
        }
        if(yDelta < 0){
        	// Figure out if you can move down
        	if(lr.solid || ll.solid) yDelta = 0;
        } else {
        	// Figure out if you can move up
        	if(ur.solid || ul.solid) yDelta = 0;
        }
    	
        output[0] = xDelta;
        output[1] = yDelta;
    	return output;
    }

	private void updateSpeed(int xDelta, int yDelta, int timeDelta) {
		if(xDelta == 0 && yDelta == 0){
			if(speed < 0) speed -= 2*this.accel*timeDelta;
			else speed = 0;
		} else if(speed < maxSpeed) speed += this.accel*timeDelta;
	}


}
