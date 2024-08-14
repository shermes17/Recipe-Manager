package softwaredesign;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class RecipeDatabase {
    private final String filename = (System.getProperty("user.dir") +
            "\\Recipes\\database.txt");
    private final String tempname = (System.getProperty("user.dir") +
            "\\Recipes\\temp.txt");
    public RecipeDatabase(){}
    public String getFilename(){return filename;}

    public boolean alreadyExists(String name) {
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.equals(name)) {
                    file.close();
                    return true;
                }
            }
            file.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addRecipe(String name){
        if(!this.alreadyExists(name)) {
            try (FileWriter fw = new FileWriter(filename, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(name);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeRecipe(String name) {
        if(this.alreadyExists(name)) {
            try {
                Scanner file = new Scanner(new File(filename));
                PrintWriter writer = new PrintWriter(tempname);

                while (file.hasNext()) {
                    String line = file.nextLine();
                    if (!line.equals(name)) {
                        writer.write(line);
                        writer.write("\n");
                    }
                }

                file.close();
                writer.close();

                File file1 = new File(filename);
                File file2 = new File(tempname);
                file1.delete();
                file2.renameTo(file1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] fileToArray() {
        if(isEmpty()) {return null;}

        String databaseArray[];
        try {
            Scanner counter = new Scanner(new File(filename));
            int count = 0;
            while (counter.hasNext()) {
                String line = counter.nextLine();
                count ++;
            }
            counter.close();

            databaseArray = new String[count];
            Scanner file = new Scanner(new File(filename));
            int index = 0;
            while (file.hasNext()) {
                String line = file.nextLine();
                databaseArray[index] = line;
                index++;
            }
            file.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }

        return databaseArray;
    }

    public boolean isEmpty() {
        try {
            Scanner file = new Scanner(new File(filename));
            if(file.hasNext()) {
                file.close();
                return false;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}