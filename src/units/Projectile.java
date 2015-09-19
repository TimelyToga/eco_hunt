package units;

import java.util.Timer;
import java.util.TimerTask;

import main.G;

import org.newdawn.slick.Graphics;

import world.Square;
import world.World;

/**
 * Created by Tim on 17/12/14.
 */
public class Projectile extends BaseUnit {

    public static final int FIRING_VELOCITY = 8;
    public static final int LIFE_TIME = 2000;

    public Timer disappearTimer;

    public Projectile(double xCood, double yCood, int directionInDegrees, World owner) {
        super(xCood, yCood);
        setVelocity(FIRING_VELOCITY);
        this.setAngle(directionInDegrees);
        this.owner = owner;
        this.sprite = this.owner.spriteSheet.getSprite(9, 0);

        this.disappearTimer = new Timer();
        this.disappearTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                destroyThisObject();
            }
        }, Projectile.LIFE_TIME);
    }

    @Override
    public void update(int xDelta, int yDelta){
        this.xCood += xDir * velocity;
        this.yCood += yDir * velocity;
                
        Square aboveSquare = World.getSquare((int) (xCood / owner.squareSize), 
        		(int) (yCood / owner.squareSize));
        if(aboveSquare.solid){
        	// Explode
        	aboveSquare.toGrass();
        }
    }
    
    @Override
    public void render(Graphics g, int xOffset, int yOffset){
    	update(0,0);
        sprite.draw((float) xCood, (float) yCood);
    }
    
    /**
     * To be called whenever the life timer times out
     */
    public void destroyThisObject(){
    	this.destroyed = true;
    }
}
