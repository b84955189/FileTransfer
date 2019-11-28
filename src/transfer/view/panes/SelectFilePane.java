package transfer.view.panes;

import jdk.nashorn.internal.scripts.JO;
import resourses.R;
import transfer.base.BasePane;
import transfer.thread.SendThread;
import transfer.utils.CommonUtils;
import transfer.view.dialogs.MyJFileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class SelectFilePane extends BasePane {


    //测试按钮
    private JButton sendButton;
    //文件选择器
    private MyJFileChooser myJFileChooser;
    @Override
    protected void initPane() {
        this.setLayout(null);
        //设置透明
        this.setOpaque(false);
    }

    @Override
    protected void initViews() {



            sendButton =new JButton();
            sendButton.setBounds(177,110,250,190);
            sendButton.setIcon(new ImageIcon(CommonUtils.getDIVImage(sendButton.getWidth(),sendButton.getHeight(),R.Pictures.SEND_BUTTON_BG)));
            sendButton.setContentAreaFilled(false);
            sendButton.setBorderPainted(false);

            myJFileChooser=new MyJFileChooser();
    }

    @Override
    protected void addComponents() {


            this.add(sendButton);
    }

    @Override
    protected void addListeners() {
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result=myJFileChooser.showOpenDialog(SelectFilePane.this);
                //判断是否选择文件
                if(result==MyJFileChooser.APPROVE_OPTION){
                    String tempDesIP=JOptionPane.showInputDialog(null,R.Strings.DES_IP_TIP,R.Strings.TIP_TITLE,JOptionPane.QUESTION_MESSAGE);
                    //利用了正则表达式校监输入IP字符串
                    if(tempDesIP==null){
                        //不处理
                    }else if(!tempDesIP.equals("")&&tempDesIP.matches(R.Configs.REGEX)){
                        //--test
                        System.out.println(tempDesIP);
                        //--test
                        File selectFile=myJFileChooser.getSelectedFile();

                        new SendThread(selectFile,new InetSocketAddress(tempDesIP,R.Configs.TCP_PORT)).start();
                    }else{
                        JOptionPane.showMessageDialog(null,R.Strings.IP_ERROR,R.Strings.TIP_TITLE, JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setIcon(new ImageIcon(CommonUtils.getDIVImage(sendButton.getWidth(),sendButton.getHeight(),R.Pictures.SEND_BUTTON_BG_COPY)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setIcon(new ImageIcon(CommonUtils.getDIVImage(sendButton.getWidth(),sendButton.getHeight(),R.Pictures.SEND_BUTTON_BG)));
            }
        });
        //按钮
        sendButton.addActionListener(e->{


        });

    }
}
