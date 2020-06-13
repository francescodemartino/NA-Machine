package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Exception.InstructionAlreadyExistsException;
import mdnd.Instruction.InstructionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class DialogUpdateDescription extends DialogBoxTable
{
    private JTextField fieldState;

    public DialogUpdateDescription(InstructionManager instructionManager, int row)
    {
        super(instructionManager, row, "Modifica descrizione", 560, 215);
    }

    @Override
    protected void createInterface(Insets insets)
    {
        super.createInterface(insets);

        JLabel name = new JLabel("Modifica descrizione");
        name.setFont(new Font(name.getName(), Font.PLAIN, 22));
        name.setSize(250, 20);
        name.setLocation(170, 30);
        add(name);

        fieldState = new JTextField(instruction.getDescription());
        fieldState.addKeyListener(new KeyAdapterDialog());
        fieldState.setSize(500 - insets.left - insets.right, 30);
        fieldState.setLocation(30, 80);
        addWindowListener( new WindowAdapter()
        {
            public void windowOpened( WindowEvent e )
            {
                fieldState.requestFocus();
            }
        });
        add(fieldState);
    }

    @Override
    protected void actionOk()
    {
        try
        {
            instructionManager.updateInstruction(instruction.getInitialState(), instruction.getInitialCharacter(), instruction.getTransitionState(), instruction.getTransitionCharacter(), instruction.getTransitionDirection(), fieldState.getText().trim(), instruction, false);
        }
        catch (IncorrectInputDataTableException e)
        {
        }
        catch (InstructionAlreadyExistsException e)
        {
        }
        dispose();
    }
}
