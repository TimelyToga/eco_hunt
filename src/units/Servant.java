package units;

import org.newdawn.slick.Graphics;

/**
 * Created by Tim on 14/12/14.
 */
public class Servant extends BaseUnit{

    private int id;
    public String name;

    private boolean busy;

    public Servant(int id, int startXCood, int startYCood, String name){
        super(startXCood, startYCood);
        this.id = id;
        this.busy = false;
        this.name = name;
    }

    public void render(Graphics g, int xOffset, int yOffset){
//        g.draw(new Rectangle((float) getxCood(), (float) getyCood(), 10, 10));
        g.drawString(name, (float) getxCood(), (float) getyCood());
    }
}
