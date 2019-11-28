package transfer.data;

public class Configs {
    //暴露开关，默认为开
    private static int udpSwitch=0;
    //默认保存地址为当前目录
    private static String fileDirectory=".";

    public static int getUdpSwitch() {
        return udpSwitch;
    }

    public static void setUdpSwitch(int udpSwitch) {
        Configs.udpSwitch = udpSwitch;
    }

    public static String getFileDiretory() {
        return fileDirectory;
    }

    public static void setFileDirectory(String fileDirectory) {
        Configs.fileDirectory = fileDirectory;
    }
    public static String toFile(){
        return udpSwitch+","+fileDirectory;
    }
    public static void fromFile(String config){
        String[] data=config.split(",");
        Configs.setUdpSwitch(Integer.parseInt(data[0]));
        Configs.setFileDirectory(data[1]);
    }
}
