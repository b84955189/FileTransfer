package transfer.view.panes;

import resourses.R;
import transfer.base.BasePane;
import transfer.data.DataList;
import transfer.utils.CommonUtils;
import transfer.view.components.DetectBeanView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;


public class DetectPane extends BasePane {
    private JScrollPane scrollPane;
    //线程锁
    private Object lock;
    private Runnable refresh;

    private JButton testButton;

    private Thread thread;

    //盒子布局
    private Box verticalBox;

    public DetectPane(){
        super();
        //开启线程显示侦测信息--防止卡顿主线程
        new Thread(refresh).start();




    }
    /*
    初始化线程锁及线程操作对象
     */
    private void initThreadLock(){
        lock=new Object();
        refresh=new Runnable(){
            public void run(){

                //加锁
                synchronized (lock) {
                    verticalBox.removeAll();
                    verticalBox.validate();
                    verticalBox.repaint();
                    scrollPane.validate();

                    DetectPane.this.showExposeUser();
                    //DataList.detectList.clear();
                }
            }
        };
    }
    @Override
    protected void initPane() {
        this.setLayout(new BorderLayout());
        //设置透明
        this.setOpaque(false);

        //初始化线程锁与线程对象
        initThreadLock();
    }

    @Override
    protected void initViews() {



        verticalBox=Box.createVerticalBox();




        scrollPane=new JScrollPane();
        scrollPane.setBorder(null);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(verticalBox);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);




        testButton=new JButton(R.Strings.TEST_BUTTON);
        testButton.setForeground(Color.white);
        testButton.setFocusPainted(false);
        testButton.setFont(CommonUtils.getDefaultFont(15));
        testButton.setContentAreaFilled(false);



    }

    @Override
    protected void addComponents() {
        this.add(scrollPane,BorderLayout.CENTER);
        //--test
        this.add(testButton,BorderLayout.SOUTH);
        //--test
    }
    /*
    显示暴露用户
     */
    private void showExposeUser(){
        //遍历集合数据
        for(String ipTemp: DataList.detectList){
            DetectBeanView detectBeanViewTemp=new DetectBeanView(ipTemp);
            detectBeanViewTemp.setPreferredSize(new Dimension(560,33));
            detectBeanViewTemp.setMaximumSize(new Dimension(560,33));
            detectBeanViewTemp.setMinimumSize(new Dimension(560,33));
            verticalBox.add(detectBeanViewTemp);
            verticalBox.add(Box.createVerticalStrut(3));
            verticalBox.validate();
            scrollPane.validate();
        }
        if(DataList.detectList.size()==0)
            JOptionPane.showMessageDialog(null,R.Strings.DETECT_ZERO_TIP,R.Strings.TIP_TITLE,JOptionPane.PLAIN_MESSAGE);
    }
    @Override
    protected void addListeners() {



        //--test
        testButton.addActionListener((ActionEvent e)->{
            //开启子线程防止卡顿主线程-影响用户体验

        if(thread==null){
            thread=new Thread(refresh);
            thread.start();
        }else if(!thread.isAlive()){
                thread=new Thread(refresh);
                thread.start();
            } else{
            //不处理
        }


        });


        //--test
    }

}


