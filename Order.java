import java.util.ArrayList;

public class Order {
    public static void main (String[] args) {
        Drink newDrink = new Drink();
        newDrink.setSize();
        newDrink.setDrinkTemp();
        newDrink.setMilk();

        ArrayList<String> commonTopics = new ArrayList<String>();

        if (newDrink.getSize().equals("none")) {
            commonTopics.add("Size");
        }
        if (newDrink.getDrinkTemp().equals("none")) {
            commonTopics.add("Drink Type");
        }
        if (newDrink.getMilk().equals("none")) {
            commonTopics.add("Milk");
        }

        for (int i = 0; i < commonTopics.size(); i++) {
            System.out.println(commonTopics.get(i));
        }
    }   
}
