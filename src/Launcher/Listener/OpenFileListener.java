package Launcher.Listener;

import Gui.Body.BodyHandler;
import Launcher.File.FileChooserMdtND;
import Launcher.File.FileLoader;
import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionManagerUpdateListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenFileListener implements ActionListener
{
    private JFrame context;
    private InstructionManager instructionManager;
    private InstructionManagerUpdateListener instructionManagerUpdateListener;
    private File fileChoose;
    private SaveUnsavedFileListener saveUnsavedFileListener;

    public OpenFileListener(JFrame context, InstructionManager instructionManager, InstructionManagerUpdateListener instructionManagerUpdateListener)
    {
        this.context = context;
        this.instructionManager = instructionManager;
        this.instructionManagerUpdateListener = instructionManagerUpdateListener;
    }

    public void addListenerSaveUnsavedFile(SaveUnsavedFileListener saveUnsavedFileListener)
    {
        this.saveUnsavedFileListener = saveUnsavedFileListener;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(saveUnsavedFileListener != null)
        {
            if(instructionManagerUpdateListener.isNotSaved())
            {
                int result = JOptionPane.showConfirmDialog(context, "Vuoi salvare il file aperto?", "File non salvato", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(result == JOptionPane.YES_OPTION)
                {
                    saveUnsavedFileListener.exe();
                }
            }
        }
        chooseFile();
    }

    public File getFileOpen()
    {
        return fileChoose;
    }

    public void setFileOpen(File fileOpen)
    {

        fileChoose = fileOpen;
    }

    public InstructionManager getInstructionManager()
    {
        return instructionManager;
    }

    public void setInstructionManager(InstructionManager instructionManager)
    {
        this.instructionManager = instructionManager;
    }

    private void chooseFile()
    {
        FileChooserMdtND fileChooser = new FileChooserMdtND();
        fileChooser.showOpenDialog(context);
        File file = fileChooser.getSelectedFile();
        if(file != null)
        {
            fileChoose = file;
            FileLoader fileLoader = new FileLoader(fileChoose);
            InstructionManager instructionManager = fileLoader.getInstructionManager();
            if(fileLoader.existsLastFileOpen())
            {
                this.instructionManager = instructionManager;
                instructionManager.addListenerInstructionManagerUpdate(instructionManagerUpdateListener);
                context.add(new BodyHandler(instructionManager), 0);
                context.setTitle("NA-Machine - " + fileChoose.getAbsolutePath());
            }
            else
            {
                JOptionPane.showMessageDialog(context, "Il file che hai aperto non sembra essere un file valido", "Attenzione", JOptionPane.UNDEFINED_CONDITION);
            }
        }
    }
}
