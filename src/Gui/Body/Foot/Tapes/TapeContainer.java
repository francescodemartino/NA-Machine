package Gui.Body.Foot.Tapes;

import Gui.Body.Tape.StatePanel;
import Gui.Body.Tape.TapeAdder;
import Gui.Body.Tape.TapeViewerFocusListener;
import Gui.Utility;
import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TapeContainer extends JPanel
{
    private Tape tape;
    private TapeAdder tapeAdder;
    private JPanel whiteSpace;
    private JPanel containerButtonOpenTape;
    private JButton buttonOpenTape;
    private JLabel currentState;
    private JPanel statePanel;

    public TapeContainer(Tape tape, TapeAdder tapeAdder)
    {
        this.tape = tape;
        this.tapeAdder = tapeAdder;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(9, 0, 0, 0));
        whiteSpace = new JPanel();
        containerButtonOpenTape = new JPanel();
        containerButtonOpenTape.setMaximumSize(new Dimension(40, 40));
        containerButtonOpenTape.setLayout(new BorderLayout());
        containerButtonOpenTape.setBorder(new EmptyBorder(1, 0, 0, 0));
        buttonOpenTape = Utility.createButton("link.png");
        containerButtonOpenTape.add(buttonOpenTape);
        currentState = new JLabel();
        currentState.setLayout(new BorderLayout());
        currentState.setFont(new Font("Arial", Font.BOLD, 20));
        currentState.setBorder(new EmptyBorder(10, 15, 0, 0));
        statePanel = new JPanel();
        statePanel.setLayout(new BorderLayout());
        statePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        statePanel.setMaximumSize(new Dimension(50, 40));
        defineLayout();
        defineListener();
    }

    private void defineLayout()
    {
        JPanel body = new JPanel();
        GroupLayout groupLayout = new GroupLayout(body);
        body.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(currentState, 80, 81, 82)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(statePanel, 120, 121, 122)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(whiteSpace, 20, 21, 22)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(containerButtonOpenTape, 40, 41, 42)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(whiteSpace, 20, 21, 22)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(tape.getTapeViewer().second())
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
                                .addComponent(whiteSpace)
                                .addComponent(containerButtonOpenTape)
                                .addComponent(whiteSpace)
                                .addComponent(tape.getTapeViewer().second(), 40, 41, 42)
                                .addComponent(whiteSpace)
                        )
        );

        add(body);
    }

    private void defineListener()
    {
        TapeViewerFocusListener tapeViewerFocusListener = () ->
        {
            statePanel.removeAll();
            StatePanel statePanelReal = new StatePanel(tape.getTapeViewer().getState(), tape.getTapeViewer().getCurrentState());
            statePanel.add(statePanelReal);
            statePanel.revalidate();
            currentState.setText(tape.getTapeViewer().getCurrentState());
        };
        tape.getTapeViewer().addFocusTableListener(tapeViewerFocusListener);
        tapeViewerFocusListener.update();

        buttonOpenTape.addActionListener(e ->
        {
            tapeAdder.viewTape(tape);
        });
    }
}
