package transfer.view;

import resourses.R;
import transfer.data.Configs;
import transfer.data.DataList;
import transfer.thread.DetectThread;
import transfer.thread.ExposeThread;
import transfer.thread.ServerAcceptThread;
import transfer.utils.CommonUtils;
import transfer.view.dialogs.SettingDialog;
import transfer.view.panes.DetectPane;
import transfer.view.panes.SelectFilePane;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;


public class MainView extends JFrame {

    //按下点
    private Point pressedPoint;
    //icon
    private JMenuItem iconMenuIcon;
    //窗口操作按钮（使用菜单项实现）
    private JMenuItem minimizeMenuItem,closeMenuItem;
    //菜单栏
    private JMenuBar menuBar;

    //菜单
    private JMenu toolMenu,settingMenu;
    //设置菜单选项:指定传输-侦听
    private JMenuItem appointItem,detectItem;
    private JMenuItem settingItem;

    //UDP广播端口
    private DatagramSocket udpSocket;
    //侦测/暴露线程
    private ExposeThread exposeThread;
    private DetectThread detectThread;

    //TCP接收线程
    private ServerAcceptThread serverAcceptThread;

    //配置文件
    private File configFile=new File(R.Configs.CONFIG_FILE_DIRECTORY,R.Configs.CONFIG_FILE_NAME);

    //背景图片载体
    private final JLabel embed=new JLabel();//初始化载体地址---载体地址始终不变;

    public MainView(){

        this.initConfig();
        this.initUDPThread();
        this.initTCPThread();
        this.initFrame();
        this.initView();
        this.addMenuBar();
        this.addComponents();
        this.addListeners();



        //自适应大小
        //this.pack();
    }

    /*
    初始化读取配置文件
     */
    private void initConfig(){
        DataInputStream dataInputStream=null;
        try{
            dataInputStream=new DataInputStream(new FileInputStream(configFile));
            String config=dataInputStream.readUTF();
            if(config==null||config.equals(""))
                    throw new Exception();
            else{
                Configs.fromFile(config);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,R.Strings.READ_CONFIG_ERROR,R.Strings.TIP_TITLE,JOptionPane.ERROR_MESSAGE);


        }
    }

    /*
   TCP接收线程初始化--默认开启
    */
    private void initTCPThread(){
        try {
            serverAcceptThread = new ServerAcceptThread(new ServerSocket(R.Configs.TCP_PORT));
            serverAcceptThread.start();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,R.Strings.PORT_ERROR);
        }
    }
    /*
    UDP广播初始化--默认开启
     */
    private void initUDPThread(){
        try {
            udpSocket = new DatagramSocket(R.Configs.UDP_PORT);
            exposeThread = new ExposeThread(udpSocket);
            detectThread = new DetectThread(udpSocket);

            //启动线程
            //加载配置文件
            exposeThread.start();
            if(Configs.getUdpSwitch()==1)
                exposeThread.setSwitch(false);
            detectThread.start();






        }catch(SocketException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"程序已运行！","Tip",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"程序打开失败！");
        }
    }
    private void initFrame(){
        this.setTitle(R.Strings.MAIN_TITLE);
        this.setIconImage(CommonUtils.getIamge(R.Pictures.ICON));
        this.setSize(600,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        //初始背景
        CommonUtils.setBackground(this,embed,CommonUtils.getDIVImage(this.getWidth(),this.getHeight(),R.Pictures.MAIN_BG));
        //---test
        this.setContentPane(new SelectFilePane());
        //---test
        this.setDefaultCloseOperation(MainView.EXIT_ON_CLOSE);
    }
    private void initView(){
        Font font=CommonUtils.getDefaultFont(15);
        //窗口icon
        iconMenuIcon=new JMenuItem(new ImageIcon(CommonUtils.getDIVImage(20,20,R.Pictures.ICON)));
        iconMenuIcon.setOpaque(false);
        iconMenuIcon.setEnabled(false);
        //窗体操作按钮
        closeMenuItem=new JMenuItem(new ImageIcon(CommonUtils.getDIVImage(20,20,R.Pictures.CLOSE_ICON)));
        closeMenuItem.setOpaque(false);
        closeMenuItem.setEnabled(false);
        minimizeMenuItem=new JMenuItem(new ImageIcon(CommonUtils.getDIVImage(15,20,R.Pictures.MINIMIZE_ICON)));
        minimizeMenuItem.setOpaque(false);
        minimizeMenuItem.setEnabled(false);

        //菜单栏
        menuBar=new JMenuBar();

        //菜单
        toolMenu =new JMenu(R.Strings.TOOL_MENU);
        toolMenu.setForeground(Color.WHITE);
        toolMenu.setFont(font);

        settingMenu=new JMenu(R.Strings.SETTING_MENU_ITEM);
        settingMenu.setForeground(Color.WHITE);
        settingMenu.setFont(font);

        //菜单项
        detectItem=new JMenuItem(R.Strings.DETECT_MENU_ITEM);
        detectItem.setFont(font);
        appointItem=new JMenuItem(R.Strings.APPOINT_MENU_ITEM);
        appointItem.setFont(font);

        settingItem=new JMenuItem(R.Strings.SETTING_MENU_ITEM);


        settingItem.setFont(font);





    }
    private void addComponents(){

    }
    private void addMenuBar(){
        toolMenu.add(appointItem);
        toolMenu.addSeparator();
        toolMenu.add(detectItem);

        settingMenu.add(settingItem);

        menuBar.add(iconMenuIcon);

        menuBar.add(toolMenu);
        menuBar.add(Box.createHorizontalStrut(2));
        menuBar.add(settingMenu);
        menuBar.add(Box.createHorizontalStrut(400));

        menuBar.add(minimizeMenuItem);
        menuBar.add(closeMenuItem);

        menuBar.setBackground(new Color(0,0,0,0.15f));
        menuBar.setBorderPainted(false);
        menuBar.repaint();
        this.setJMenuBar(menuBar);
    }
    private void addListeners(){
        //菜单栏
        menuBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                do_topPanel_mouseDragged(e);
            }
        });
        //菜单栏
        menuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO 自动生成的方法存根
                do_topPanel_mousePressed(e);
            }
        });
        //最小化菜单项
        minimizeMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MainView.this.setExtendedState(MainView.HIDE_ON_CLOSE);
            }
        });
        //关闭菜单项
        closeMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                saveConfigs();
                MainView.this.dispose();
                System.exit(0);
            }
        });
        //指定传输菜单项
        appointItem.addActionListener(e->{
            this.changePane(new SelectFilePane());
            CommonUtils.setBackground(this,embed,CommonUtils.getDIVImage(this.getWidth(),this.getHeight(),R.Pictures.MAIN_BG));

        });
        //侦听菜单项
        detectItem.addActionListener(e->{
            this.changePane(new DetectPane());
            CommonUtils.setBackground(this,embed,CommonUtils.getDIVImage(this.getWidth(),this.getHeight(),R.Pictures.DETECT_BG));
            menuBar.repaint();
        });
        //设置菜单项
        settingItem.addActionListener(e->{
                new SettingDialog(exposeThread).setVisible(true);
        });
        //本窗口
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
               saveConfigs();
            }
        });
    }
    /*
    ---之所以不直接用字符流，是因为不想让人随意查看配置文件--抬高一点逼格,然并卵...haha...
     */
    private void saveConfigs(){
        DataOutputStream dataOutputStream=null;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(configFile));
            dataOutputStream.writeUTF(Configs.toFile());
            dataOutputStream.close();

        }catch (Exception e){
            e.printStackTrace();


            try {
                if (dataOutputStream!=null)
                    dataOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
    /*
    切换面板
     */
    private void changePane(JPanel panel){
        this.remove(this.getContentPane());
        this.setContentPane(panel);
        //重新布局组件
        this.validate();

    }
    protected void do_topPanel_mousePressed(MouseEvent e) {
        // TODO 自动生成的方法存根
        pressedPoint = e.getPoint();
    }

    protected void do_topPanel_mouseDragged(MouseEvent e) {
        // TODO 自动生成的方法存根
        Point point = e.getPoint();
        Point locationPoint = getLocation();

        int x = locationPoint.x + point.x - pressedPoint.x;
        int y = locationPoint.y + point.y - pressedPoint.y;

        setLocation(x, y);
    }


}
