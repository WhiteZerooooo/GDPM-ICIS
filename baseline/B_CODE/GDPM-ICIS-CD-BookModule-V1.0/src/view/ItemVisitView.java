package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ItemStorage.Item;
import handle.HandleItemVisitSubmit;

public class ItemVisitView extends JPanel {
    private JComboBox<String> comboBox; // 选择下拉框
    private JTextField inputItemInfo; // 输入物品编号
    private JButton submit; // 查询按钮
    private JTextArea showItem; // 查询结果
    private List<Item> editItemList = new ArrayList<>();; // 提供给编辑物品选项卡
    private HandleItemVisitSubmit handleItemVisitSubmit; // 处理查询按钮
    private EditItemView editView;

    public ItemVisitView() {
        initView();
        registerListener();
    }

    // getter and setter

    public JComboBox<String> getComboBox() {
        return this.comboBox;
    }

    public void setComboBox(JComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }

    public JTextField getInputItemInfo() {
        return this.inputItemInfo;
    }

    public void setInputItemInfo(JTextField inputItemInfo) {
        this.inputItemInfo = inputItemInfo;
    }

    public JButton getSubmit() {
        return this.submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JTextArea getShowItem() {
        return this.showItem;
    }

    public void setShowItem(JTextArea showItem) {
        this.showItem = showItem;
    }

    public List<Item> getEditItemList() {
        return this.editItemList;
    }

    public void setEditItemList(List<Item> editItemList) {
        this.editItemList = editItemList;
    }

    public HandleItemVisitSubmit getHandleItemVisitSubmit() {
        return this.handleItemVisitSubmit;
    }

    public void setHandleItemVisitSubmit(HandleItemVisitSubmit handleItemVisitSubmit) {
        this.handleItemVisitSubmit = handleItemVisitSubmit;
    }

    public EditItemView getEditView() {
        return this.editView;
    }

    public void setEditView(EditItemView editView) {
        this.editView = editView;
    }

    // 初始化面板视图
    private void initView() {
        setLayout(new BorderLayout());

        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.NORTH);
        box.add(Box.createHorizontalStrut(5));

        box.add(new JLabel("查询方式:"));
        box.add(Box.createHorizontalStrut(5));

        comboBox = new JComboBox<String>();
        comboBox.addItem("标题");
        comboBox.addItem("编号");
        comboBox.addItem("类别");
        comboBox.setPreferredSize(new Dimension(100, 30));
        box.add(comboBox);
        box.add(Box.createHorizontalStrut(5));

        inputItemInfo = new JTextField();
        box.add(inputItemInfo);
        box.add(Box.createHorizontalStrut(5));

        submit = new JButton("查询");
        submit.setIcon(MainView.getSmallIcon("img/visit.png", 25, 25));
        box.add(submit);
        box.add(Box.createHorizontalStrut(5));

        showItem = new JTextArea();
        showItem.setEditable(false);
        showItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(showItem), BorderLayout.CENTER);
    }

    // 注册监听
    private void registerListener() {
        handleItemVisitSubmit = new HandleItemVisitSubmit();
        handleItemVisitSubmit.setView(this);
        submit.addActionListener(handleItemVisitSubmit);
    }
}
