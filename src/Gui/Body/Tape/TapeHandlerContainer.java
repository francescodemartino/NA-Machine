package Gui.Body.Tape;

import Gui.Utility;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TapeHandlerContainer extends JPanel
{
    private InstructionManager instructionManager;
    private TapeLoaderThread tapeLoaderThread;
    private JLabel currentState;
    private JPanel statePanel;
    private JPanel whiteSpace;
    private JButton buttonEditTape;
    private JPanel containerButtonEditTape;
    private Tape tape;
    private TapeViewerFocusListener tapeViewerFocusListener;

    public TapeHandlerContainer(InstructionManager instructionManager, TapeLoaderThread tapeLoaderThread)
    {
        this.instructionManager = instructionManager;
        this.tapeLoaderThread = tapeLoaderThread;
        setLayout(new BorderLayout());
        defineElementInterface();
        createListener();
    }

    private void defineElementInterface()
    {
        currentState = new JLabel();
        currentState.setFont(new Font("Arial", Font.BOLD, 20));
        currentState.setBorder(new EmptyBorder(28, 15, 0, 0));

        statePanel = new JPanel();
        statePanel.setBorder(BorderFactory.createEmptyBorder(28, 0, 0, 0));
        statePanel.setMaximumSize(new Dimension(50, 60));
        statePanel.setLayout(new BorderLayout());
        statePanel.add(new StatePanel(Tape.STATE_IN_EXE, "q0"));

        containerButtonEditTape = new JPanel();
        containerButtonEditTape.setMaximumSize(new Dimension(40, 40));
        containerButtonEditTape.setLayout(new BorderLayout());
        containerButtonEditTape.setBorder(new EmptyBorder(21, 0, 0, 0));
        buttonEditTape = Utility.createButton("edit.png");
        buttonEditTape.addActionListener(e ->
        {
            DialogDefineCharactersTape dialogDefineCharactersTape = new DialogDefineCharactersTape(instructionManager, tape);
            dialogDefineCharactersTape.showDialog();
            //tapeLoaderThread.exe();
        });
        containerButtonEditTape.add(buttonEditTape);

        whiteSpace = new JPanel();
    }

    private void createListener()
    {
        tapeViewerFocusListener = () ->
        {
            currentState.setText(tape.getTapeViewer().getCurrentState());
            statePanel.removeAll();
            statePanel.add(new StatePanel(tape.getTapeViewer().getState(), tape.getTapeViewer().getCurrentState()));
            statePanel.revalidate();
        };
    }

    private void defineView()
    {
        Panel body = new Panel();
        GroupLayout groupLayout = new GroupLayout(body);
        body.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(currentState, 80, 81, 82)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(statePanel, 160, 161, 162)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(containerButtonEditTape, 40, 41, 42)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(whiteSpace, 20, 21, 22)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(tape.getTapeViewer().first())
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(whiteSpace, 20, 21, 22)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(currentState)
                                .addComponent(statePanel)
                                .addComponent(containerButtonEditTape)
                                .addComponent(whiteSpace)
                                .addComponent(tape.getTapeViewer().first())
                                .addComponent(whiteSpace)
                        )
        );

        removeAll();
        add(body);
    }

    public void setTape(Tape tape)
    {
        this.tape = tape;
        if(tape.getTapeViewer() != null)
        {
            tape.getTapeViewer().removeFocusListener();
        }
        tape.getTapeViewer().addFocusListener(tapeViewerFocusListener);
        tapeViewerFocusListener.update();
        defineView();
        revalidate();
    }

    public void setEnabled(boolean enabled)
    {
        buttonEditTape.setEnabled(enabled);
    }
}
