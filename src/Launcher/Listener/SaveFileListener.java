package Launcher.Listener;

import Gui.Body.BodyHandler;
import Launcher.File.FileChooserMdtND;
import Launcher.File.FileCreator;
import Launcher.File.FileLoader;
import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionManagerUpdateListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveFileListener implements ActionListener
{
    private JFrame context;
    private OpenFileListener openFileListener;
    private InstructionManagerUpdateListener instructionManagerUpdateListener;

    public SaveFileListener(JFrame context, OpenFileListener openFileListener, InstructionManagerUpdateListener instructionManagerUpdateListener)
    {
        this.context = context;
        this.openFileListener = openFileListener;
        this.instructionManagerUpdateListener = instructionManagerUpdateListener;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        save();
    }

    public void save()
    {
        FileCreator fileCreator = new FileCreator(context, openFileListener.getInstructionManager(), openFileListener.getFileOpen());
        File file = fileCreator.saveFile();
        if(fileCreator.isSaved())
        {
            openFileListener.setFileOpen(file);
            instructionManagerUpdateListener.reset();
        }
    }
}
