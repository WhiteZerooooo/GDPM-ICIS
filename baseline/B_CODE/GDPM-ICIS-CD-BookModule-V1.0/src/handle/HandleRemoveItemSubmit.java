package handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import view.MainView;
import view.RemoveItemView;

public class HandleRemoveItemSubmit implements ActionListener {
    public RemoveItemView view;

    public void setView(RemoveItemView view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        String inputItemSerialNumber = view.getInputItemSerialNumber().getText();

        if (inputItemSerialNumber.length() == 0) {
            view.getSubmitHint().setText("删除物品失败，编号为空"); 
            return;
        }

        MainView.checkDataFile();
        try {
            FileReader itemFile = new FileReader("data/items.json");
            JsonObject jsonObject = JsonParser.parseReader(itemFile).getAsJsonObject();
            itemFile.close();

            JsonArray atlasArray = jsonObject.getAsJsonArray("atlas");
            JsonArray discArray = jsonObject.getAsJsonArray("disc");
            JsonArray drawingArray = jsonObject.getAsJsonArray("drawing");
            
            JsonArray mainArray = jsonObject.getAsJsonArray("atlas");
            if (isSerialNumberExist(atlasArray, inputItemSerialNumber)) {
                mainArray = atlasArray;
            } else if (isSerialNumberExist(discArray, inputItemSerialNumber)) {
                mainArray = discArray;
            } else if (isSerialNumberExist(drawingArray, inputItemSerialNumber)) {
                mainArray = drawingArray;
            } else {
                view.getSubmitHint().setText("删除物品失败，编号为不存在"); 
                return;
            }
            
            Iterator<JsonElement> iterator = mainArray.iterator();
            while (iterator.hasNext()) {
                JsonObject item = iterator.next().getAsJsonObject();
                if (item.get("SerialNumber").getAsString().equals(inputItemSerialNumber)) {
                    iterator.remove();
                }
            }

            FileWriter fileWriter = new FileWriter("data/items.json");
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject, jsonWriter);
            jsonWriter.close();
            view.getSubmitHint().setText("删除物品成功");

        } catch (IOException ee) {
            ee.printStackTrace();
        }
        
    }
    
    private boolean isSerialNumberExist(JsonArray jsonArray, String serialNumber) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject item = jsonArray.get(i).getAsJsonObject();
            if (item.get("SerialNumber").getAsString().equals(serialNumber)) {
                return true;
            }
        }
        return false;
    }
}
