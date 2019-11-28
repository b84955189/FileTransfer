package transfer.thread;

import resourses.R;
import transfer.data.Configs;
import transfer.view.dialogs.ProgressBarDialog;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class FileAcceptThread extends Thread{
    //接收文件目录
    private File fileDirectory;
    //接收文件
    private File thisFile;

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    //进度条
    private ProgressBarDialog progressBarDialog;
    public FileAcceptThread(Socket socket){
        this.socket=socket;

        fileDirectory=new File(Configs.getFileDiretory());
    }

    @Override
    public void run(){
        try {
            socket.setSoTimeout(0);
            byte[] buffer=new byte[10240];
            dataInputStream = new DataInputStream(socket.getInputStream());
            //获取文件名
            String fileName=dataInputStream.readUTF();
            //获取文件大小
            long size=dataInputStream.readLong();
            //接收进度条
            progressBarDialog=new ProgressBarDialog((int) size,false);
            //显示进度条弹窗
            progressBarDialog.setVisible(true);
            //输出文件路径+文件名
            thisFile=new File(fileDirectory,fileName);
            int fileCount=0;
            //判断文件名是否冲突
            while(thisFile.exists()){
                int position=fileName.lastIndexOf(".");
                String preFileName=fileName.substring(0,position);
                String latFileType=fileName.substring(position);//不用去除'.'了
                fileName=preFileName+"("+(++fileCount)+")"+latFileType;
                thisFile=new File(fileDirectory,fileName);

            }
            dataOutputStream=new DataOutputStream(new FileOutputStream(thisFile));
            int len;
            while((len=dataInputStream.read(buffer))!=-1){
                dataOutputStream.write(buffer,0,len);
                //设置进度条
                progressBarDialog.loadProgressBar(len);
            }
            progressBarDialog.dispose();

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            JOptionPane.showMessageDialog(null,R.Strings.DOWNLOAD_SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            //关闭进度条
            progressBarDialog.dispose();
            try {
                if(dataInputStream!=null)
                dataInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                if (dataOutputStream!=null)
                dataOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if(socket!=null)
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //报错
            JOptionPane.showMessageDialog(null, R.Strings.ERROR);
            //删除失败文件
            thisFile.delete();

        }
    }

}
