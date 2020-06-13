package Gui.Body.Head.Adder;

import Gui.Component.Dialog.DialogBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogAddElement extends DialogBox
{
    public static int INFINITY_LENGTH_JTEXTFIELD = -1;

    private JTextField fieldElement;
    private JTextFieldLister jTextFieldLister;
    private int limitLengthJTextField;

    public DialogAddElement(String title, JTextFieldLister jTextFieldLister, int limitLengthJTextField)
    {
        super(title, 450, 215);
        this.jTextFieldLister = jTextFieldLister;
        this.limitLengthJTextField = limitLengthJTextField;
    }

    @Override
    protected void createInterface(Insets insets)
    {
        super.createInterface(insets);

        JLabel name = new JLabel("Aggiungi");
        name.setFont(new Font(name.getName(), Font.PLAIN, 22));
        name.setSize(600, 50);
        name.setLocation(180, 10);
        add(name);

        fieldElement = new JTextField("");
        fieldElement.addKeyListener(new KeyAdapterDialog()
        {
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                if (limitLengthJTextField != -1 && fieldElement.getText().length() >= limitLengthJTextField)
                {
                    e.consume();
                }
            }
        });
        fieldElement.setSize(390 - insets.left - insets.right, 30);
        fieldElement.setLocation(30, 80);
        addWindowListener( new WindowAdapter()
        {
            public void windowOpened( WindowEvent e )
            {
                fieldElement.requestFocus();
            }
        });
        add(fieldElement);
    }

    @Override
    protected void actionOk()
    {
        if(fieldElement.getText().trim().equals(""))
        {
            openMessageError("Il campo di inserimento non può essere vuoto");
        }
        else
        {
            jTextFieldLister.exeAction(fieldElement.getText().trim());
            dispose();
        }
    }
}
