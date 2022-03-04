package reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Reader {

    private JsonObject jsonObject;

    public Reader() {
        File file = new File("userData.json");
        try {
            file.createNewFile();
            String content = FileUtils.readFileToString(file, "UTF-8");
            jsonObject = new JsonParser().parse(content).getAsJsonObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public JsonArray getUserList() {
        return jsonObject.getAsJsonArray("accounts");
    }
}
