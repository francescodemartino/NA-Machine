package Gui.Body.Foot.Tendency;

import Gui.Body.Tape.Circle;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
    Step: value

    Numero nastri: value

    Nastri attivi: value

    Nastri terminati: value             Terminati con "yes": value              Con altri stati: value

    Nastri clonati: value

 */

public class TendencyHandler extends JPanel
{
    private JLabel labelStep;
    private JLabel valueStep;
    private JLabel labelTape;
    private JLabel valueTape;
    private Circle circleActiveTape;
    private JLabel labelActiveTape;
    private JLabel valueActiveTape;
    private JLabel labelTerminatedTape;
    private JLabel valueTerminatedTape;
    private Circle circleTerminatedNotNextTapeGreen;
    private JLabel labelTerminatedNotNextTapeGreen;
    private JLabel valueTerminatedNotNextTapeGreen;
    private Circle circleTerminatedNotNextTapeNo;
    private JLabel labelTerminatedNotNextTapeNo;
    private JLabel valueTerminatedNotNextTapeNo;
    private Circle circleTerminatedNotNextTapeRed;
    private JLabel labelTerminatedNotNextTapeRed;
    private JLabel valueTerminatedNotNextTapeRed;
    private Circle circleTerminatedPositionTapeOrange;
    private JLabel labelTerminatedPositionTapeOrange;
    private JLabel valueTerminatedPositionTapeOrange;
    private Circle circleClonedTape;
    private JLabel labelClonedTape;
    private JLabel valueClonedTape;

    public TendencyHandler()
    {
        setLayout(new BorderLayout());
        defineJLabel();
        defineLayout();
    }

    private void defineJLabel()
    {
        labelStep = createJLabel("Step:", Font.BOLD);
        valueStep = createJLabel("0", Font.PLAIN);

        labelTape = createJLabel("Configurazioni totali:", Font.BOLD);
        valueTape = createJLabel("0", Font.PLAIN);

        circleActiveTape = new Circle(Color.white);
        labelActiveTape = createJLabel("Configurazioni attive:", Font.BOLD);
        valueActiveTape = createJLabel("0", Font.PLAIN);

        circleTerminatedNotNextTapeGreen = new Circle(Color.green);
        circleTerminatedNotNextTapeNo = new Circle(new Color(255, 153, 153));
        circleTerminatedNotNextTapeRed = new Circle(Color.YELLOW);
        circleTerminatedPositionTapeOrange = new Circle(Color.orange);
        labelTerminatedTape = createJLabel("Configurazioni terminate:", Font.BOLD);
        valueTerminatedTape = createJLabel("0", Font.PLAIN);
        labelTerminatedNotNextTapeGreen = createJLabel("yes:", Font.BOLD);
        valueTerminatedNotNextTapeGreen = createJLabel("0", Font.PLAIN);
        labelTerminatedNotNextTapeNo = createJLabel("no:", Font.BOLD);
        valueTerminatedNotNextTapeNo = createJLabel("0", Font.PLAIN);
        labelTerminatedNotNextTapeRed = createJLabel("Altri stati:", Font.BOLD);
        valueTerminatedNotNextTapeRed = createJLabel("0", Font.PLAIN);
        labelTerminatedPositionTapeOrange = createJLabel("Fuori dal nastro:", Font.BOLD);
        valueTerminatedPositionTapeOrange = createJLabel("0", Font.PLAIN);

        circleClonedTape = new Circle(Color.cyan);
        labelClonedTape = createJLabel("Configurazioni clonate:", Font.BOLD);
        valueClonedTape = createJLabel("0", Font.PLAIN);
    }

    private JLabel createJLabel(String text, int style)
    {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("Arial", style, 22));
        return jLabel;
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
                                        .addComponent(labelStep, 58, 59, 60)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueStep, 120, 121, 122)
                                )
                        )
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTape)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTape, 120, 121, 122)
                                )
                        )
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleActiveTape, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelActiveTape)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueActiveTape, 120, 121, 122)
                                )
                        )
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTerminatedTape)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTerminatedTape, 100, 101, 102)
                                )
                                .addGap(15)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleTerminatedNotNextTapeGreen, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTerminatedNotNextTapeGreen)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTerminatedNotNextTapeGreen, 100, 101, 102)
                                )
                                .addGap(15)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleTerminatedNotNextTapeNo, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTerminatedNotNextTapeNo)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTerminatedNotNextTapeNo, 100, 101, 102)
                                )
                                .addGap(15)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleTerminatedNotNextTapeRed, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTerminatedNotNextTapeRed)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTerminatedNotNextTapeRed, 100, 101, 102)
                                )
                                .addGap(15)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleTerminatedPositionTapeOrange, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelTerminatedPositionTapeOrange)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueTerminatedPositionTapeOrange, 100, 101, 102)
                                )
                        )
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(circleClonedTape, 30, 31, 32)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelClonedTape)
                                )
                                .addGap(10)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueClonedTape, 120, 121, 122)
                                )
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelStep, 30, 31, 32)
                                .addComponent(valueStep, 30, 31, 32)
                        )
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelTape, 30, 31, 32)
                                .addComponent(valueTape, 30, 31, 32)
                        )
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(circleActiveTape, 30, 31, 32)
                                .addComponent(labelActiveTape, 30, 31, 32)
                                .addComponent(valueActiveTape, 30, 31, 32)
                        )
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labelTerminatedTape, 30, 31, 32)
                                .addComponent(valueTerminatedTape, 30, 31, 32)
                                .addComponent(circleTerminatedNotNextTapeGreen, 30, 31, 32)
                                .addComponent(labelTerminatedNotNextTapeGreen, 30, 31, 32)
                                .addComponent(valueTerminatedNotNextTapeGreen, 30, 31, 32)
                                .addComponent(circleTerminatedNotNextTapeNo, 30, 31, 32)
                                .addComponent(labelTerminatedNotNextTapeNo, 30, 31, 32)
                                .addComponent(valueTerminatedNotNextTapeNo, 30, 31, 32)
                                .addComponent(circleTerminatedNotNextTapeRed, 30, 31, 32)
                                .addComponent(labelTerminatedNotNextTapeRed, 30, 31, 32)
                                .addComponent(valueTerminatedNotNextTapeRed, 30, 31, 32)
                                .addComponent(circleTerminatedPositionTapeOrange, 30, 31, 32)
                                .addComponent(labelTerminatedPositionTapeOrange, 30, 31, 32)
                                .addComponent(valueTerminatedPositionTapeOrange, 30, 31, 32)
                        )
                        .addGap(25)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(circleClonedTape, 30, 31, 32)
                                .addComponent(labelClonedTape, 30, 31, 32)
                                .addComponent(valueClonedTape, 30, 31, 32)
                        )
        );

        add(body);
    }

    public void update(List<Tape> listTape, int step)
    {
        int tapesActive = 0;
        int tapesCloned = 0;
        int tapesTerminatedYes = 0;
        int tapesTerminatedNo = 0;
        int tapesTerminatedNextState = 0;
        int tapesTerminatedPosition = 0;

        for (Tape tape : listTape)
        {
            switch (tape.getState())
            {
                case Tape.STATE_IN_EXE:
                    tapesActive = tapesActive + 1;
                    break;
                case Tape.STATE_CLONED:
                    tapesCloned = tapesCloned + 1;
                    break;
                case Tape.STATE_NOT_CORRECT_POSITION:
                    tapesTerminatedPosition = tapesTerminatedPosition + 1;
                    break;
                case Tape.STATE_NOT_NEXT_STATE:
                    if(tape.getCurrentState().equals(InstructionManager.YES))
                    {
                        tapesTerminatedYes = tapesTerminatedYes + 1;
                    }
                    else if (tape.getCurrentState().equals(InstructionManager.NO))
                    {
                        tapesTerminatedNo = tapesTerminatedNo + 1;
                    }
                    else
                    {
                        tapesTerminatedNextState = tapesTerminatedNextState + 1;
                    }
                    break;
            }
        }

        valueStep.setText(Integer.toString(step));
        valueActiveTape.setText(Integer.toString(tapesActive));
        valueClonedTape.setText(Integer.toString(tapesCloned));
        valueTerminatedPositionTapeOrange.setText(Integer.toString(tapesTerminatedPosition));
        valueTerminatedNotNextTapeGreen.setText(Integer.toString(tapesTerminatedYes));
        valueTerminatedNotNextTapeNo.setText(Integer.toString(tapesTerminatedNo));
        valueTerminatedNotNextTapeRed.setText(Integer.toString(tapesTerminatedNextState));
        valueTerminatedTape.setText(Integer.toString(tapesTerminatedPosition + tapesTerminatedYes + tapesTerminatedNo + tapesTerminatedNextState));
        valueTape.setText(Integer.toString(listTape.size()));
    }

    public void reset()
    {
        valueStep.setText("0");
        valueActiveTape.setText("0");
        valueClonedTape.setText("0");
        valueTerminatedPositionTapeOrange.setText("0");
        valueTerminatedNotNextTapeGreen.setText("0");
        valueTerminatedNotNextTapeNo.setText("0");
        valueTerminatedNotNextTapeRed.setText("0");
        valueTerminatedTape.setText("0");
        valueTape.setText("0");
    }
}