package Launcher;

import Gui.Body.BodyHandler;
import Gui.Body.Head.HeadHandler;
import Gui.Utility;
import Launcher.File.FileCreator;
import Launcher.File.FileLoader;
import Launcher.Listener.NewFileListener;
import Launcher.Listener.OpenFileListener;
import Launcher.Listener.OpenMenuListener;
import Launcher.Listener.SaveFileListener;
import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionManagerUpdateListener;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MdtNDFrame extends JFrame
{
    private SaveFileListener saveFileListener;
    private OpenFileListener openFileListener;
    private InstructionManagerUpdateListener instructionManagerUpdateListener;

    public MdtNDFrame(InstructionManager instructionManager)
    {
        super("NA-Machine");
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        setSize(HeadHandler.WIDTH, BodyHandler.HEIGHT);
        setListenerInstructionManagerUpdate();
        instructionManager.addListenerInstructionManagerUpdate(instructionManagerUpdateListener);
        openFileListener = new OpenFileListener(this, instructionManager, instructionManagerUpdateListener);
        createMenuBar();
        defineListenerWindowClosing();
        add(new BodyHandler(instructionManager));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void setListenerInstructionManagerUpdate()
    {
        instructionManagerUpdateListener = new InstructionManagerUpdateListener()
        {
            @Override
            protected void updateInternal()
            {
                setTitle("(Non salvato) " + getTitle());
            }

            @Override
            protected void resetInternal()
            {
                if(openFileListener.getFileOpen() == null)
                {
                    setTitle("NA-Machine");
                }
                else
                {
                    setTitle("NA-Machine - " + openFileListener.getFileOpen().getAbsolutePath());
                }
            }
        };
    }

    private void createMenuBar()
    {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.addMenuListener(new OpenMenuListener(this));
        JMenuItem newFile = new JMenuItem("Nuovo", new ImageIcon(Utility.class.getResource("/img/new.png")));
        JMenuItem openFile = new JMenuItem("Apri", new ImageIcon(Utility.class.getResource("/img/open.png")));
        openFile.addActionListener(openFileListener);
        JMenuItem saveFile = new JMenuItem("Salva", new ImageIcon(Utility.class.getResource("/img/save.png")));
        saveFileListener = new SaveFileListener(this, openFileListener, instructionManagerUpdateListener);
        saveFile.addActionListener(saveFileListener);
        openFileListener.addListenerSaveUnsavedFile(saveFileListener::save);
        newFile.addActionListener(new NewFileListener(this, saveFileListener, openFileListener, instructionManagerUpdateListener));
        menu.add(newFile);
        menu.add(openFile);
        menu.add(saveFile);
        mb.add(menu);
        setJMenuBar(mb);
    }

    private void defineListenerWindowClosing()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(instructionManagerUpdateListener.isNotSaved())
                {
                    int result = JOptionPane.showConfirmDialog(MdtNDFrame.this, "Vuoi salvare il file aperto?", "File non salvato", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(result == JOptionPane.YES_OPTION)
                    {
                        saveFileListener.save();
                    }
                    else if(result == JOptionPane.CLOSED_OPTION || result == JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                }
                e.getWindow().dispose();
            }
        });
    }

    public static void main(String[] args)
    {
        new MdtNDFrame(new InstructionManager());
    }
}
