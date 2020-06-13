package Gui.Body.Foot.Tapes;

import Gui.Body.Tape.TapeAdder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Visualizer extends JPanel
{
    private TapeAdder tapeAdder;
    private List<Tape> listTape;
    private TapesTable tapesTable;
    private JLabel labelState;
    private JTextField textFieldState;
    private List<TapeContainer> listTapeContainer;
    private TapesContainerTableManager tapesContainerTableManager;
    private JRadioButton radioButtonAll;
    private JRadioButton radioButtonActive;
    private JRadioButton radioButtonCloned;
    private JRadioButton radioButtonTerminated;
    private int stateTypology = 1;

    public Visualizer(TapeAdder tapeAdder)
    {
        this.tapeAdder = tapeAdder;
        listTape = new ArrayList<>();
        listTapeContainer = new ArrayList<>();
        tapesContainerTableManager = new TapesContainerTableManager();
        setLayout(new BorderLayout());
        tapesTable = new TapesTable(listTape, tapesContainerTableManager);
        labelState = new JLabel("Stato:");
        labelState.setFont(new Font("Arial", Font.BOLD, 18));
        textFieldState = new JTextField();
        defineRadioButtons();
        defineLayout();
        defineListenerTextField();
    }

    public void addTapes(List<Tape> listTape)
    {
        this.listTape.addAll(listTape);
        listTape.forEach((tape) ->
        {
            listTapeContainer.add(new TapeContainer(tape, tapeAdder));
        });
        tapesTable.refreshSize();
    }

    public void refreshTable()
    {
        tapesTable.refreshGUI();
    }

    public void reset()
    {
        tapesTable.setRowTable(0);
        listTape.clear();
        listTapeContainer.clear();
        tapesContainerTableManager.reset();
    }

    private void defineLayout()
    {
        JPanel body = new JPanel();
        GroupLayout groupLayout = new GroupLayout(body);
        body.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelState, 62, 63, 64)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(textFieldState, 120, 121, 122)
                                )
                                .addGap(50)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(radioButtonAll, 70, 71, 72)
                                )
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(radioButtonActive, 78, 79, 80)
                                )
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(radioButtonCloned, 90, 91, 92)
                                )
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(radioButtonTerminated, 110, 111, 112)
                                )
                        )
                        .addComponent(tapesTable)
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelState, 30, 31, 32)
                                .addComponent(textFieldState, 30, 31, 32)
                                .addComponent(radioButtonAll, 30, 31, 32)
                                .addComponent(radioButtonActive, 30, 31, 32)
                                .addComponent(radioButtonCloned, 30, 31, 32)
                                .addComponent(radioButtonTerminated, 30, 31, 32)
                        )
                        .addGap(10)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(tapesTable)
                        )
        );

        add(body);
    }

    private void defineListenerTextField()
    {
        tapesTable.defineFilter(new RowFilter<TableModel, Integer>()
        {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry)
            {
                boolean response;
                String state = textFieldState.getText().trim();
                if(state.equals(""))
                {
                    if (hasTapeContainerPassedFilterState(listTape.get(entry.getIdentifier())))
                    {
                        tapesContainerTableManager.addToPreemptiveList(listTapeContainer.get(entry.getIdentifier()));
                        response = true;
                    }
                    else
                    {
                        response = false;
                    }
                }
                else
                {
                    if(listTape.get(entry.getIdentifier()).getCurrentState().contains(state))
                    {
                        if (hasTapeContainerPassedFilterState(listTape.get(entry.getIdentifier())))
                        {
                            tapesContainerTableManager.addToPreemptiveList(listTapeContainer.get(entry.getIdentifier()));
                            response = true;
                        }
                        else
                        {
                            response = false;
                        }
                    }
                    else
                    {
                        response = false;
                    }
                }
                if(entry.getIdentifier() == (listTape.size() - 1))
                {
                    tapesContainerTableManager.doSwitch();
                }
                return response;
            }
        });
        textFieldState.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                tapesTable.refreshGUI();
            }
        });
    }

    private boolean hasTapeContainerPassedFilterState(Tape tape)
    {
        switch (stateTypology)
        {
            case 1:
                return true;
            case 2:
                return tape.getState() == Tape.STATE_IN_EXE;
            case 3:
                return tape.getState() == Tape.STATE_CLONED;
            case 4:
                return tape.getState() == Tape.STATE_NOT_NEXT_STATE || tape.getState() == Tape.STATE_NOT_CORRECT_POSITION;
        }
        return true;
    }

    private void defineRadioButtons()
    {
        radioButtonAll = new JRadioButton("Tutti");
        radioButtonActive = new JRadioButton("Attivo");
        radioButtonCloned = new JRadioButton("Clonato");
        radioButtonTerminated = new JRadioButton("Terminato");

        ButtonGroup groupStateTypology = new ButtonGroup();
        groupStateTypology.add(radioButtonAll);
        groupStateTypology.add(radioButtonActive);
        groupStateTypology.add(radioButtonCloned);
        groupStateTypology.add(radioButtonTerminated);

        radioButtonAll.setSelected(true);

        radioButtonAll.addActionListener(e ->
        {
            stateTypology = 1;
            tapesTable.refreshGUI();
        });
        radioButtonActive.addActionListener(e ->
        {
            stateTypology = 2;
            tapesTable.refreshGUI();
        });
        radioButtonCloned.addActionListener(e ->
        {
            stateTypology = 3;
            tapesTable.refreshGUI();
        });
        radioButtonTerminated.addActionListener(e ->
        {
            stateTypology = 4;
            tapesTable.refreshGUI();
        });
    }
}
