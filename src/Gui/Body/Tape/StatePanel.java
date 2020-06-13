package Gui.Body.Tape;

import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends JPanel
{
    public StatePanel(int state, String currentState)
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addConfiguration(state, currentState);
    }

    public void addConfiguration(int state, String currentState)
    {
        Color color = Color.white;
        String text = "";

        switch (state)
        {
            case Tape.STATE_IN_EXE:
                color = Color.white;
                text = "Attivo";
                break;
            case Tape.STATE_CLONED:
                color = Color.cyan;
                text = "Clonato";
                break;
            case Tape.STATE_NOT_CORRECT_POSITION:
                color = Color.orange;
                text = "Terminato";
                break;
            case Tape.STATE_NOT_NEXT_STATE:
                color = currentState.equals(InstructionManager.YES) ? Color.green : (currentState.equals(InstructionManager.NO) ? new Color(255, 153, 153) : Color.YELLOW);
                text = "Terminato";
                break;
        }

        Circle circle = new Circle(color);
        circle.setMaximumSize(new Dimension(32, 80));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        add(circle);
        add(label);
    }
}
