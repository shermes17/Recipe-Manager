package softwaredesign;
import java.util.List;

public class Checklist {
    private List<Ingredient> ingredients;
    private Boolean allchecked;

    public Checklist(Recipe r){
        this.ingredients = r.getIngredients();
    }

    public Boolean checkIngredient(){
        UserInterface UI = new UserInterface();
        for (Ingredient i : ingredients){
            String input = UI.getInput("(Y or N)Do you have the ingredient: " + i.getName());
            input = input.toUpperCase();
            if(input == "N"){
                allchecked = false;
                return false;
            }
        }
        allchecked = true;
        return true;
    }

}
