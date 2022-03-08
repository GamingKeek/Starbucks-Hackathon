public class Drink {
    String translatedSentence = "I want a tall hot coffee with milk";
    
    private final String[] SIZE_OPTIONS = {"SMALL", "MEDIUM", "LARGE","TALL", "GRANDE", "VENTI", "TRENTA"};
    String sizeSelected;
    
    private final String[] DRINK_TEMP = {"HOT", "COLD", "ICED"};
    String temp;
    
    private final String[] MILK_OPTIONS =  {"DAIRY", "NON-DAIRY", "2%", "NONFAT", "WHOLE", "HALF AND HALF", "HEAVY CREAM", 
    "OAT", "SOY", "COCONUT", "ALMOND", "SWEET CREAM"};
    String milkSelected;
    
    public Drink() {
        String size = sizeSelected;
        String drinkTemp = temp;
        String milk = milkSelected;
    }

    private String[] sentenceSplit(String sentence) {
        String[] words = sentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return words;
    }
    
    String[] keywords = sentenceSplit(translatedSentence);

    public void setSize() {
        for (int i = 0; i < keywords.length; i++) {
            for (int j = 0; j < SIZE_OPTIONS.length; j++) {
                if (SIZE_OPTIONS[j].equals(keywords[i].toUpperCase())) {
                    sizeSelected = SIZE_OPTIONS[j];
                }
            }
        }
    }
    
    public String getSize() {
        if (sizeSelected != null) {
            return sizeSelected;
        }
        else {
            sizeSelected = "none";
            return sizeSelected;
        }
    }

    public void setDrinkTemp() {
        for (int i = 0; i < keywords.length; i++) {
            for (int j = 0; j < DRINK_TEMP.length; j++) {
                if (DRINK_TEMP[j].equals(keywords[i].toUpperCase())) {
                    temp = DRINK_TEMP[j];
                }
            }
        }
    }

    public String getDrinkTemp() {
        if (temp != null) {
            return temp;
        }
        else {
            temp = "none";
            return temp;
        } 
    }


    public void setMilk() {
        for (int i = 0; i < keywords.length; i++) {
            for (int j = 0; j < MILK_OPTIONS.length; j++) {
                if (MILK_OPTIONS[j].equals(keywords[i].toUpperCase())) {
                    milkSelected = MILK_OPTIONS[j];
                }
            }
        }
    }

    public String getMilk() {
        if (milkSelected != null) {
            return milkSelected;
        }
        else {
            milkSelected = "none";
            return milkSelected;
        }
    }
}
