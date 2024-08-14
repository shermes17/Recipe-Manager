package softwaredesign;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.util.List;


public class Event {
    private String name;
    private UserInterface UI;
    private RecipeDatabase database;

    public Event(String name, UserInterface UI, RecipeDatabase database){
        this.name = name;
        this.UI = UI;
        this.database = database;
    }

    public void createRecipe() {

        UI.print("Now beginning the recipe creation process!\n");
        String n = UI.getInput("Please input the name:");
        while(database.alreadyExists(n)) {
            UI.print("A recipe with the name " + n + " already exists!");
            n = UI.getInput("Please input a new name:");
        }

        Recipe recipe = new Recipe(0, n);
        Date date = new Date();
        recipe.setTimeCreated(String.valueOf(date));

        UI.print("Please add the first step in the recipe:");
        String input = "Y";
        while (input.equals("Y")) {
            recipe.addStep(createStep(recipe));
            input = UI.getInput("Do you want to add another step? Answer Y or N");
            input = input.toUpperCase();
            while (!(input.equals("Y") || input.equals("N"))) {
                input = UI.getInput("Invalid input, try again!").toUpperCase();
            }
        }

        input = UI.getInput("Do you want to add a category? Answer Y or N");
        input = input.toUpperCase();
        while (!(input.equals("Y") || input.equals("N"))) {
            input = UI.getInput("Invalid input, try again!").toUpperCase();
        }
        if (input.equals("Y")) {
            recipe.setCategory(UI.getInput("Please enter the category"));
        }

        recipe.setCookingTimes(Integer.parseInt(UI.getInput("Please enter the cooking time (minutes)")));
        recipe.setServings(Integer.parseInt(UI.getInput("Please enter the number of servings")));

        saveRecipe(recipe);

    }

    public void updateRecipe() {
        UI.print("Now beginning the recipe update process!\n");
        UI.printDatabase(database.fileToArray());
        if(database.isEmpty()) {return;}

        String recipeName = UI.getInput("Which recipe do you want to update? (options above)");
        if(recipeName.equals("exit")) {return;}

        while(!database.alreadyExists(recipeName)) {
            UI.print("A recipe with the name " + recipeName + " does not exist!");
            recipeName = UI.getInput("Please input an existing recipe name:");
            if(recipeName.equals("exit")) {return;}
        }

        while(true) {
            Recipe recipe = loadRecipe(recipeName);
            File f = new File(System.getProperty("user.dir") + "\\Recipes\\" +
                    recipe.getCategory() + "\\" + recipeName + ".json");

            UI.print("\nWhat would you like to change?");
            UI.print("1. Recipe name");
            UI.print("2. Update an existing step");
            UI.print("3. Add a new step");
            UI.print("4. Change category");
            UI.print("5. Change cooking time");
            UI.print("6. Change servings");
            UI.print("7. Exit update menu");
            String input = UI.getInput("");

            while(!(input.equals("1") || input.equals("2") || input.equals("3") ||
                    input.equals("4") || input.equals("5") || input.equals("6") ||
                    input.equals("7"))) {
                input = UI.getInput("Invalid input, please try again!");
            }

            switch (input) {
                case "1":
                    String name = UI.getInput("Please enter the new name");
                    database.removeRecipe(recipeName);
                    recipe.setName(name);
                    break;
                case "2":
                    for (Step s : recipe.getSteps()) {
                        UI.print("Do you want to update this step (Y/N):");
                        String str = UI.getInput(s.getInstructions());
                        if (str.toUpperCase().equals("Y")) {
                            List<Ingredient> oldIngredients = recipe.getIngredients();
                            for (Ingredient i : s.getIngredient()) {
                                oldIngredients.remove(i);
                            }
                            s = createStep(recipe);
                            break;
                        }
                    }
                    break;
                case "3":
                    String str = "N";
                    int counter = 0;
                    for (Step s : recipe.getSteps()) {
                        String str2 = UI.getInput("(Y/N) Do you want to add a step after step " + (counter + 1) + ":\n" + s.getInstructions()).toUpperCase();
                        if (str2.equals("Y")) {
                            Step newStep = createStep(recipe);
                            List<Step> newSteps = new ArrayList<>();
                            List<Step> oldSteps = recipe.getSteps();
                            for (int i = 0; i < oldSteps.size(); i++) {
                                newSteps.add(oldSteps.get(i));
                                if (i == counter)
                                    newSteps.add(newStep);
                            }

                            break;
                        }
                        counter++;
                    }

                    break;
                case "4":
                    String category = UI.getInput("Please enter the new category");
                    recipe.setCategory(category);
                    break;
                case "5":
                    String cookingTime = UI.getInput("Please enter the new cooking time");
                    recipe.setCookingTimes(Integer.parseInt(cookingTime));
                    break;
                case "6":
                    String servings = UI.getInput("Please enter the new servings");
                    recipe.setServings(Integer.parseInt(servings));
                    break;
                case "7":
                    UI.print("Finished with updates!");
                    return;
                default:
                    break;
            }
            Date date = new Date();
            recipe.setTimeUpdated(String.valueOf(date));

            if(f.delete()) {}
            else {UI.print("\nError, outdated recipe file not deleted!\n");}

            recipeName = recipe.getName();
            saveRecipe(recipe);
            UI.print("Your recipe has been updated!\n");
        }
    }

    public void deleteRecipe() {
        UI.print("Now beginning the recipe deletion process!\n");
        UI.printDatabase(database.fileToArray());
        if(database.isEmpty()) {return;}
        String recipeName = UI.getInput("Which recipe do you want to delete? (options above)");
        if(recipeName.equals("exit")) {return;}

        while(!database.alreadyExists(recipeName)) {
            UI.print("A recipe with the name " + recipeName + " does not exist!");
            recipeName = UI.getInput("Please input an existing recipe name:");
            if(recipeName.equals("exit")) {return;}
        }

        JSONparser p = new JSONparser();
        String path = p.findFile(recipeName + ".json");

        File recipeFile = new File(path);
        if (recipeFile.delete()) {
            database.removeRecipe(recipeName);
            UI.print("Deleted the recipe file: " + recipeFile.getName());
        } else {
            UI.print("Failed to delete the recipe file!");
        }
    }

    public Recipe loadRecipe(String recipeName) {
        JSONparser parser = new JSONparser();
        Recipe recipe = parser.fileToObject(recipeName);
        UI.print("Successfully converted JSON file " + recipe.getName() +
                " into an object!");
        return recipe;
    }

    public void saveRecipe(Recipe recipe) {
        JSONparser parser = new JSONparser();
        parser.objectToFile(recipe);
        UI.print("Successfully converted object " + recipe.getName() +
                " into a JSON file!");
        database.addRecipe(recipe.getName());
    }

    public void executeRecipe() {

        UI.print("\nNow beginning the recipe execution process!\n");
        UI.printDatabase(database.fileToArray());
        if(database.isEmpty()) {return;}
        String recipeName = UI.getInput("Which recipe do you want to execute? (options above)");
        if(recipeName.equals("exit")) {return;}

        while(!database.alreadyExists(recipeName)) {
            UI.print("A recipe with the name " + recipeName + " does not exist!");
            recipeName = UI.getInput("Please input an existing recipe name:");
            if(recipeName.equals("exit")) {return;}
        }

        Recipe recipe = loadRecipe(recipeName);
        Checklist checklist = new Checklist(recipe);
        if (checklist.checkIngredient() == false) {
            UI.print("You do not have all of the ingredients needed!");
            return;
        }

        String input = "not done";
        int stepCounter = 0;
        for (Step s : recipe.getSteps()) {
            stepCounter++;
            while (!input.equals("done")) {
                if (recipe.getIngredients().size() != 0) {
                    UI.print("Ingredients for step " + stepCounter + ":\n");
                    for (Ingredient i : recipe.getIngredients()) {
                        UI.print(i.getQuantity() + "" + i.getUnit() + " of " + i.getName());
                    }
                }
                UI.print("Step "+ stepCounter + ":\n"+s.getInstructions());
                input = UI.getInput( "\nInput \"done\" to move to the next step");
            }
            input = UI.getInput("Do you want to add a note? Answer Y or N").toUpperCase();
            while (!(input.equals("Y") || input.equals("N"))) {
                input = UI.getInput("Invalid input, try again!").toUpperCase();
            }
            if (input.equals("Y")) {
                s.setNote(UI.getInput("Enter the note:"));
            }
        }
        input = UI.getInput("You have finished with the recipe!\nPlease enter your rating. (1 to 5)");
        recipe.setRating(Integer.parseInt(input));

        database.removeRecipe(recipeName);
        database.addRecipe(recipeName + " - " + recipe.getRating() + " stars");
    }

    public Step createStep(Recipe recipe) {

        Step step = new Step();
        step.setInstructions(UI.getInput("Please input instructions for the step"));

        String str = UI.getInput("Do you want to add an ingredient to this step? Answer Y or N").toUpperCase();
        while (!(str.equals("Y") || str.equals("N"))) {
            str = UI.getInput("Invalid input, try again!").toUpperCase();
        }

        while (str.equals("Y")) {
            String ingredientName = UI.getInput("Please input the ingredient name.");

            String quant = UI.getInput("Please input the ingredient quantity.");
            while (true) {
                try {
                    Double.parseDouble(quant);
                    break;
                }
                catch(NumberFormatException e) {
                    quant = UI.getInput("Invalid input, try again!");
                }
            }
            double ingredientQuantity = Double.parseDouble(quant);

            String u = UI.getInput("Please enter the unit: g, mL, oz, cups, tbsp, tsp, or unit");
            while(!(u.equals("g") || u.equals("mL")||u.equals("oz")||u.equals("cups")||u.equals("tbsp")|| u.equals("tsp") || u.equals("unit"))) {
               str = UI.getInput("Invalid input, try again!");
            }
            Unit unit = strToUnit(u);

            Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, unit);
            if(!recipe.getIngredients().contains(ingredient))
                 recipe.addIngredient(ingredient);
            step.addIngredients(ingredient);
            str = UI.getInput("Do you want to add another ingredient to this step? Answer Y or N");
            str = str.toUpperCase();
            while (!(str.equals("Y") || str.equals("N"))) {
                str = UI.getInput("Invalid input, try again!").toUpperCase();
            }
        }

        return step;
    }

    public Unit strToUnit(String s){
        if(s.equals("g"))
            return Unit.g;
        if(s.equals("mL"))
            return Unit.mL;
        if(s.equals("oz"))
            return Unit.oz;
        if(s.equals("cups"))
            return Unit.cups;
        if(s.equals("tbsp"))
            return Unit.tbsp;
        if(s.equals("unit"))
            return Unit.unit;
        else
            return Unit.tsp;


    }


}