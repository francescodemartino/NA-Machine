package mdnd.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Instruction
{
    private String initialState;
    private char initialCharacter;
    private List<String> nextState;
    private List<Character> nextCharacter;
    private List<Direction> directions;
    private List<InstructionView> instructionViews;

    public Instruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, InstructionView instructionView)
    {
        this.initialState = state;
        this.initialCharacter = character;
        nextState = new ArrayList<>();
        nextCharacter = new ArrayList<>();
        directions = new ArrayList<>();
        instructionViews = new ArrayList<>();
        addTransition(stateTransition, characterTransition, directionTransition, instructionView);
    }

    public void addTransition(String state, char character, Direction direction, InstructionView instructionView)
    {
        nextState.add(state);
        nextCharacter.add(character);
        directions.add(direction);
        instructionViews.add(instructionView);
    }

    public int sizeTransition()
    {
        return nextState.size();
    }

    public String getInitialState()
    {
        return initialState;
    }

    public char getInitialCharacter()
    {
        return initialCharacter;
    }

    public List<String> getState()
    {
        return nextState;
    }

    public List<Character> getCharacter()
    {
        return nextCharacter;
    }

    public List<Direction> getDirections()
    {
        return directions;
    }

    public List<InstructionView> getInstructionViews()
    {
        return instructionViews;
    }
}
