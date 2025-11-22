package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ItemStorage.Atlas;
import ItemStorage.Disc;
import ItemStorage.Drawing;
import handle.HandleEditItemSubmit;

public class EditItemView extends JPanel {
    private JComboBox<String> comboBox; // 选择下拉框
    private JTextField inputItemSerialNumber; // 输入物品编号
    private JTextField inputItemTitle; // 输入物品标题
    private JTextField inputItemAuthor; // 输入物品作者
    private JTextField inputItemRating; // 输入物品评级
    private JTextField inputItemSelect1; // 输入图书出版社/光盘出品者/图画出品国籍
    private JTextField inputItemSelect2; // 输入图书ISBN号/光盘出品年份/图画作品长度
    private JTextField inputItemSelect3; // 输入物品评级图书页数/光盘视频时长/图画作品宽度
    private JButton submit; // 提交按钮
    private JTextField submitHint; // 提交提示
    private HandleEditItemSubmit handleEditItemSubmit; // 处理提交按钮
    private ItemVisitView visitView;

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

    public HandleEditItemSubmit getHandleEditItemSubmit() {
        return this.handleEditItemSubmit;
    }

    public void setHandleEditItemSubmit(HandleEditItemSubmit handleEditItemSubmit) {
        this.handleEditItemSubmit = handleEditItemSubmit;
    }

    public ItemVisitView getVisitView() {
        return this.visitView;
    }

    public void setVisitView(ItemVisitView visitView) {
        this.visitView = visitView;
    }

    public EditItemView() {
        initView();
        registerListener();
    }

    // 初始化面板视图
    public void initView() {
        Box boxV = Box.createVerticalBox(); // 列盒子
        Box[] boxH = new Box[8];
        Box submitBox = Box.createHorizontalBox();

        String[] labels = { "物品选择:", "物品编号:", "物品标题:", "物品作者:", "物品评级:", "图书出版社:", "图书ISBN号:", "图书页数:" };
        JTextField[] textFields = new JTextField[labels.length];
        JComboBox<String> comboBoxTmp = null;
        for (int i = 0; i < labels.length; i++) {
            boxH[i] = Box.createHorizontalBox();
            boxH[i].add(new JLabel(labels[i]));
            boxH[i].add(Box.createHorizontalStrut(10));
            if (i == 0) {
                comboBoxTmp = new JComboBox<String>();
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
                if (comboBox.getSelectedIndex() < 0 || comboBox.getItemCount() < comboBox.getSelectedIndex() + 1) {
                    inputItemSerialNumber.setText("");
                    inputItemTitle.setText("");
                    inputItemAuthor.setText("");
                    inputItemRating.setText("");
                    inputItemSelect1.setText("");
                    inputItemSelect2.setText("");
                    inputItemSelect3.setText("");
                    return;
                }
                int selectedOptionNum = comboBox.getSelectedIndex();
                String selectedOption = "";
                if (visitView.getEditItemList().get(selectedOptionNum) instanceof Atlas) {
                    selectedOption = "图书";
                } else if (visitView.getEditItemList().get(selectedOptionNum) instanceof Disc) {
                    selectedOption = "视频光盘";
                } else if (visitView.getEditItemList().get(selectedOptionNum) instanceof Drawing) {
                    selectedOption = "图画";
                }
                JLabel selectLabel1 = (JLabel) boxH[5].getComponent(0);
                JLabel selectLabel2 = (JLabel) boxH[6].getComponent(0);
                JLabel selectLabel3 = (JLabel) boxH[7].getComponent(0);
                if (selectedOption.equals("图书")) {
                    selectLabel1.setText("图书出版社:");
                    selectLabel2.setText("图书ISBN号:");
                    selectLabel3.setText("图书页数:");
                    Atlas item = (Atlas) visitView.getEditItemList().get(selectedOptionNum);
                    inputItemSelect1.setText(item.getPublisher());
                    inputItemSelect2.setText(item.getISBN());
                    inputItemSelect3.setText(item.getPages());
                } else if (selectedOption.equals("视频光盘")) {
                    selectLabel1.setText("光盘出品者:");
                    selectLabel2.setText("光盘出品年份:");
                    selectLabel3.setText("光盘视频时长:");
                    Disc item = (Disc) visitView.getEditItemList().get(selectedOptionNum);
                    inputItemSelect1.setText(item.getProducerName());
                    inputItemSelect2.setText(item.getProducionYear());
                    inputItemSelect3.setText(item.getVedioLength());
                } else if (selectedOption.equals("图画")) {
                    selectLabel1.setText("图画出品国籍:");
                    selectLabel2.setText("图画作品长度:");
                    selectLabel3.setText("图画作品宽度:");
                    Drawing item = (Drawing) visitView.getEditItemList().get(selectedOptionNum);
                    inputItemSelect1.setText(item.getNationality());
                    inputItemSelect2.setText(item.getWorkLength());
                    inputItemSelect3.setText(item.getWorkWidth());
                }
                inputItemSerialNumber.setText(visitView.getEditItemList().get(selectedOptionNum).getSerialNumber());
                inputItemTitle.setText(visitView.getEditItemList().get(selectedOptionNum).getTitle());
                inputItemAuthor.setText(visitView.getEditItemList().get(selectedOptionNum).getAuthor());
                inputItemRating.setText(visitView.getEditItemList().get(selectedOptionNum).getRating());
            }
        });

        submit = new JButton("修改物品");
        submit.setIcon(MainView.getSmallIcon("img/edit.png", 25, 25));

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
        handleEditItemSubmit = new HandleEditItemSubmit();
        handleEditItemSubmit.setView(this);
        submit.addActionListener(handleEditItemSubmit);
    }
}
