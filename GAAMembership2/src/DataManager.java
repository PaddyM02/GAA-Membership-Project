
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOExceptiot;

public class DataManager {

    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new Object())
        .create();

    private final String filePath = "data.json";  // path to your JSON file

    public void saveDatMyObjectct obj) {
        String json = gson.toJson(obj);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    publiMyObjectct loadData() {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, MyObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
