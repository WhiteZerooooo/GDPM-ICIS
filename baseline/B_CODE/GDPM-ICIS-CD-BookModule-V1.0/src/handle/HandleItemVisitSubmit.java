package handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import ItemStorage.Atlas;
import ItemStorage.Disc;
import ItemStorage.Drawing;
import view.ItemVisitView;
import view.MainView;

public class HandleItemVisitSubmit implements ActionListener {
    public ItemVisitView view;

    public void setView(ItemVisitView view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        view.getShowItem().setText("");
        view.getEditItemList().clear();
        view.getEditView().getComboBox().removeAllItems();

        String type = (String) view.getComboBox().getSelectedItem();
        String inputItemInfo = view.getInputItemInfo().getText();

        if (inputItemInfo.length() == 0) {
            view.getShowItem().setText("查询物品失败，请输入要查询的信息");
            return;
        }

        MainView.checkDataFile();
        try {
            FileReader itemFile = new FileReader("data/items.json");
            JsonObject jsonObject = JsonParser.parseReader(itemFile).getAsJsonObject();
            itemFile.close();

            List<JsonObject> atlasList = parseJsonArray(jsonObject.getAsJsonArray("atlas"));
            List<JsonObject> discList = parseJsonArray(jsonObject.getAsJsonArray("disc"));
            List<JsonObject> drawingList = parseJsonArray(jsonObject.getAsJsonArray("drawing"));

            atlasList.sort(Comparator.comparing(o -> o.get("SerialNumber").getAsString()));
            discList.sort(Comparator.comparing(o -> o.get("SerialNumber").getAsString()));
            drawingList.sort(Comparator.comparing(o -> o.get("SerialNumber").getAsString()));

            JsonArray atlasArray = listToJsonArray(atlasList);
            JsonArray discArray = listToJsonArray(discList);
            JsonArray drawingArray = listToJsonArray(drawingList);

            if (type.equals("标题")) {
                int finalCount = 0;
                for (int i = 0; i < atlasArray.size(); i++) {
                    JsonObject item = atlasArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出版社: " + item.get("Publisher").getAsString()
                            + "  ISBN号: " + item.get("ISBN").getAsString()
                            + "  页数: " + item.get("Pages").getAsString();
                    Atlas itemTMP = new Atlas();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setPublisher(item.get("Publisher").getAsString());
                    itemTMP.setISBN(item.get("ISBN").getAsString());
                    itemTMP.setPages(item.get("Pages").getAsString());
                    if (item.get("Title").getAsString().contains(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < discArray.size(); i++) {
                    JsonObject item = discArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出品者: " + item.get("ProducerName").getAsString()
                            + "  出品年份: " + item.get("ProducionYear").getAsString()
                            + "  视频时长(s): " + item.get("VedioLength").getAsString();
                    Disc itemTMP = new Disc();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setProducerName(item.get("ProducerName").getAsString());
                    itemTMP.setProducionYear(item.get("ProducionYear").getAsString());
                    itemTMP.setVedioLength(item.get("VedioLength").getAsString());
                    if (item.get("Title").getAsString().contains(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < drawingArray.size(); i++) {
                    JsonObject item = drawingArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  国籍: " + item.get("Nationality").getAsString()
                            + "  作品长度(cm): " + item.get("WorkLength").getAsString()
                            + "  作品宽度(cm): " + item.get("WorkWidth").getAsString();
                    Drawing itemTMP = new Drawing();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setNationality(item.get("Nationality").getAsString());
                    itemTMP.setWorkLength(item.get("WorkLength").getAsString());
                    itemTMP.setWorkWidth(item.get("WorkWidth").getAsString());
                    if (item.get("Title").getAsString().contains(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                if (finalCount == 0) {
                    view.getShowItem().setText("包含该标题的物品不存在！");
                } else {
                    for (int i = 0; i < view.getEditItemList().size(); i++) {
                        view.getEditView().getComboBox()
                                .addItem("编号：" + view.getEditItemList().get(i).getSerialNumber());
                    }
                }
            } else if (type.equals("编号")) {
                int finalCount = 0;
                for (int i = 0; i < atlasArray.size(); i++) {
                    JsonObject item = atlasArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出版社: " + item.get("Publisher").getAsString()
                            + "  ISBN号: " + item.get("ISBN").getAsString()
                            + "  页数: " + item.get("Pages").getAsString();
                    Atlas itemTMP = new Atlas();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setPublisher(item.get("Publisher").getAsString());
                    itemTMP.setISBN(item.get("ISBN").getAsString());
                    itemTMP.setPages(item.get("Pages").getAsString());
                    if (item.get("SerialNumber").getAsString().equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < discArray.size(); i++) {
                    JsonObject item = discArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出品者: " + item.get("ProducerName").getAsString()
                            + "  出品年份: " + item.get("ProducionYear").getAsString()
                            + "  视频时长(s): " + item.get("VedioLength").getAsString();
                    Disc itemTMP = new Disc();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setProducerName(item.get("ProducerName").getAsString());
                    itemTMP.setProducionYear(item.get("ProducionYear").getAsString());
                    itemTMP.setVedioLength(item.get("VedioLength").getAsString());
                    if (item.get("SerialNumber").getAsString().equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < drawingArray.size(); i++) {
                    JsonObject item = drawingArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  国籍: " + item.get("Nationality").getAsString()
                            + "  作品长度(cm): " + item.get("WorkLength").getAsString()
                            + "  作品宽度(cm): " + item.get("WorkWidth").getAsString();
                    Drawing itemTMP = new Drawing();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setNationality(item.get("Nationality").getAsString());
                    itemTMP.setWorkLength(item.get("WorkLength").getAsString());
                    itemTMP.setWorkWidth(item.get("WorkWidth").getAsString());
                    if (item.get("SerialNumber").getAsString().equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                if (finalCount == 0) {
                    view.getShowItem().setText("该编号不存在！");
                } else {
                    for (int i = 0; i < view.getEditItemList().size(); i++) {
                        view.getEditView().getComboBox()
                                .addItem("编号：" + view.getEditItemList().get(i).getSerialNumber());
                    }
                }
            } else if (type.equals("类别")) {
                int finalCount = 0;
                for (int i = 0; i < atlasArray.size(); i++) {
                    JsonObject item = atlasArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出版社: " + item.get("Publisher").getAsString()
                            + "  ISBN号: " + item.get("ISBN").getAsString()
                            + "  页数: " + item.get("Pages").getAsString();
                    Atlas itemTMP = new Atlas();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setPublisher(item.get("Publisher").getAsString());
                    itemTMP.setISBN(item.get("ISBN").getAsString());
                    itemTMP.setPages(item.get("Pages").getAsString());
                    if ("图书".equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < discArray.size(); i++) {
                    JsonObject item = discArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  出品者: " + item.get("ProducerName").getAsString()
                            + "  出品年份: " + item.get("ProducionYear").getAsString()
                            + "  视频时长(s): " + item.get("VedioLength").getAsString();
                    Disc itemTMP = new Disc();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setProducerName(item.get("ProducerName").getAsString());
                    itemTMP.setProducionYear(item.get("ProducionYear").getAsString());
                    itemTMP.setVedioLength(item.get("VedioLength").getAsString());
                    if ("视频光盘".equals(inputItemInfo) || "视频".equals(inputItemInfo) || "光盘".equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                for (int i = 0; i < drawingArray.size(); i++) {
                    JsonObject item = drawingArray.get(i).getAsJsonObject();
                    String text = "编号: " + item.get("SerialNumber").getAsString()
                            + "  标题: " + item.get("Title").getAsString()
                            + "  作者: " + item.get("Author").getAsString()
                            + "  评级: " + item.get("Rating").getAsString()
                            + "  国籍: " + item.get("Nationality").getAsString()
                            + "  作品长度(cm): " + item.get("WorkLength").getAsString()
                            + "  作品宽度(cm): " + item.get("WorkWidth").getAsString();
                    Drawing itemTMP = new Drawing();
                    itemTMP.setSerialNumber(item.get("SerialNumber").getAsString());
                    itemTMP.setTitle(item.get("Title").getAsString());
                    itemTMP.setAuthor(item.get("Author").getAsString());
                    itemTMP.setRating(item.get("Rating").getAsString());
                    itemTMP.setNationality(item.get("Nationality").getAsString());
                    itemTMP.setWorkLength(item.get("WorkLength").getAsString());
                    itemTMP.setWorkWidth(item.get("WorkWidth").getAsString());
                    if ("图画".equals(inputItemInfo)) {
                        finalCount++;
                        view.getShowItem().append(finalCount + "、" + text + "\n");
                        view.getEditItemList().add(itemTMP);
                    }
                }
                if (finalCount == 0) {
                    view.getShowItem().setText("该类别没有物品！");
                } else {
                    for (int i = 0; i < view.getEditItemList().size(); i++) {
                        view.getEditView().getComboBox()
                                .addItem("编号：" + view.getEditItemList().get(i).getSerialNumber());
                    }
                }
            }
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, "数据文件不存在", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 将 JsonArray 转换为 List
    private static List<JsonObject> parseJsonArray(JsonArray array) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(array, new TypeToken<List<JsonObject>>() {
        }.getType());
    }

    // 将 List 转换为 JsonArray
    private static JsonArray listToJsonArray(List<JsonObject> list) {
        JsonArray jsonArray = new JsonArray();
        list.forEach(jsonArray::add);
        return jsonArray;
    }
}
