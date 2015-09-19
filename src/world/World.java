package world;

import main.G;
import units.BaseUnit;
import units.Player;
import units.Renderable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tim on 15/12/14.
 */
public class World {

    public int squareSize;
    public SpriteSheet spriteSheet;
  
    List<Square> bounds;

    // Lengths in squares
    public static int width;
    public static int height;

    public int xOffset;
    public int yOffset;

    int screenXStart;
    int screenYStart;
    int screenXEnd;
    int screenYEnd;

    Square ul;
    
    int cyclesSinceLastCleanup = 0;

    public static Square[] squares;
    private Set<Renderable> renderables;
    private Player player;

    public World(int squareSize, int width, int height){
        this.squareSize = squareSize;
        this.width = width;
        this.height = height;
        
        this.bounds = new ArrayList<Square>();

        this.renderables = new HashSet<Renderable>();
        try {
            Image sheetImage = new Image("/res/img/SpriteSheet.png");
            spriteSheet = new SpriteSheet(sheetImage, squareSize, squareSize);
            spriteSheet.startUse();
        } catch (Exception e){
            System.out.println("Didn't find spritesheet");
            System.exit(0);
        }

        // Create the array of squares
        squares = new Square[width * height];
        int tempX = 0, tempY = 0;
        for(int a = 0; a < squares.length; a++){
            squares[a] = new Square(squareSize, tempX, tempY, this);
            tempX += squareSize;
            if(tempX >= (width * squareSize)){
                tempX = 0;
                tempY += squareSize;
                if(tempY >= (height * squareSize)){
                    tempY = 0;
                }
            }
        }
    }

    public static Square getSquare(int xCood, int yCood){
        return squares[yCood*width + xCood];
    }

    public void render(Graphics g){
        for(Square s: squares){
            if(shouldRender(s)) {
                s.render(g, xOffset, yOffset);
            }
        }

        for(Renderable r: renderables){
            if(shouldRender(r)){
            	r.update(0, 0);
                r.render(g, xOffset, yOffset);
            }
        }

        player.render(g, xOffset, yOffset);
        if(ul != null) g.drawString("ul: " + ul.getxCood() + ", " + ul.getyCood(), G.screenX - 150, G.screenY - 25);
    }

    public void update(int xDelta, int yDelta, int timeDelta){
    	// Handle Player Movement
        int[] deltas = this.player.handlePlayerMove(xDelta, yDelta, timeDelta);
    	
        this.xOffset += deltas[0];
        this.yOffset += deltas[1];

        // Drawing bounds
        screenXStart = xOffset + squareSize;
        screenYStart = yOffset + squareSize;
        screenXEnd = screenXStart - G.screenX - squareSize;
        screenYEnd = screenYStart - G.screenY - squareSize;

        for(Renderable r: renderables){
            r.update(0, 0);
        }

        if(cyclesSinceLastCleanup > 50){
        	cleanupRenderables();
        }
        
        cyclesSinceLastCleanup++;
    }

    public void addRenderable(Renderable r){
    	System.out.println(renderables.size());
        if(!renderables.contains(r)){
            renderables.add(r);
        }
    }

    public void removeRenderable(Renderable r){
        renderables.remove(r);
    }
    
    public void cleanupRenderables(){
    	List<Renderable> shouldRemove = new ArrayList<Renderable>();
    	for(Renderable r : renderables){
    		if(r instanceof BaseUnit && ((BaseUnit) r).destroyed) shouldRemove.add(r); 
    	}
    	
    	for(Renderable r : shouldRemove){
    		removeRenderable(r);
    	}
    	
    	cyclesSinceLastCleanup = 0;
    }

    public void setPlayer(Player p){
        this.player = p;
    }

    public boolean shouldRender(Renderable r){
        int squareX = r.xCood + xOffset + squareSize;
        int squareY = r.yCood + yOffset + squareSize;
        int x = r.xCood + xOffset;
        int y = r.yCood + yOffset;

        if(x > screenXStart || squareX < screenXEnd || y > screenYStart || squareY < screenYEnd){
            return false;
        }

        return true;
    }
}
