package transfer.base;

import javax.swing.*;
import java.awt.*;

public abstract class BaseDialog extends JDialog {

    public BaseDialog(){
        super();
        this.initDialog();
        this.initViews();
        this.addComponents();
        this.addListeners();
    }
    //初始化JDialog
    protected  void initDialog(){

        this.setAlwaysOnTop(true);
        this.setModal(true);
        this.setLayout(null);
        this.setResizable(false);

        this.setDefaultCloseOperation(BaseDialog.DISPOSE_ON_CLOSE);
    };
    //初始化组件
    protected abstract void initViews();
    //添加组件
    protected abstract void addComponents();
    //添加监听
    protected abstract void addListeners();
}
