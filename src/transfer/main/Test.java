package transfer.main;

import transfer.utils.CommonUtils;
import transfer.view.dialogs.LoadDialog;
import transfer.view.dialogs.ProgressBarDialog;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class Test{
    static int i=0;
    public static void main(String[] args) throws UnknownHostException, SocketException {
                System.out.println(CommonUtils.getIPV4Address());
                //new SettingDialog().setVisible(true);
               // JOptionPane.showConfirmDialog(null,"是否？","Tips",JOptionPane.OK_CANCEL_OPTION);
//                System.out.println(dsf.sd);
//                dsf.sd="么么大";
//                System.out.println(dsf.sd);
//                ProgressBarDialog progressBarDialog=new ProgressBarDialog(12,true);
//                progressBarDialog.setVisible(true);
//        Timer timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                progressBarDialog.loadProgressBar(23);
//            }
//        },0,1000);
        //System.out.println((int)(/1024.0*100));
        new LoadDialog().setVisible(true);

















    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }


    private static InetAddress getLocalHostAddress() throws UnknownHostException {
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        if (ip != null && ip instanceof InetAddress) {
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
        if (jdkSuppliedAddress == null) {
            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
        }
        return jdkSuppliedAddress;
    }

}
