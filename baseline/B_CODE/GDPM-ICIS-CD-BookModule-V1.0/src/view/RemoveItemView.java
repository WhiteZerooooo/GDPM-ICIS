package view;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import handle.HandleRemoveItemSubmit;

public class RemoveItemView extends JPanel {
    private JTextField inputItemSerialNumber; // 输入物品编号
    private JButton submit; // 提交按钮
    private JTextField submitHint; // 提交提示
    private HandleRemoveItemSubmit handleRemoveItemSubmit; // 处理提交按钮

    public RemoveItemView() {
        initView();
        registerListener();
    }

    // getter and setter

    public JTextField getInputItemSerialNumber() {
        return this.inputItemSerialNumber;
    }

    public void setInputItemSerialNumber(JTextField inputItemSerialNumber) {
        this.inputItemSerialNumber = inputItemSerialNumber;
    }

    public JButton getSubmit() {
        return this.submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JTextField getSubmitHint() {
        return this.submitHint;
    }

    public void setSubmitHint(JTextField submitHint) {
        this.submitHint = submitHint;
    }

    public HandleRemoveItemSubmit getHandleRemoveItemSubmit() {
        return this.handleRemoveItemSubmit;
    }

    public void setHandleRemoveItemSubmit(HandleRemoveItemSubmit handleRemoveItemSubmit) {
        this.handleRemoveItemSubmit = handleRemoveItemSubmit;
    }

    // 初始化面板视图
    public void initView() {
        int totalCount = 0;

        inputItemSerialNumber = new JTextField(6);
        submit = new JButton("删除物品");
        submit.setIcon(MainView.getSmallIcon("img/remove.png", 25, 25));
        submitHint = new JTextField(30);
        submitHint.setEditable(false);

        MainView.checkDataFile();
        try {
            FileReader itemFile = new FileReader("data/items.json");
            JsonObject jsonObject = JsonParser.parseReader(itemFile).getAsJsonObject();
            itemFile.close();

            JsonArray atlasArray = jsonObject.getAsJsonArray("atlas");
            JsonArray discArray = jsonObject.getAsJsonArray("disc");
            JsonArray drawingArray = jsonObject.getAsJsonArray("drawing");
            
            int atlasCount = atlasArray.size();
            int discCount = discArray.size();
            int drawingCount = drawingArray.size();
            totalCount = atlasCount + discCount + drawingCount;
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        
        if (totalCount == 0) {
            add(new JLabel("物品库为空！"));
            return;
        }

        Box boxV = Box.createVerticalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();

        boxV.add(box1);
        boxV.add(Box.createVerticalStrut(5));
        boxV.add(box2);

        box1.add(new JLabel("输入要删除的物品编号:"));
        box1.add(Box.createHorizontalStrut(10));
        box1.add(inputItemSerialNumber);

        box2.add(submit);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(submitHint);

        add(boxV);
    }

    // 注册监听
    private void registerListener() {
        handleRemoveItemSubmit = new HandleRemoveItemSubmit();
        handleRemoveItemSubmit.setView(this);
        submit.addActionListener(handleRemoveItemSubmit);
    }
}
