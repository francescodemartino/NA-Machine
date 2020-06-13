package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

abstract class DialogAction extends DialogBoxTable
{
    protected JTextField fieldState;
    protected JTextField fieldCharacter;
    protected JTextField fieldDirection;
    protected int locationTitleHorizontal = 265;
    private String title;

    public DialogAction(InstructionManager instructionManager, int row, String title)
    {
        super(instructionManager, row, title, 690, 215);
        this.title = title;
    }

    protected abstract String getValueInitialState();

    protected abstract String getValueInitialCharacter();

    protected abstract String getValueDirection();

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
        labelCharacter.setLocation(275, 75);
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
        fieldCharacter.setSize(100, 30);
        fieldCharacter.setLocation(340, 80);
        fieldCharacter.setHorizontalAlignment(JTextField.CENTER);
        add(fieldCharacter);

        JLabel labelDirection = new JLabel("Dir");
        labelDirection.setFont(new Font(name.getName(), Font.PLAIN, 15));
        labelDirection.setSize(60, 40);
        labelDirection.setLocation(505, 75);
        add(labelDirection);

        fieldDirection = new JTextField(getValueDirection());
        fieldDirection.addKeyListener(new KeyAdapterDialog()
        {
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                if(fieldDirection.getText().length() >= 1 || !(e.getKeyChar() == 'R' || e.getKeyChar() == 'L' || e.getKeyChar() == 'S'))
                {
                    e.consume();
                }
            }
        });
        fieldDirection.setSize(100, 30);
        fieldDirection.setLocation(560 - insets.right - insets.left, 80);
        fieldDirection.setHorizontalAlignment(JTextField.CENTER);
        add(fieldDirection);
    }

    protected abstract void actionBeforeDispose() throws IncorrectInputDataTableException;

    protected abstract void actionAfterDispose();

    @Override
    protected void actionOk()
    {
        if(fieldState.getText().trim().equals("") || fieldCharacter.getText().trim().equals("") || fieldDirection.getText().trim().equals(""))
        {
            openMessageError("ATTENZIONE! Il campo state, il campo tape o il campo dir non possono essere vuoti");
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

    protected Direction getDirection()
    {
        switch (fieldDirection.getText().trim())
        {
            case "R":
                return Direction.RIGHT;
            case "L":
                return Direction.LEFT;
            case "S":
                return Direction.STOP;
        }
        return Direction.RIGHT;
    }
}
