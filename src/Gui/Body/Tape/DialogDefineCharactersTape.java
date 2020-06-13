package Gui.Body.Tape;

import Gui.Component.Dialog.DialogBox;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogDefineCharactersTape extends DialogBox
{
    private InstructionManager instructionManager;
    private Tape tape;
    private JTextField fieldTape;

    public DialogDefineCharactersTape(InstructionManager instructionManager, Tape tape)
    {
        super("Modifica nastro", 550, 215);
        this.instructionManager = instructionManager;
        this.tape = tape;
    }

    @Override
    protected void createInterface(Insets insets)
    {
        super.createInterface(insets);
        fieldTape = new JTextField(getTextTape());
        fieldTape.selectAll();
        fieldTape.addKeyListener(new KeyAdapterDialog());
        fieldTape.setSize(530 - insets.left - insets.right, 40);
        fieldTape.setLocation(10, 55);
        addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {
                fieldTape.requestFocus();
            }
        });
        add(fieldTape);
    }

    private String getTextTape()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : tape.getCharacters())
        {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void actionOk()
    {
        String textTape = fieldTape.getText().trim();
        if(isTapeAllowed(textTape))
        {
            tape.changeContentTape(textTape);
            dispose();
        }
        else
        {
            openMessageError("Utilizzo di caratteri non definiti");
        }
    }

    private boolean isTapeAllowed(String tape)
    {
        for(int i=0; i<tape.length(); i=i+1)
        {
            if(!instructionManager.hasCharacter(tape.charAt(i)))
            {
                return false;
            }
        }
        return  true;
    }
}
