package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import handle.HandleItemListSubmit;

public class ItemListView extends JPanel {
    private JButton submit; // 查询按钮
    private JTextArea showItem; // 查询结果
    private HandleItemListSubmit handleItemListSubmit; // 处理查询按钮

    public ItemListView() {
        initView();
        registerListener();
    }

    // getter and setter

    public JButton getSubmit() {
        return this.submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JTextArea getShowItem() {
        return showItem;
    }

    public void setShowItem(JTextArea showItem) {
        this.showItem = showItem;
    }

    public HandleItemListSubmit getHandleItemListSubmit() {
        return this.handleItemListSubmit;
    }

    public void setHandleItemListSubmit(HandleItemListSubmit handleItemListSubmit) {
        this.handleItemListSubmit = handleItemListSubmit;
    }

    // 初始化面板视图
    private void initView() {
        setLayout(new BorderLayout());

        submit = new JButton("更新数据");
        submit.setIcon(MainView.getSmallIcon("img/list.png", 25, 25));
        submit.setPressedIcon(MainView.getSmallIcon("img/listpressed.png", 25, 25));
        showItem = new JTextArea();
        showItem.setEditable(false);
        showItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(submit, BorderLayout.NORTH);
        add(new JScrollPane(showItem), BorderLayout.CENTER);
    }

    // 注册监听
    private void registerListener() {
        // "更新数据" 按钮监听
        handleItemListSubmit = new HandleItemListSubmit();
        handleItemListSubmit.setView(this);
        submit.addActionListener(handleItemListSubmit);
    }
}
