package Gui.Component.AddRemove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JPanel
{
    private Listener listener;

    public Button(Listener listener)
    {
        this.listener = listener;
        setLayout(null);
        JButton buttonPlus = new JButton("+");
        JButton buttonLess = new JButton("-");
        JButton buttonOrder = new JButton("o");

        buttonPlus.setBorder(null);
        buttonPlus.setFont(buttonPlus.getFont().deriveFont(Font.BOLD));
        buttonPlus.setBackground(Color.blue);
        buttonPlus.setLocation(2, 2);
        buttonPlus.setSize(35, 30);
        buttonPlus.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                listener.actionPlusButton();
            }
        });

        buttonLess.setBorder(null);
        buttonLess.setFont(buttonLess.getFont().deriveFont(Font.BOLD));
        buttonLess.setLocation(40, 2);
        buttonLess.setSize(35, 30);
        buttonLess.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                listener.actionLessButton();
            }
        });

        buttonOrder.setBorder(null);
        buttonOrder.setFont(buttonLess.getFont().deriveFont(Font.BOLD));
        buttonOrder.setLocation(78, 2);
        buttonOrder.setSize(35, 30);
        buttonOrder.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                listener.actionOrderButton();
            }
        });

        add(buttonPlus);
        add(buttonLess);
        add(buttonOrder);
        setSize(100, 39);
    }
}