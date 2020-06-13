package Launcher.Listener;

import Gui.Body.BodyHandler;
import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionManagerUpdateListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFileListener implements ActionListener
{
    private JFrame context;
    private SaveFileListener saveFileListener;
    private OpenFileListener openFileListener;
    private InstructionManagerUpdateListener instructionManagerUpdateListener;

    public NewFileListener(JFrame context, SaveFileListener saveFileListener, OpenFileListener openFileListener, InstructionManagerUpdateListener instructionManagerUpdateListener)
    {
        this.context = context;
        this.saveFileListener = saveFileListener;
        this.openFileListener = openFileListener;
        this.instructionManagerUpdateListener = instructionManagerUpdateListener;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(instructionManagerUpdateListener.isNotSaved())
        {
            int result = JOptionPane.showConfirmDialog(context, "Vuoi salvare il file aperto?", "File non salvato", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.YES_OPTION)
            {
                saveFileListener.save();
            }
            else if (result == JOptionPane.CLOSED_OPTION || result == JOptionPane.CANCEL_OPTION)
            {
                return;
            }
        }
        openFileListener.setFileOpen(null);
        openFileListener.setInstructionManager(new InstructionManager());
        openFileListener.getInstructionManager().addListenerInstructionManagerUpdate(instructionManagerUpdateListener);
        context.add(new BodyHandler(openFileListener.getInstructionManager()), 0);
        context.setTitle("NA-Machine");
    }
}
