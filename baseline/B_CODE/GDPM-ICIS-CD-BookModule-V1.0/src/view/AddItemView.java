package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import handle.HandleAddItemSubmit;

public class AddItemView extends JPanel {
    private JComboBox<String> comboBox; // "物品类型"下拉框
    private JTextField inputItemSerialNumber; // 物品编号
    private JTextField inputItemTitle; // 物品标题
    private JTextField inputItemAuthor; // 物品作者
    private JTextField inputItemRating; // 物品评级
    private JTextField inputItemSelect1; // 图书出版社/光盘出品者/图画出品国籍
    private JTextField inputItemSelect2; // 图书ISBN号/光盘出品年份/图画作品长度
    private JTextField inputItemSelect3; // 物品评级图书页数/光盘视频时长/图画作品宽度

    private JButton submit; // "添加物品"按钮
    private JTextField submitHint; // 提交提示
    private HandleAddItemSubmit handleAddItemSubmit; // 处理"添加物品"按钮

    public AddItemView() {
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

    public JTextField getInputItemSerialNumber() {
        return this.inputItemSerialNumber;
    }

    public void setInputItemSerialNumber(JTextField inputItemSerialNumber) {
        this.inputItemSerialNumber = inputItemSerialNumber;
    }

    public JTextField getInputItemTitle() {
        return this.inputItemTitle;
    }

    public void setInputItemTitle(JTextField inputItemTitle) {
        this.inputItemTitle = inputItemTitle;
    }

    public JTextField getInputItemAuthor() {
        return this.inputItemAuthor;
    }

    public void setInputItemAuthor(JTextField inputItemAuthor) {
        this.inputItemAuthor = inputItemAuthor;
    }

    public JTextField getInputItemRating() {
        return this.inputItemRating;
    }

    public void setInputItemRating(JTextField inputItemRating) {
        this.inputItemRating = inputItemRating;
    }

    public JTextField getInputItemSelect1() {
        return this.inputItemSelect1;
    }

    public void setInputItemSelect1(JTextField inputItemSelect1) {
        this.inputItemSelect1 = inputItemSelect1;
    }

    public JTextField getInputItemSelect2() {
        return this.inputItemSelect2;
    }

    public void setInputItemSelect2(JTextField inputItemSelect2) {
        this.inputItemSelect2 = inputItemSelect2;
    }

    public JTextField getInputItemSelect3() {
        return this.inputItemSelect3;
    }

    public void setInputItemSelect3(JTextField inputItemSelect3) {
        this.inputItemSelect3 = inputItemSelect3;
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

    public HandleAddItemSubmit getHandleAddItemSubmit() {
        return this.handleAddItemSubmit;
    }

    public void setHandleAddItemSubmit(HandleAddItemSubmit handleAddItemSubmit) {
        this.handleAddItemSubmit = handleAddItemSubmit;
    }

    // 初始化面板视图
    private void initView() {
        Box boxV = Box.createVerticalBox(); // 列盒子
        Box[] boxH = new Box[8]; // 行盒子组
        Box submitBox = Box.createHorizontalBox(); // 按钮盒子

        String[] labels = { "物品类型:", "物品编号:", "物品标题:", "物品作者:", "物品评级:", "图书出版社:", "图书ISBN号:", "图书页数:" };
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String> comboBoxTmp = null;
        for (int i = 0; i < labels.length; i++) {
            boxH[i] = Box.createHorizontalBox();
            boxH[i].add(new JLabel(labels[i]));
            boxH[i].add(Box.createHorizontalStrut(10));
            if (i == 0) {
                comboBoxTmp = new JComboBox<String>();
                comboBoxTmp.addItem("图书");
                comboBoxTmp.addItem("视频光盘");
                comboBoxTmp.addItem("图画");
                boxH[i].add(comboBoxTmp);
            } else {
                textFields[i] = new JTextField(30);
                boxH[i].add(textFields[i]);
            }
            boxV.add(boxH[i]);
            boxV.add(Box.createVerticalStrut(3));
        }

        comboBox = comboBoxTmp;
        inputItemSerialNumber = textFields[1];
        inputItemTitle = textFields[2];
        inputItemAuthor = textFields[3];
        inputItemRating = textFields[4];
        inputItemSelect1 = textFields[5];
        inputItemSelect2 = textFields[6];
        inputItemSelect3 = textFields[7];

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                JLabel selectLabel1 = (JLabel) boxH[5].getComponent(0);
                JLabel selectLabel2 = (JLabel) boxH[6].getComponent(0);
                JLabel selectLabel3 = (JLabel) boxH[7].getComponent(0);
                if (selectedOption.equals("图书")) {
                    selectLabel1.setText("图书出版社:");
                    selectLabel2.setText("图书ISBN号:");
                    selectLabel3.setText("图书页数:");
                } else if (selectedOption.equals("视频光盘")) {
                    selectLabel1.setText("光盘出品者:");
                    selectLabel2.setText("光盘出品年份:");
                    selectLabel3.setText("光盘视频时长:");
                } else if (selectedOption.equals("图画")) {
                    selectLabel1.setText("图画出品国籍:");
                    selectLabel2.setText("图画作品长度:");
                    selectLabel3.setText("图画作品宽度:");
                }
            }
        });

        // 创建按钮，并提示输出
        submit = new JButton("添加物品");
        submit.setIcon(MainView.getSmallIcon("img/add.png", 25, 25));
        
        submitHint = new JTextField(30);
        submitHint.setEditable(false);

        submitBox.add(submit);
        submitBox.add(Box.createHorizontalStrut(10));
        submitBox.add(submitHint);

        boxV.add(submitBox);
        add(boxV);
    }

    // 注册监听
    private void registerListener() {
        // "添加物品" 按钮监听
        handleAddItemSubmit = new HandleAddItemSubmit();
        handleAddItemSubmit.setView(this);
        submit.addActionListener(handleAddItemSubmit);
    }
}
