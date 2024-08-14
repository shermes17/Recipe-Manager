package softwaredesign;

import com.alibaba.fastjson2.JSON;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class JSONparser {

    public JSONparser() {};

    public void objectToFile(Recipe obj) {
        // convert the Recipe object to JSON string format
        String jsonString = JSON.toJSONString(obj);

        // create the Recipe category folder if it doesn't exist already
        String catDir = System.getProperty("user.dir") + "\\Recipes\\" +
                obj.getCategory();
        File category = new File(catDir);
        if(!category.exists()) {
            category.mkdir();
        }

        // create the JSON file
        String filename = (catDir + "\\" + obj.getName() + ".json");
        try {
            FileWriter w = new FileWriter(filename);
            w.write(jsonString);
            w.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Recipe fileToObject(String name) {
        String filename = findFile(name + ".json");

        try {
            FileReader r = new FileReader(filename);
            int count = 0;
            while (r.read() != -1) {
                count++;
            }
            r.close();

            FileReader r2 = new FileReader(filename);
            char[] arr = new char[count];
            r2.read(arr);
            String fromFile = String.valueOf(arr);

            Recipe obj = JSON.parseObject(fromFile,Recipe.class);
            r2.close();

            return obj;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String findFile(String toFind) {

        String fileNameToFind = toFind;
        String directory = System.getProperty("user.dir") + "\\Recipes";
        File rootDirectory = new File(directory);

        Optional<Path> foundFile;

        try (Stream<Path> walkStream = Files.walk(rootDirectory.toPath())) {
            foundFile = walkStream.filter(p -> p.toFile().isFile())
                    .filter(p -> p.toString().endsWith(fileNameToFind))
                    .findFirst();

            if(foundFile.isPresent()) {
                return foundFile.get().toString();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


}
