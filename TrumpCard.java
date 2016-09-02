/**
 * Created by tony on 6/08/2016.
 */
public class TrumpCard extends Card{ //trump card information

    private String category;

    public TrumpCard(String name, String category){ //trump card constructor

        super(name);
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    @Override
    public String toString() { //used to print trump card values
        return "Name: " + this.getName() + ",  " +
                "Category: " + this.getCategory()+ "\n" ;
    }
}
