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

import view.ItemListView;
import view.MainView;

public class HandleItemListSubmit implements ActionListener {
   public ItemListView view;

   public void setView(ItemListView view) {
      this.view = view;
   }

   public void actionPerformed(ActionEvent e) {
      view.getShowItem().setText("");

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

         int atlasCount = atlasArray.size();
         int discCount = discArray.size();
         int drawingCount = drawingArray.size();
         int totalCount = atlasCount + discCount + drawingCount;

         if (totalCount == 0) {
            view.getShowItem().append("物品库为空！");
         } else {
            // 输出 "统计" 数据
            view.getShowItem().append("---------- 统计 ----------\n");
            view.getShowItem().append("图书数量：" + atlasCount + "\n");
            view.getShowItem().append("视频光盘数量：" + discCount + "\n");
            view.getShowItem().append("图画数量：" + drawingCount + "\n");
            view.getShowItem().append("总计：" + totalCount + " / 100\n");

            // 输出 "图书类" 数据
            view.getShowItem().append("\n---------- 图书 ----------\n");
            if (atlasCount == 0)
               view.getShowItem().append("无图书类物品\n");
            outputDataInfo(atlasArray, "出版社", "ISBN号", "页数", "Publisher", "ISBN", "Pages");

            // 输出 "视频光盘类" 数据
            view.getShowItem().append("\n-------- 视频光盘 --------\n");
            if (discCount == 0)
               view.getShowItem().append("无视频光盘类物品\n");
            outputDataInfo(discArray, "出品者", "出品年份", "视频时长(s)", "ProducerName", "ProducionYear", "VedioLength");

            // 输出 "图画类" 数据
            view.getShowItem().append("\n---------- 图画 ----------\n");
            if (drawingCount == 0)
               view.getShowItem().append("无图画类物品\n");
            outputDataInfo(drawingArray, "国籍", "作品长度(cm)", "作品宽度(cm)", "Nationality", "WorkLength", "WorkWidth");
         }
      } catch (IOException ee) {
         JOptionPane.showMessageDialog(null, "数据文件不存在", "警告", JOptionPane.WARNING_MESSAGE);
      }
   }

   private void outputDataInfo(JsonArray infoArray, String t1, String t2, String t3, String s1, String s2, String s3) {
      for (int i = 0; i < infoArray.size(); i++) {
         JsonObject item = infoArray.get(i).getAsJsonObject();
         String text = "编号: " + item.get("SerialNumber").getAsString()
               + "  标题: " + item.get("Title").getAsString()
               + "  作者: " + item.get("Author").getAsString()
               + "  评级: " + item.get("Rating").getAsString()
               + "  " + t1 + ": " + item.get(s1).getAsString()
               + "  " + t2 + ": " + item.get(s2).getAsString()
               + "  " + t3 + ": " + item.get(s3).getAsString();
         view.getShowItem().append(text + "\n");
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
