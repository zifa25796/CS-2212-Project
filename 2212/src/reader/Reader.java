package reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * <p>This class represents the tool to access the data for user account.</p>
 * <p>It reads a local JSON file</p>
 */
public class Reader {

    /**
     * JsonObject to store json data
     */
    private JsonObject jsonObject;

    /**
     * {@link Reader} class initializer
     */
    public Reader() {
        // Create the file object
        File file = new File("userData.json");
        try {
            // Try to import the data
            file.createNewFile();
            String content = FileUtils.readFileToString(file, "UTF-8");
            jsonObject = new JsonParser().parse(content).getAsJsonObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter method for json data
     * @return jsonObject's accounts data
     */
    public JsonArray getUserList() {
        return jsonObject.getAsJsonArray("accounts");
    }
}
