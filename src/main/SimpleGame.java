package main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

import units.Player;
import world.World;

import java.util.Random;

public class SimpleGame extends BasicGame {


    public SimpleGame() {
        super("main.SimpleGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
//        main.G.servantFactory = ServantFactory.getInstance();
//        String phrase = "And then my heart with pleasure fills, And dances with the daffodils.";
//        String[] array = phrase.split(" ");
//        double angle = 90.0 / array.length;
//        int curAngle = 0;
//        for(String s: array){
//            main.G.servantFactory.createServant(0, 0, curAngle, main.G.screenX, main.G.screenY, s);
//            curAngle += angle;
//            System.out.println(s + ": " + curAngle);
//        }

        G.rgen = new Random();
        G.world = new World(64, 64, 64);
        G.player = new Player(300, 300, G.world);

    }

    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
//        for(Integer key: main.G.servantFactory.servantList.keySet()){
//            main.G.servantFactory.servantList.get(key).update();
//        }

        if(Keyboard.isKeyDown(200) || Keyboard.isKeyDown(203) || Keyboard.isKeyDown(205) || Keyboard.isKeyDown(208)){
            int tmpXDelta = 0, tmpYDelta = 0;
            if(Keyboard.isKeyDown(208)){
                // up
                tmpYDelta += -1;
            }

            if(Keyboard.isKeyDown(205)){
                // left
                tmpXDelta += -1;
            }

            if(Keyboard.isKeyDown(203)){
                // right
                tmpXDelta += 1;
            }

            if(Keyboard.isKeyDown(200)){
                // down
                tmpYDelta += 1;
            }
            

            G.world.update(tmpXDelta, tmpYDelta, delta);
        }
        
        // Player Fire
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
        	G.player.fire();
        }

        // Game exit
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            System.exit(0);
        }
    }

    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
//        for(Integer key: main.G.servantFactory.servantList.keySet()){
//            main.G.servantFactory.servantList.get(key).render(g);
//        }
        G.world.render(g);

    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new SimpleGame());
            app.setDisplayMode(640, 480, false);
            G.screenX = app.getWidth();
            G.screenY = app.getHeight();
            System.out.println("Screen width, height: " + G.screenX + ", " + G.screenY);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}