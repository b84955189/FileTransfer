package transfer.thread;
/*
    侦测线程
 */

import transfer.data.DataList;
import transfer.utils.CommonUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DetectThread extends Thread{
    private DatagramSocket socket;
    //启动标记---默认启动
    private boolean sign=true;
    public DetectThread(DatagramSocket socket){
        this.socket=socket;
    }

    @Override
    public void run(){
        try {
            while (sign) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 1024);
                socket.receive(packet);
                String temp = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                //如果检测到暴露线程
                if(temp.equals("expose")){
                    String strIP=packet.getAddress().getHostAddress();

                    //如果集合内不包含此ip，则添加至数据集合
                    if(!DataList.detectList.contains(strIP)&&!strIP.equals(CommonUtils.getIPV4Address().getHostAddress()))
                        DataList.detectList.add(strIP);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /*
    线程开关--与暴露线程一致
     */
    public void setSwitch(boolean sign){
        this.sign=sign;
        if(sign){
            if(this.isAlive()){
                //不做处理

            }else{
                this.start();
            }
        }
    }


}
