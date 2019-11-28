package resourses;

public class R {
    public interface Strings{
        //Frame
        String MAIN_TITLE="文件传输助手";


        //Menu
        String TOOL_MENU="工具";
        String DETECT_MENU_ITEM="网内侦测";
        String APPOINT_MENU_ITEM="指定传输";

        String SETTING_MENU_ITEM="设置";




        //JLabel
        String IP_MODEL="127.0.0.1";
        String DES_IP_TIP="目标IP：";
        String EXPOSE_SWITCH="暴露开关：";

        String FILE_DIRECTORY_TIP="文件保存地址：";

        String UPLOAD_TIP="不可关闭，正在上传···";
        String DOWNLOAD_TIP="不可关闭，正在下载···";
        String UPLOAD_SUCCESS="上传成功！";
        String DOWNLOAD_SUCCESS="下载成功！";

        String UPLOAD_DIALOG="上传进度";
        String DOWNLOAD_DIALOG="下载进度";

        //JButton
        String COMMIT_BUTTON="选择";
        String CONFIRM_BUTTON="确定";
        String CANCEL_BUTTON="取消";
        String SEND_BUTTON="发送";
        String EXPLORER_BUTTON="浏览";

        //JRadioButton
        String SWITCH_ON="开";
        String SWITCH_OFF="关";

        //JOptionPane
        String TIP_TITLE="Tips：";
        String NETWORK_CHANGE="网络已切换！";
        String NOT_NETWORK="请连接网络后使用！";
        String CONNECT_ERROR="连接异常，请检查网络后继续";
        String ERROR="接收失败";
        String PORT_ERROR="端口被占用/或已经运行！";
        String ACCEPT_TIPS="是否接收来自：";
        String STATE_TIP="目标拒绝接收了你的文件！";
        String IP_ERROR="请检查输入的目标IP是否正确，或者检查对方是否已经开启客户端！";
        String DIRECTORY_ERROR="请输入正确的文件保存路径！";
        String READ_CONFIG_ERROR="读取配置文件失败！";
        String DETECT_ZERO_TIP="暂时没有侦测到用户，请稍后刷新重试！";

        //JDialog
        String LOAD_DIALOG_TITLE="请稍后···";
        String LOAD_DIALOG_TIP="等待对方操作···";
        //Test
        String TEST_BUTTON="刷新";


    }
    public interface Fonts{
        String DEFAULT_FONT="微软雅黑";
    }
    public interface Pictures{
        String GRAY_BG="picture/gray_bg.png";
        String ICON="picture/icon.png";
        String MAIN_BG="picture/main_bg.png";
        String SEND_BUTTON_BG="picture/send_button_bg.png";
        String SEND_BUTTON_BG_COPY="picture/send_button_bg_copy.png";
        String DETECT_BG="picture/detect_bg.png";
        String CLOSE_ICON="picture/close_icon.png";
        String CLOSE_ICON_COPY="picture/close_icon_copy.png";
        String MINIMIZE_ICON="picture/minimize_icon.png";
    }
    public interface Configs{
        //配置文件名
        String CONFIG_FILE_NAME="config.cnf";
        //配置文件路径
        String CONFIG_FILE_DIRECTORY=".";

        int UDP_PORT=54188;
        int TCP_PORT=54199;

        //IP正则表达式
        String REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
           "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                   "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                   "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    }

}
