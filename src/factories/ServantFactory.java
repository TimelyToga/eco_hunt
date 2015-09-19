package factories;

import units.Servant;

import java.util.HashMap;

/**
 * Created by Tim on 14/12/14.
 */
public class ServantFactory {

    private static ServantFactory instance = null;

    public HashMap<Integer, Servant> servantList = new HashMap<Integer, Servant>();
    private int numServants = -1;

    protected ServantFactory(){}; // defeats instantiation

    public static ServantFactory getInstance(){
        if(instance == null){
            instance = new ServantFactory();
        }

        return instance;
    }

    public Servant createServant(int startXCood, int startYCood, String name){
        numServants += 1;
        Servant tmpServant = new Servant(numServants, startXCood, startYCood, name);
        servantList.put(numServants, tmpServant);
        return tmpServant;
    }

    public Servant createServant(int startXCood, int startYCood, int angle, int xSize, int ySize, String name){
        numServants += 1;
        Servant tmpServant = new Servant(numServants, startXCood, startYCood, name);
        tmpServant.setAngle(angle);
        tmpServant.setBelievedWorldWidth(xSize);
        tmpServant.setBelievedWorldHeight(ySize);

        servantList.put(numServants, tmpServant);
        return tmpServant;
    }

    public Servant getServant(int servantId){
        if(servantList.containsKey(servantId)){
            return servantList.get(servantId);
        } else {
            return null;
        }
    }

}
