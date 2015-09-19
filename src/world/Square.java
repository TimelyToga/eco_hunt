package world;

import main.G;
import units.BaseUnit;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
/**
 * Created by Tim on 15/12/14.
 */
public class Square extends BaseUnit{

    private Image sprite;
    private Image black;
    private int squareSize;
    private int xCood;
    private int yCood;
    public boolean solid = false;

    public Square(int squareSize, int xCood, int yCood, World owner){
        super(xCood, yCood);
        this.sprite = owner.spriteSheet.getSprite(4 + G.rgen.nextInt(2), 0);
        this.black = owner.spriteSheet.getSprite(8,0);
        this.squareSize = squareSize;
        this.xCood = xCood;
        this.yCood = yCood;
        if(G.rgen.nextBoolean()){
            if(G.rgen.nextBoolean()){
                if(G.rgen.nextBoolean()){
                    if(G.rgen.nextBoolean()){
                        this.solid = true;
                        this.sprite = owner.spriteSheet.getSprite(8,0);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g, int xOffset, int yOffset){
        this.sprite.draw(xCood + xOffset, yCood + yOffset);
    }
    
    public void toGrass(){
    	this.sprite = G.world.spriteSheet.getSprite(4, 0);
    	this.solid = false;
    }

}
