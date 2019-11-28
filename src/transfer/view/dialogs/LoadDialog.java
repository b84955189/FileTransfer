package transfer.view.dialogs;

import resourses.R;
import transfer.base.BaseDialog;

import javax.swing.*;
import java.awt.*;

public class LoadDialog extends BaseDialog {
    private JLabel loadTip;
    @Override
    protected void initDialog() {
        super.initDialog();
        this.setTitle(R.Strings.LOAD_DIALOG_TITLE);
        this.setModal(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setSize(300,40);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        this.setBackground(new Color(0,0,0,0.30f));
        this.setDefaultCloseOperation(LoadDialog.DO_NOTHING_ON_CLOSE);

    }

    @Override
    protected void initViews() {
        Font font=new Font(R.Fonts.DEFAULT_FONT,Font.PLAIN,20);
        loadTip=new JLabel(R.Strings.LOAD_DIALOG_TIP);
        loadTip.setForeground(Color.WHITE);
        loadTip.setFont(font);

    }

    @Override
    protected void addComponents() {
            this.add(loadTip);
    }

    @Override
    protected void addListeners() {

    }
}
