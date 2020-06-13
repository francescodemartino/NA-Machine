package Gui.Component.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

abstract public class DialogBox extends JDialog
{
    protected JButton okButton;
    protected String okButtonText;
    protected  String cancelButtonText;

    public DialogBox(String title, int width, int height)
    {
        setTitle(title);
        setLayout(null);
        setSize(width,height);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        addKeyListener();
        okButtonText = "OK";
        cancelButtonText = "Cancel";
    }

    public void showDialog()
    {
        addComponentListener(new ComponentAdapter()
        {
            public void componentShown(ComponentEvent e)
            {
                Insets insets = DialogBox.this.getInsets();
                createInterface(insets);
                repaint();
            }
        });
        setVisible(true);
    }

    private void addKeyListener()
    {
        addKeyListener(new KeyListener()
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

    protected void openMessageError(String message)
    {
        if(message.equals("-1"))
        {
            message = "ATTENZIONE! I dati inseriti non sono validi";
        }
        JOptionPane.showMessageDialog(this, message, "Segnalazione", JOptionPane.UNDEFINED_CONDITION);
    }

    protected void createInterface(Insets insets)
    {
        okButton = new JButton(okButtonText);
        okButton.setBackground(Color.blue);
        okButton.addActionListener(getActionListenerOk());
        okButton.setSize(100, 30);
        okButton.setLocation(getWidth() - 249 - insets.right - insets.left, 175 - insets.top - insets.bottom);
        add(okButton);

        JButton cancelButton = new JButton(cancelButtonText);
        cancelButton.addActionListener(getActionListenerClose());
        cancelButton.setSize(100, 30);
        cancelButton.setLocation(getWidth() - 129 - insets.right - insets.left, 175 - insets.top - insets.bottom);
        add(cancelButton);
    }

    abstract protected void actionOk();

    private ActionListener getActionListenerOk()
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionOk();
            }
        };
    }

    private ActionListener getActionListenerClose()
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        };
    }

    public class KeyAdapterDialog extends KeyAdapter
    {
        public void keyTyped(KeyEvent e)
        {
            if (e.getKeyChar() == '\n')
            {
                okButton.doClick();
            }
            else if (e.getKeyChar() == 27)
            {
                dispose();
            }
        }
    }
}
