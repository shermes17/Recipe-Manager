package softwaredesign;
import java.util.ArrayList;
import java.util.List;

public class Step {
private List<Ingredient> ingredients;
private String instructions;
private String note;

public Step(){
    ingredients = new ArrayList<>();
}

public List<Ingredient> getIngredient(){
    return ingredients;
}
public String getInstructions(){
    return instructions;
}
public void setIngredients(List<Ingredient> ingredients){this.ingredients= ingredients;}
public void setInstructions(String instruction){this.instructions = instruction;}
public void addIngredients(Ingredient i){
    ingredients.add(i);
}
public void setNote(String note){this.note = note;}

}
