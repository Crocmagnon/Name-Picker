package info.augendre.name_picker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by gaugendre on 18/04/2016 13:32.
 */
public class ChooseFileAction extends AbstractAction {
    private JFrame parent;
    private MainPanel mainPanel;

    public ChooseFileAction(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.parent = mainPanel.getMainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = null;
        FileDialog dialog;
        String mHomeDir = System.getProperty("user.home");

        dialog = new FileDialog(parent, "Save", FileDialog.LOAD);
        dialog.setDirectory(mHomeDir);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        if (dialog.getFile() != null) {
            mHomeDir = dialog.getDirectory();
            file = new File(mHomeDir + dialog.getFile());
        }

        if (file != null) {
            mainPanel.setNamesFile(file);
        }

    }
}
