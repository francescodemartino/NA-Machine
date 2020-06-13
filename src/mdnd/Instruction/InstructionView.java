package mdnd.Instruction;

public class InstructionView implements Comparable<InstructionView>
{
    private String state;
    private char character;
    private String stateTransition;
    private char characterTransition;
    private Direction directionTransition;
    private String description;

    public InstructionView(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, String description)
    {
        this.state = state;
        this.character = character;
        this.stateTransition = stateTransition;
        this.characterTransition = characterTransition;
        this.directionTransition = directionTransition;
        this.description = description;
    }

    public String getInitialState()
    {
        return state;
    }

    public char getInitialCharacter()
    {
        return character;
    }

    public String getTransitionState()
    {
        return stateTransition;
    }

    public char getTransitionCharacter()
    {
        return characterTransition;
    }

    public Direction getTransitionDirection()
    {
        return directionTransition;
    }

    public String getDescription()
    {
        return description;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setCharacter(char character)
    {
        this.character = character;
    }

    public void setStateTransition(String stateTransition)
    {
        this.stateTransition = stateTransition;
    }

    public void setCharacterTransition(char characterTransition)
    {
        this.characterTransition = characterTransition;
    }

    public void setDirectionTransition(Direction directionTransition)
    {
        this.directionTransition = directionTransition;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public int compareTo(InstructionView instructionView)
    {
        int result = state.compareTo(instructionView.state);
        if (result == 0)
        {
            return Character.compare(character, instructionView.character);
        }
        return result;
    }
}
