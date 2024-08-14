package softwaredesign;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    //private String input;
    private boolean running;
    private Scanner scan = new Scanner(System.in);

    public UserInterface(){
        //this.input = "";
        this.running = true;
    }
    public static void main(String [] args) {
        UserInterface UI = new UserInterface();

        UI.startupChecks();

        while (UI.running){
            UI.mainMenu();
       }
    }

    public void mainMenu() {
        System.out.println("\nWELCOME TO LetsCook!");
        System.out.println("\nHere are your options: ");
        System.out.println("1. create a recipe");
        System.out.println("2. update a recipe");
        System.out.println("3. execute a recipe");
        System.out.println("4. delete a recipe");
        System.out.println("5. exit");
        String userInput = getInput("\"Enter the action you would like to perform: \"");

        while (!(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") ||
                userInput.equals("4") || userInput.equals("5"))) {
            userInput = getInput("Please enter a valid option (1,2,3,4,5): ");
        }

        RecipeDatabase database = new RecipeDatabase();

        switch(userInput) {
            case "1":
                Event create = new Event("create", this, database);
                create.createRecipe();
                break;
            case "2":
                Event update = new Event("update", this, database);
                update.updateRecipe();
                break;
            case "3":
                Event execute = new Event("execute", this, database);
                execute.executeRecipe();
                break;
            case "4":
                Event delete = new Event("delete", this, database);
                delete.deleteRecipe();
                break;
            case "5":
                System.out.println("Now exiting... Goodbye!");
                running = false;
                break;
            default:
                break;
        }
    }

    public String getInput(String output){
        System.out.println("[->] " + output);
        return scan.nextLine();
    }

    public void print(String output){
        System.out.println(output);
    }


    public void printDatabase(String[] arr) {
        if(arr == null || arr.length == 0) {
            print("Database is empty! Create some recipes!\n" +
                    "Returning to main menu.\n");
            return;
        }

        for(int i = 0; i < arr.length; i++) {
            print("-" + arr[i]);
        }
        print("-TYPE 'exit' TO EXIT");
    }

    public void startupChecks() {

        // check if the Recipes folder exists; if not, create it
        String currentDir = System.getProperty("user.dir");
        String recipesDir = currentDir + "\\Recipes";
        File RecipeFolder = new File(recipesDir);
        if(!RecipeFolder.exists()) {
            this.print("Recipes folder does not yet exist...");
            RecipeFolder.mkdir();
            this.print("Recipes folder created!\n");
        }

        // check if the database exists; if not, create it
        File f = new File(currentDir + "\\Recipes\\database.txt");
        if(!f.exists()) {
            try {
                this.print("Recipe database does not yet exist...");
                f.createNewFile();
                this.print("Recipe database created: \\Recipes\\database.txt\n");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }

        // check if the default category folder exists; if not, create it
        File defaultCat = new File(recipesDir + "\\default");
        if(!defaultCat.exists()) {
            this.print("Default recipe category folder does not yet exist...");
            defaultCat.mkdir();
            this.print("Default category folder created: \\Recipes\\default\\\n");
        }
    }

}