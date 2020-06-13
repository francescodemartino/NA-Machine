package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

abstract class DialogConfiguration extends DialogBoxTable
{
    protected JTextField fieldState;
    protected JTextField fieldCharacter;
    protected int locationTitleHorizontal = 160;
    private String title;

    public DialogConfiguration(InstructionManager instructionManager, int row, String title)
    {
        super(instructionManager, row, title, 560, 215);
        this.title = title;
    }

    protected abstract String getValueInitialState();

    protected abstract String getValueInitialCharacter();

    @Override
    protected void createInterface(Insets insets)
    {
        super.createInterface(insets);

        JLabel name = new JLabel(title);
        name.setFont(new Font(name.getName(), Font.PLAIN, 22));
        name.setSize(600, 50);
        name.setLocation(locationTitleHorizontal, 10);
        add(name);

        JLabel labelState = new JLabel("State");
        labelState.setFont(new Font(name.getName(), Font.PLAIN, 15));
        labelState.setSize(60, 40);
        labelState.setLocation(20, 75);
        add(labelState);

        fieldState = new JTextField(getValueInitialState());
        fieldState.addKeyListener(new KeyAdapterDialog());
        fieldState.setSize(150, 30);
        fieldState.setLocation(80, 80);
        fieldState.setHorizontalAlignment(JTextField.CENTER);
        addWindowListener( new WindowAdapter()
        {
            public void windowOpened( WindowEvent e )
            {
                fieldState.requestFocus();
            }
        });
        add(fieldState);

        JLabel labelCharacter = new JLabel("Tape");
        labelCharacter.setFont(new Font(name.getName(), Font.PLAIN, 15));
        labelCharacter.setSize(60, 40);
        labelCharacter.setLocation(315, 75);
        add(labelCharacter);

        fieldCharacter = new JTextField(getValueInitialCharacter());
        fieldCharacter.addKeyListener(new KeyAdapterDialog()
        {
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                if (fieldCharacter.getText().length() >= 1)
                {
                    e.consume();
                }
            }
        });
        fieldCharacter.setSize(150, 30);
        fieldCharacter.setLocation(380 - insets.right - insets.left, 80);
        fieldCharacter.setHorizontalAlignment(JTextField.CENTER);
        add(fieldCharacter);
    }

    protected abstract void actionBeforeDispose() throws IncorrectInputDataTableException;

    protected abstract void  actionAfterDispose();

    @Override
    protected void actionOk()
    {
        if(fieldState.getText().trim().equals("") || fieldCharacter.getText().trim().equals(""))
        {
            openMessageError("ATTENZIONE! Il campo state o il campo tape non possono essere vuoti");
        }
        else
        {
            try
            {
                actionBeforeDispose();
                dispose();
                actionAfterDispose();
            }
            catch (IncorrectInputDataTableException error)
            {
                if(error.getMessage().equals("Incorrect first char"))
                {
                    openMessageError("ATTENZIONE! In presenda di " + Tape.FIRST_CHAR + " deve obbligatoriamente essere presente la direzione destra");
                }
                else if(error.getMessage().equals("Incorrect state or character"))
                {
                    OpenErrorTable openErrorTable = new OpenErrorTable(instructionManager, row, fieldState.getText().trim(), fieldCharacter.getText().trim().charAt(0));
                    openErrorTable.showDialog();
                    if(openErrorTable.getResponse())
                    {
                        actionOk();
                    }
                }
            }
        }
    }
}
