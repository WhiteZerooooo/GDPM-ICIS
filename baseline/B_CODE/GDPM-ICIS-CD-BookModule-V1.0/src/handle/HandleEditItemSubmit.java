package handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import ItemStorage.Atlas;
import ItemStorage.Disc;
import ItemStorage.Drawing;
import view.EditItemView;
import view.MainView;

public class HandleEditItemSubmit implements ActionListener {
    public EditItemView view;

    public void setView(EditItemView view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        int selectedOptionNum = view.getComboBox().getSelectedIndex();
        if (selectedOptionNum < 0) {
            view.getSubmitHint().setText("修改物品失败，选择的物品为空");
            return;
        }
        String type = "";
        if (view.getVisitView().getEditItemList().get(selectedOptionNum) instanceof Atlas) {
            type = "图书";
        } else if (view.getVisitView().getEditItemList().get(selectedOptionNum) instanceof Disc) {
            type = "视频光盘";
        } else if (view.getVisitView().getEditItemList().get(selectedOptionNum) instanceof Drawing) {
            type = "图画";
        }

        String inputItemSerialNumber = view.getInputItemSerialNumber().getText();
        String inputItemTitle = view.getInputItemTitle().getText();
        String inputItemAuthor = view.getInputItemAuthor().getText();
        String inputItemRating = view.getInputItemRating().getText();
        String inputItemSelect1 = view.getInputItemSelect1().getText();
        String inputItemSelect2 = view.getInputItemSelect2().getText();
        String inputItemSelect3 = view.getInputItemSelect3().getText();

        if (inputItemSerialNumber.length() == 0 || inputItemTitle.length() == 0 || inputItemAuthor.length() == 0
                || inputItemRating.length() == 0 || inputItemSelect1.length() == 0 || inputItemSelect2.length() == 0
                || inputItemSelect3.length() == 0) {
            view.getSubmitHint().setText("修改物品失败，某栏为空");
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

            if ((isSerialNumberExist(atlasArray, inputItemSerialNumber)
                    || isSerialNumberExist(discArray, inputItemSerialNumber)
                    || isSerialNumberExist(drawingArray, inputItemSerialNumber))
                    && !inputItemSerialNumber
                            .equals(view.getVisitView().getEditItemList().get(selectedOptionNum).getSerialNumber())) {
                view.getSubmitHint().setText("修改物品失败，编号已存在");
                return;
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject newItemJson = null;

            if (type.equals("图书")) {
                for (int i = 0; i < atlasArray.size(); i++) {
                    JsonObject item = atlasArray.get(i).getAsJsonObject();
                    if (item.get("SerialNumber").getAsString()
                            .equals(view.getVisitView().getEditItemList().get(selectedOptionNum).getSerialNumber())) {
                        atlasArray.remove(i);
                        break;
                    }
                }
                Atlas item = new Atlas(inputItemSerialNumber, inputItemTitle, inputItemAuthor, inputItemRating,
                        inputItemSelect1, inputItemSelect2, inputItemSelect3);
                newItemJson = gson.toJsonTree(item).getAsJsonObject();
                atlasArray.add(newItemJson);
            } else if (type.equals("视频光盘")) {
                for (int i = 0; i < discArray.size(); i++) {
                    JsonObject item = discArray.get(i).getAsJsonObject();
                    if (item.get("SerialNumber").getAsString()
                            .equals(view.getVisitView().getEditItemList().get(selectedOptionNum).getSerialNumber())) {
                        discArray.remove(i);
                        break;
                    }
                }
                Disc item = new Disc(inputItemSerialNumber, inputItemTitle, inputItemAuthor, inputItemRating,
                        inputItemSelect1, inputItemSelect2, inputItemSelect3);
                newItemJson = gson.toJsonTree(item).getAsJsonObject();
                discArray.add(newItemJson);
            } else if (type.equals("图画")) {
                for (int i = 0; i < drawingArray.size(); i++) {
                    JsonObject item = drawingArray.get(i).getAsJsonObject();
                    if (item.get("SerialNumber").getAsString()
                            .equals(view.getVisitView().getEditItemList().get(selectedOptionNum).getSerialNumber())) {
                        drawingArray.remove(i);
                        break;
                    }
                }
                Drawing item = new Drawing(inputItemSerialNumber, inputItemTitle, inputItemAuthor, inputItemRating,
                        inputItemSelect1, inputItemSelect2, inputItemSelect3);
                newItemJson = gson.toJsonTree(item).getAsJsonObject();
                drawingArray.add(newItemJson);
            }

            FileWriter fileWriter = new FileWriter("data/items.json");
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            gson.toJson(jsonObject, jsonWriter);
            jsonWriter.close();
            view.getSubmitHint().setText("修改物品成功");

            view.getVisitView().getEditItemList().clear();
            view.getComboBox().removeAllItems();

        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, "数据文件不存在", "警告", JOptionPane.WARNING_MESSAGE);
        }

    }

    // 判断是否存在这个编号的物品
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
