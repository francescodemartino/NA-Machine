package Launcher.Listener;


import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class OpenMenuListener implements MenuListener
{
    private JFrame context;

    public OpenMenuListener(JFrame context)
    {
        this.context = context;
    }

    @Override
    public void menuSelected(MenuEvent e)
    {
        context.revalidate();
    }

    @Override
    public void menuDeselected(MenuEvent e)
    {
        context.revalidate();
    }

    @Override
    public void menuCanceled(MenuEvent e) {}
}