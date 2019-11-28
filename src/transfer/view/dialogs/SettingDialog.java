package transfer.view.dialogs;

import resourses.R;
import transfer.base.BaseDialog;
import transfer.data.Configs;
import transfer.thread.ExposeThread;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingDialog extends BaseDialog {
    private JLabel detectSettingTip;

    private ButtonGroup switchGroup;
    private JRadioButton exposeSwitchOn,exposeSwitchOff;
    private JButton explorerButton;
    private JButton applyButton,cancelButton;

    private JLabel fileDirectoryTip;
    private JTextField fileDirectory;

    //暴露线程
    private ExposeThread exposeThread;
    //--test
    public SettingDialog(){
        super();
    }
    //--test

    public SettingDialog(ExposeThread exposeThread){

        super();
        this.exposeThread=exposeThread;

    }
    @Override
    protected void initDialog() {


        this.setTitle(R.Strings.SETTING_MENU_ITEM);
        this.setSize(new Dimension(500,200));
        this.setLocationRelativeTo(null);



        super.initDialog();
    }

    @Override
    protected void initViews() {
        detectSettingTip=new JLabel(R.Strings.EXPOSE_SWITCH);
        detectSettingTip.setBounds(70,20,80,33);



        exposeSwitchOn=new JRadioButton(R.Strings.SWITCH_ON);
        exposeSwitchOff=new JRadioButton(R.Strings.SWITCH_OFF);
        //加载配置文件信息
        switch(Configs.getUdpSwitch()){
            case 0:exposeSwitchOn.setSelected(true);break;
            case 1:exposeSwitchOff.setSelected(true);break;
        }
        exposeSwitchOn.setBounds(150,20,50,33);
        exposeSwitchOff.setBounds(230,20,50,33);


        switchGroup=new ButtonGroup();
        switchGroup.add(exposeSwitchOn);
        switchGroup.add(exposeSwitchOff);

        //文件保存地址
        fileDirectoryTip=new JLabel(R.Strings.FILE_DIRECTORY_TIP);
        fileDirectoryTip.setBounds(70,60,100,33);
        fileDirectory=new JTextField(10);
        fileDirectory.setEditable(false);

        //加载配置文件信息
        fileDirectory.setText(Configs.getFileDiretory());
        fileDirectory.setBounds(170,65,230,25);

        //浏览按钮
        explorerButton=new JButton(R.Strings.EXPLORER_BUTTON);
        explorerButton.setBounds(400,60,60,33);

        //应用/取消按钮
        applyButton=new JButton(R.Strings.CONFIRM_BUTTON);
        applyButton.setBounds(360,125,60,33);
        cancelButton=new JButton(R.Strings.CANCEL_BUTTON);
        cancelButton.setBounds(430,125,60,33);



    }

    @Override
    protected void addComponents() {
        this.add(detectSettingTip);

        this.add(exposeSwitchOn);
        this.add(exposeSwitchOff);

        this.add(fileDirectoryTip);
        this.add(fileDirectory);;

        this.add(explorerButton);

        this.add(applyButton);
        this.add(cancelButton);
    }

    @Override
    protected void addListeners() {
        //浏览按钮
        explorerButton.addActionListener(e->{
            JFileChooser jFileChooser=new JFileChooser(fileDirectory.getSelectedText());
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setMultiSelectionEnabled(false);
            int result=jFileChooser.showOpenDialog(this);
            if(result==JFileChooser.APPROVE_OPTION){
                fileDirectory.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        //取消按钮
        cancelButton.addActionListener(e->{
            this.dispose();
        });
        //应用按钮
        applyButton.addActionListener(e->{



            //侦听单选按钮
            if(exposeSwitchOn.isSelected()){
                exposeThread.setSwitch(true);
                //更新配置信息
                Configs.setUdpSwitch(0);

            }else{
                exposeThread.setSwitch(false);
                //更新配置信息
                Configs.setUdpSwitch(1);


            }

            //文件保存目录
            String directory=fileDirectory.getText().trim();
            File  testFile=new File(directory);
            //判断输入是否正确-------这是没有想到用JFileChooser之前写的
            if(directory!=null&&!directory.equals("")&&testFile.exists()&&testFile.isDirectory()){
                //更新配置信息
                Configs.setFileDirectory(directory);
            }else{
                JOptionPane.showMessageDialog(null,R.Strings.DIRECTORY_ERROR,R.Strings.TIP_TITLE,JOptionPane.ERROR_MESSAGE);
            }


            //关闭GUI,释放资源
            this.dispose();
        });
    }
}
