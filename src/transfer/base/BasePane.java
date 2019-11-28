package transfer.base;

import javax.swing.*;

public abstract class BasePane extends JPanel {
    public BasePane(){
        this.initPane();
        this.initViews();
        this.addComponents();
        this.addListeners();
    }
    //初始化面板
    protected abstract void initPane();
    //初始化组件
    protected abstract void initViews();
    //添加组件
    protected abstract void addComponents();
    //添加监听
    protected abstract void addListeners();

}
