import java.util.ArrayList;

public class Order {
    public static void main (String[] args) {
        Drink newDrink = new Drink();
        newDrink.setSize();
        newDrink.setDrinkTemp();
        newDrink.setMilk();

        ArrayList<String> commonPhrases = new ArrayList<String>();

        if (newDrink.getSize().equals("none")) {
            commonPhrases.add("What size drink would you like?");
        }
        if (newDrink.getDrinkTemp().equals("none")) {
            commonPhrases.add("Do you want that hot or cold?");
            commonPhrases.add("Do you want that hot or iced?");
        }
        if (newDrink.getMilk().equals("none")) {
            commonPhrases.add("What kind of milk would you like?");
        }

        for (int i = 0; i < commonPhrases.size(); i++) {
            System.out.println(commonPhrases.get(i));
        }
    }   
}