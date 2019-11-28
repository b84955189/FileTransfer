package transfer.thread;

import resourses.R;
import sun.awt.windows.ThemeReader;
import transfer.view.dialogs.LoadDialog;
import transfer.view.dialogs.ProgressBarDialog;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendThread extends Thread{
    //状态码
    private static int RESEPONSE_OK=1;

    private File selectedFile;

    private Socket socket;
    private DataOutputStream dataOutputStream;

    private DataInputStream dataInputStream,stateInputStream;
    private InetSocketAddress address;

    //进度条
    private ProgressBarDialog progressBarDialog;

    //等待操作框
    private LoadDialog loadDialog;
    public SendThread(File selectedFile, InetSocketAddress address){
        super();
        this.selectedFile=selectedFile;
        this.address=address;
        socket=new Socket();





    }

    @Override
    public void run(){
        try {
            byte[] buffer=new byte[10240];
            //0-为无限等待
           socket.setSoTimeout(0);
            //TCP连接--设置了连接超时
            socket.connect(address,3000);
            //接收目标状态信息流
            stateInputStream=new DataInputStream(socket.getInputStream());
            loadDialog=new LoadDialog();
            loadDialog.setVisible(true);
            int responseCode=stateInputStream.readInt();
            loadDialog.dispose();
            //判断是否可发送
            if(responseCode==RESEPONSE_OK) {
                //设置进度条最大值--file.length--字节
                long size=selectedFile.length();//--->暂不将字节转为M
                //--test
                System.out.println(size+":"+(int)size);
                //--test
                //发送进度条
                progressBarDialog=new ProgressBarDialog((int)size,true);
                //显示进度条弹窗
                progressBarDialog.setVisible(true);
                //本机文件流
                dataInputStream = new DataInputStream(new FileInputStream(selectedFile));

                //文件发送流
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                //发送文件名
                dataOutputStream.writeUTF(selectedFile.getName());
                //文件大小
                dataOutputStream.writeLong(size);
                int len=0;
                while ((len = dataInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, len);
                    //设置进度条
                    progressBarDialog.loadProgressBar(len);
                    //System.out.println(len/size*100);

                }
                progressBarDialog.dispose();


                dataInputStream.close();
                dataOutputStream.close();
                JOptionPane.showMessageDialog(null,R.Strings.UPLOAD_SUCCESS);

            }else {
                //关闭状态输入流-----注意，此处会关闭socket
               stateInputStream.close();

               JOptionPane.showMessageDialog(null,R.Strings.STATE_TIP,R.Strings.TIP_TITLE,JOptionPane.PLAIN_MESSAGE);

            }
            socket.close();






        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, R.Strings.CONNECT_ERROR);
            //关闭进度条
            progressBarDialog.dispose();
            if(loadDialog!=null)
            loadDialog.dispose();
        }
    }
}
