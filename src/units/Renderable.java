package units;


import org.newdawn.slick.Graphics;

/**
 * Created by Tim on 17/12/14.
 */
public interface Renderable {

    public boolean shouldRender = true;
    public boolean shouldUpdate = true;

    public int xCood = 0;
    public int yCood = 0;

    public void update(int xDelta, int yDelta);

    public void render(Graphics g, int xOffset, int yOffset);

}
