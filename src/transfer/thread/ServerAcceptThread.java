package transfer.thread;

import resourses.R;

import javax.swing.*;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAcceptThread extends Thread{
    private ServerSocket serverSocket;
    public ServerAcceptThread(ServerSocket serverSocket){
        this.serverSocket=serverSocket;

    }
    @Override
    public void run(){
        try {
            while (true) {
                Socket socket=serverSocket.accept();
                //-------------状态信息流


                DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
                int state=JOptionPane.showConfirmDialog(null,R.Strings.ACCEPT_TIPS+socket.getInetAddress().getHostAddress()+" 的文件？",R.Strings.TIP_TITLE,JOptionPane.OK_CANCEL_OPTION);

                if(state==JOptionPane.OK_OPTION) {
                    dataOutputStream.writeInt(1);
                    //启动接收线程
                    new FileAcceptThread(socket).start();
                }
                else
                    dataOutputStream.writeInt(0);

                //dataOutputStream.close();//---调用close关闭流会导致socket关闭
                //-------------状态信息流



            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, R.Strings.ERROR);
            this.run();
        }

    }

}
