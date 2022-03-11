package speech;

import java.util.ArrayList;

public class Order {
    private Drink newDrink;
    private ArrayList<String> commonTopics = new ArrayList<String>();
    
    public Order() {
        newDrink = new Drink();
    }

    public void setDrink() {
        newDrink.setSize();
        newDrink.setDrinkTemp();
        newDrink.setMilk();
    }

    public ArrayList<String> getCommonTopics() {
        if (newDrink.getSize().equals("none")) {
            commonTopics.add("Size");
        }
        
        if (newDrink.getDrinkTemp().equals("none")) {
            commonTopics.add("speech.Drink Type");
        }
        
        if (newDrink.getMilk().equals("none")) {
            commonTopics.add("Milk");
        }
        return commonTopics;
    }
}
