package Gui.Body.Tape;

import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TapeHandler extends JPanel implements TapeAdder
{
    private TapeHandlerContainer tapeHandlerContainer;
    private InstructionManager instructionManager;
    private TapeLoaderThread tapeLoaderThread;

    public TapeHandler(InstructionManager instructionManager, TapeLoaderThread tapeLoaderThread)
    {
        this.instructionManager = instructionManager;
        this.tapeLoaderThread = tapeLoaderThread;
        configureLayout();
        tapeHandlerContainer = new TapeHandlerContainer(instructionManager, tapeLoaderThread);
        add(tapeHandlerContainer);
    }

    @Override
    public void viewTape(Tape tape)
    {
        TapeViewer tapeViewer = tape.getTapeViewer();
        tapeViewer.first().setBorder(new EmptyBorder(20, 0, 0, 0));
        tapeHandlerContainer.setTape(tape);
        tapeViewer.goToEndOfTableIfNecessary();
    }

    public void setEnabled(boolean enabled)
    {
        tapeHandlerContainer.setEnabled(enabled);
    }

    private void configureLayout()
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }
}