package Gui.Body.Head.Table;

import mdnd.Instruction.InstructionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class OpenErrorTable extends DialogBoxTable
{
    private boolean response;
    private String state;
    private char charTransaction;

    public OpenErrorTable(InstructionManager instructionManager, int row, String state, char charTransaction)
    {
        super(instructionManager, row,"Configurazione non acconsentita", 580, 215);
        this.state = state;
        this.charTransaction = charTransaction;
        response = false;
        okButtonText = "SI";
        cancelButtonText = "NO";
    }

    private void addListenerOk()
    {
        okButton.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if (e.getKeyChar() == 27)
                {
                    dispose();
                }
            }

            @Override
            public void keyPressed(KeyEvent e)
            {

            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
    }

    protected void createInterface(Insets insets)
    {
        super.createInterface(insets);

        addListenerOk();
        getRootPane().setDefaultButton(okButton);
        okButton.requestFocus();

        JLabel text = new JLabel("<html>Attenzione, lo state o il tape che hai inserito non sono presenti.<br>Vuoi che li aggiungo?</html>");
        text.setFont(new Font(text.getName(), Font.PLAIN, 20));
        text.setSize(560 - insets.right - insets.left, 50);
        text.setLocation(5, 40);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setVerticalAlignment(JLabel.CENTER);
        add(text);
    }

    @Override
    protected void actionOk()
    {
        response = true;
        instructionManager.addState(state);
        instructionManager.addChar(charTransaction);
        dispose();
    }

    public boolean getResponse()
    {
        return response;
    }
}
