package transfer.view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MyJFileChooser extends JFileChooser {
        public MyJFileChooser(){
            //--默认当前目录
            super(new File("."));
            //仅选择文件模式
            this.setFileSelectionMode(MyJFileChooser.FILES_ONLY);
            //不可多选
            this.setMultiSelectionEnabled(false);

        }
}
