package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

public class MainView extends JFrame {
    public JTabbedPane tabbedPane; // 选项卡面板
    public ItemListView itemListView; // 浏览所有物品视图
    public AddItemView addItemView; // 添加物品视图
    public RemoveItemView removeItemView; // 移除物品视图
    public ItemVisitView itemVisitView; // 查询物品视图
    public EditItemView editItemView; // 编辑物品视图

    public MainView() {
        initSetting();
        initView();
        checkDataFile();
    }

    // 初始化窗口设置
    public void initSetting() {
        setTitle("媒体库管理系统");
        setBounds(100, 100, 1200, 560);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGlobalFont(new Font("微软雅黑", Font.BOLD, 16));
        setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

        java.net.URL imgUrl = MainView.class.getResource("/img/icon.png");
        ImageIcon icon = new ImageIcon(imgUrl);
        Image image = icon.getImage();
        setIconImage(image);
    }

    // 初始化窗口视图
    public void initView() {
        // 菜单
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("菜单");
        JMenuItem menuItem1 = new JMenuItem("打开数据文件");
        menuItem1.addActionListener(e -> {
            checkDataFile();
            File folder = new File("data/items.json");
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(folder);
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        });
        menu.add(menuItem1);
        menuBar.add(menu);

        JMenu styleMenu = new JMenu("页面风格");
        String StyleName[] = { "默认风格", "柔和黑", "木质感", "Swing风格", "金属风格" };
        String StyleAddress[] = {
                "com.jtattoo.plaf.mcwin.McWinLookAndFeel",
                "com.jtattoo.plaf.noire.NoireLookAndFeel",
                "com.jtattoo.plaf.smart.SmartLookAndFeel",
                "com.jtattoo.plaf.fast.FastLookAndFeel",
                "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"
        };
        for (int i = 0; i < StyleName.length; i++) {
            String styleAddress = StyleAddress[i];
            JMenuItem styleItem = new JMenuItem(StyleName[i]);
            styleItem.addActionListener(e -> setLookAndFeel(styleAddress));
            styleMenu.add(styleItem);
        }
        menuBar.add(styleMenu);

        // 面板
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane, BorderLayout.CENTER);

        itemListView = new ItemListView();
        addItemView = new AddItemView();
        removeItemView = new RemoveItemView();
        itemVisitView = new ItemVisitView();
        editItemView = new EditItemView();

        itemVisitView.setEditView(editItemView);
        editItemView.setVisitView(itemVisitView);

        tabbedPane.add("浏览所有物品", wrapInPanel(itemListView));
        tabbedPane.add("添加物品", wrapInPanel(addItemView));
        tabbedPane.add("删除物品", wrapInPanel(removeItemView));
        tabbedPane.add("查询物品", wrapInPanel(itemVisitView));
        tabbedPane.add("编辑物品", wrapInPanel(editItemView));

        validate();
    }

    // 检查数据文件
    public static void checkDataFile() {
        File file = new File("data/items.json");

        // 检查目录是否存在，不存在则创建目录
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                JOptionPane.showMessageDialog(null, "创建文件目录失败", "警告", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // 判断数据文件是否存在，不存在则创建文件
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    writer.write("{\"atlas\":[],\"disc\":[],\"drawing\":[]}");
                    writer.close();
                } else {
                    JOptionPane.showMessageDialog(null, "创建数据文件失败", "警告", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "创建数据文件失败", "警告", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // 给面板添加边框
    private JPanel wrapInPanel(JPanel panel) {
        JPanel wrappedPanel = new JPanel(new BorderLayout());
        wrappedPanel.add(panel, BorderLayout.CENTER);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return wrappedPanel;
    }

    // 设置外观风格
    private void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "无法应用该外观风格", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 设置全局字体函数
    public static void initGlobalFont(Font fnt) {
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    // 设置Icon缩放
    public static ImageIcon getSmallIcon(String address, int targetWidth, int targetHeight) {
        java.net.URL imgUrl = MainView.class.getResource("/" + address);
        Image Image = new ImageIcon(imgUrl).getImage();
        ImageIcon Icon = new ImageIcon(Image.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH));
        return Icon;
    }
}