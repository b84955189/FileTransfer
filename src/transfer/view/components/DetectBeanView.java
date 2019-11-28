package transfer.view.components;

import resourses.R;
import transfer.base.BasePane;
import transfer.thread.SendThread;
import transfer.view.dialogs.MyJFileChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.InetSocketAddress;

public class DetectBeanView extends BasePane {
    private JLabel label_ip;
    private JButton button_commit;


    private MyJFileChooser myJFileChooser;

    //发送地址
    private String ip;

    public DetectBeanView(String ip){
        super();//即使不写，也默认调用
        this.ip=ip;
        label_ip.setText(ip);
    }

    //--test
    public DetectBeanView(){
        super();
    }
    //--test



    @Override
    protected void initPane() {
        this.setBackground(Color.decode("#757976"));
        this.setLayout(null);
    }

    @Override
    protected void initViews() {
        //字体
        Font font=new Font(R.Fonts.DEFAULT_FONT,Font.PLAIN,18);

        //文件选择器
        myJFileChooser=new MyJFileChooser();



        //标签
        label_ip=new JLabel(R.Strings.IP_MODEL);
        label_ip.setFont(font);
        label_ip.setBorder(BorderFactory.createEtchedBorder());
        label_ip.setForeground(Color.WHITE);
        label_ip.setBounds(50,0,200,33);
        label_ip.setHorizontalAlignment(JLabel.CENTER);


        //按钮
        button_commit=new JButton(R.Strings.SEND_BUTTON);
        button_commit.setForeground(Color.WHITE);
        button_commit.setFont(font);
        button_commit.setFocusPainted(false);
        button_commit.setContentAreaFilled(false);
        button_commit.setBounds(400,0,70,33);


    }

    @Override
    protected void addComponents() {
        this.add(label_ip);
        this.add(button_commit);





    }

    @Override
    protected void addListeners() {
        button_commit.addActionListener((ActionEvent e)->{
            int result= myJFileChooser.showOpenDialog(this);
            if(result==MyJFileChooser.APPROVE_OPTION){
                File selectedFile= myJFileChooser.getSelectedFile();

                //--test
                System.out.println(selectedFile.getAbsoluteFile());
                System.out.println(selectedFile.getName());
                System.out.println(selectedFile.length());
                //--test
                //开启文件传输线程--附带进度条面板
                InetSocketAddress address=new InetSocketAddress(this.label_ip.getText(),R.Configs.TCP_PORT);
                System.out.println(address.getAddress().getHostAddress());
                new SendThread(selectedFile,address).start();
            }
        });
    }
}
