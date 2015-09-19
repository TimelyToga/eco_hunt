package units;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import world.World;

/**
 * Created by Tim on 14/12/14.
 */
public class BaseUnit implements Renderable{

    protected int believedWorldWidth;
    protected int believedWorldHeight;

    protected double xCood;
    protected double yCood;

    protected double xDir;
    protected double yDir;
    protected double angle;
    protected double velocity;
    protected double frictionCoef = .9925;
    
    protected Image sprite;

    protected World owner;
    
    public boolean destroyed = false;

    public BaseUnit(double xCood, double yCood){
        this.xCood = xCood;
        this.yCood = yCood;
        this.velocity = 3;
        this.setAngle(45);
    }

    public BaseUnit(int xCood, int yCood, World owner){
        this.xCood = xCood;
        this.yCood = yCood;
        this.velocity = 3;
        this.setAngle(45);
        this.owner = owner;
        this.owner.addRenderable(this);
    }

    @Override
    public void update(int xDelta, int yDelta){
        if(xCood > 0 && xCood < believedWorldWidth){
            // Step forward
            xCood += xDir * velocity;
        } else if(xCood <= 0){
            xCood = 0;
            xDir *= -1;
            xCood += xDir * velocity;
        } else if(xCood >= believedWorldWidth){
            xCood = believedWorldWidth;
            xDir *= -1;
            xCood += xDir * velocity;
        }

        // Y Handling
        if(yCood > 0 && yCood < believedWorldHeight){
            // Step forward
            yCood += yDir * velocity;
        } else if(yCood <= 0){
            yCood = 0;
            yDir *= -1;
            yCood += yDir * velocity;
        } else if(yCood >= believedWorldHeight){
            yCood = believedWorldHeight;
            yDir *= -1;
            yCood += yDir * velocity;
        }

        velocity *= frictionCoef;
    }

    public void render(Graphics g, int xOffset, int yOffset){
        System.out.println("No graphics implemented...");
    }

    public int getBelievedWorldWidth() {
        return believedWorldWidth;
    }

    public int getBelievedWorldHeight() {
        return believedWorldHeight;
    }

    public double getxCood() {
        return xCood;
    }

    public double getyCood() {
        return yCood;
    }

    public double getxDir() {
        return xDir;
    }

    public double getyDir() {
        return yDir;
    }

    public double getAngle() {
        return angle;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setBelievedWorldWidth(int believedWorldWidth) {
        this.believedWorldWidth = believedWorldWidth;
    }

    public void setBelievedWorldHeight(int believedWorldHeight) {
        this.believedWorldHeight = believedWorldHeight;
    }

    public void setxCood(double xCood) {
        this.xCood = xCood;
    }

    public void setyCood(double yCood) {
        this.yCood = yCood;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.xDir = Math.cos(Math.toRadians(angle));
        this.yDir = Math.sin(Math.toRadians(angle));
    }
}
