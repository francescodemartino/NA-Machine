package Gui.Body.Head;

import Gui.Component.AddRemove.Button;
import Gui.Component.AddRemove.Listener;
import Gui.Body.Head.Adder.Box;
import Gui.Body.Head.Adder.Character;
import Gui.Body.Head.Adder.State;
import Gui.Body.Head.Table.ActionRefresh;
import Gui.Body.Head.Table.Table;
import mdnd.Instruction.InstructionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HeadHandler extends JPanel
{
    public static final int WIDTH = 1400;

    private InstructionManager instructionManager;
    private Box stateBox;
    private Box charBox;

    public HeadHandler(InstructionManager instructionManager)
    {
        this.instructionManager = instructionManager;
        configureContainerHandler();
    }

    private void configureContainerHandler()
    {
        Table table = new Table(instructionManager);

        table.addListenerActionRefresh(new ActionRefresh()
        {
            @Override
            public void doRefresh()
            {
                stateBox.refreshGUI();
                charBox.refreshGUI();
            }
        });

        Listener listenerAddRemove = new Listener()
        {
            @Override
            protected void actionPlusButton()
            {
                table.addInstruction();
            }

            @Override
            protected void actionLessButton()
            {
                table.removeInstruction();
            }

            @Override
            protected void actionOrderButton()
            {
                instructionManager.sortInstruction();
                table.refreshGUI();
            }
        };

        Panel panelTableAdder = new Panel();
        GroupLayout groupLayout = new GroupLayout(panelTableAdder);
        panelTableAdder.setLayout(groupLayout);

        Button buttonAdder = new Button(listenerAddRemove);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(table)
                                .addComponent(buttonAdder)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(table)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(buttonAdder, 33, 34, 35)
                        )
        );

        Panel panelAdder = new Panel(new GridLayout(2,1));
        stateBox = new State(instructionManager);
        panelAdder.add(stateBox);
        charBox = new Character(instructionManager);
        panelAdder.add(charBox);

        JSplitPane jSplitPaneVertical = new JSplitPane(SwingConstants.VERTICAL, panelTableAdder, panelAdder);
        jSplitPaneVertical.setEnabled(false);
        jSplitPaneVertical.setDividerLocation(WIDTH - 200);


        addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent e)
            {
                if(getWidth() > 650)
                {
                    jSplitPaneVertical.setDividerLocation(getWidth() - 200);
                }
            }
        });

        setLayout(new BorderLayout());
        add(jSplitPaneVertical);
    }
}
