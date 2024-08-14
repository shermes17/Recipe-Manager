package softwaredesign;
import java.util.ArrayList;
//import com.alibaba.fastjson.JSON;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step>  steps;
    private String timeCreated;
    private String timeUpdated;
    private int servings = 1;
    private int cookingTime;
    private String category = "default";
    private int rating;

    private Checklist checklist;


    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
      ingredients = new ArrayList<>();
      steps = new ArrayList<>();
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setCookingTimes(int time){
        this.cookingTime = time;
    }
    public void setServings(int s){
        this.servings = s;
    }
    public void setCategory(String c){
        this.category = c;
    }

    public void setTimeCreated(String t){this.timeCreated = t;}
    public void setTimeUpdated(String t){this.timeUpdated = t;}

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public List<Step> getSteps(){
        return steps;
    }
    public String getTimeCreated() {
        return timeCreated;
    }
    public String getTimeUpdated() {
        return timeUpdated;
    }
    public int getServings() {
        return servings;
    }
    public int getCookingTime() {
        return cookingTime;
    }
    public String getCategory() {
        return category;
    }
    public int getRating() {
        return rating;
    }

    public Checklist getChecklist(){return checklist;}
    public void addStep(Step step){
        steps.add(step);
    }
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }
    public void setIngredients(List<Ingredient> i){this.ingredients = i;}
    public void setSteps(List<Step> s){this.steps = s;}
    public void setRating(int rating){this.rating = rating;}
}






