package Launcher.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooserMdtND extends JFileChooser
{
    public FileChooserMdtND()
    {
        FileNameExtensionFilter mdtndFilter = new FileNameExtensionFilter("Macchina di Turing non deterministica (*.mdtnd)", "mdtnd");
        
        setFileFilter(mdtndFilter);
    }

    @Override
    public void approveSelection()
    {
        File file = getSelectedFile();
        if(file.exists() && getDialogType() == SAVE_DIALOG)
        {
            int result = JOptionPane.showConfirmDialog(this, "Il file esiste già, vuoi sovrascriverlo?", "File esistente", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            switch(result)
            {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    }
}
