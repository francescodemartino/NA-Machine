package mdnd.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Gui.Body.Tape.TapeOperation;
import mdnd.Instruction.Direction;
import mdnd.Instruction.Instruction;
import mdnd.Instruction.InstructionManager;
import Gui.Body.Tape.TapeViewer;

public class Tape
{
    public static final char FIRST_CHAR = '#';
    public static final char DOLLAR_CHAR = '$';
    public static final String FIRST_STATE = "q0";
    public static final int STATE_IN_EXE = 0;
    public static final int STATE_CLONED = 1;
    public static final int STATE_NOT_NEXT_STATE = 2;
    public static final int STATE_NOT_CORRECT_POSITION = 3;

    private InstructionManager instructionManager;
    private List<Character> characters;
    private ExecutionMdtND executionMdtND;
    private List<Instruction> instructions;
    private int currentPosition;
    private String currentState;
    private int step = 0;
    private int state = STATE_IN_EXE;
    private Tape previous = null;
    private TapeViewer tapeViewer;
    private int level = 0;

    public Tape(InstructionManager instructionManager, List<Character> characters, ExecutionMdtND executionMdtND, int currentPosition, String currentState, int step, int level)
    {
        this.instructionManager = instructionManager;
        this.instructions = instructionManager.getListInstruction();
        this.executionMdtND = executionMdtND;
        this.characters = characters;
        this.currentPosition = currentPosition;
        this.currentState = currentState;
        this.step = step;
        this.level = level;
        tapeViewer = new TapeViewer(instructionManager, characters, executionMdtND, currentPosition, currentState, state);
    }

    public Tape(InstructionManager instructionManager, ExecutionMdtND executionMdtND)
    {
        this(instructionManager, executionMdtND, "", 0);
    }

    public Tape(InstructionManager instructionManager, String tape)
    {
        this(instructionManager, null, tape, 0);
    }

    public Tape(InstructionManager instructionManager, ExecutionMdtND executionMdtND, String tape, int level)
    {
        this.instructionManager = instructionManager;
        this.instructions = instructionManager.getListInstruction();
        this.executionMdtND = executionMdtND;
        this.level = level;
        currentState = FIRST_STATE;
        characters = new ArrayList<>();
        changeContentTape(tape, true);
    }

    public void changeContentTape(String tape)
    {
        changeContentTape(tape, false);
    }

    public void changeContentTape(String tape, boolean isNew)
    {
        characters.clear();
        currentPosition = 0;
        if(tape.length() == 0 || tape.charAt(0) != FIRST_CHAR)
        {
            characters.add(FIRST_CHAR);
        }
        for(int i=0; i<tape.length(); i=i+1)
        {
            characters.add(tape.charAt(i));
        }
        if(isNew)
        {
            tapeViewer = new TapeViewer(instructionManager, characters, executionMdtND, currentPosition, currentState, state);
        }
        TapeOperation.fifoTapeOperation.offer(new TapeOperation(tapeViewer, currentPosition, currentState, state));
    }

    public void addExecutionMdtND(ExecutionMdtND executionMdtND)
    {
        this.executionMdtND = executionMdtND;
        tapeViewer.addExecutionMdtND(executionMdtND);
    }

    public List<Character> getCharacters()
    {
        return characters;
    }

    public ResponseTape exe()
    {
        step = step + 1;
        Optional<Instruction> instructionOptional = instructions.stream().filter(ins -> ins.getInitialCharacter() == getCurrentChar()).filter(ins -> ins.getInitialState().equals(currentState)).findFirst();
        if(!instructionOptional.isPresent())
        {
            setState(STATE_NOT_NEXT_STATE);
            TapeOperation.fifoTapeOperation.offer(new TapeOperation(tapeViewer, currentPosition, currentState, STATE_NOT_NEXT_STATE));
            return new ResponseTape(ResponseTape.ACTION_REMOVE, null);
        }
        Instruction instruction = instructionOptional.get();

        if(instruction.sizeTransition() == 1)
        {
            if(exeInstruction(instruction.getCharacter().get(0), instruction.getDirections().get(0), instruction.getState().get(0)))
            {
                TapeOperation.fifoTapeOperation.offer(new TapeOperation(tapeViewer, currentPosition, currentState, STATE_IN_EXE));
                return new ResponseTape(ResponseTape.ACTION_NOTHING, null);
            }
            else
            {
                return new ResponseTape(ResponseTape.ACTION_REMOVE, null);
            }
        }
        else
        {
            List<Tape> listTape = new ArrayList<>();
            for(int i = 0; i < instruction.sizeTransition(); i = i+1)
            {
                Tape tape = cloneTape();
                tape.setPrevious(this);
                tape.exeInstruction(instruction.getCharacter().get(i), instruction.getDirections().get(i), instruction.getState().get(i));
                TapeOperation.fifoTapeOperation.offer(new TapeOperation(tape.getTapeViewer(), tape.getCurrentPosition(), tape.getCurrentState(), tape.getState()));
                listTape.add(tape);
            }
            step = step - 1;
            setState(STATE_CLONED);
            TapeOperation.fifoTapeOperation.offer(new TapeOperation(tapeViewer, currentPosition, currentState, STATE_CLONED));
            return new ResponseTape(ResponseTape.ACTION_ADD, listTape);
        }
    }

    public boolean exeInstruction(char character, Direction direction, String state)
    {
        setCurrentChar(character);
        setCurrentState(state);
        if(direction == Direction.RIGHT)
        {
            toNextChar();
        }
        else if(direction == Direction.LEFT)
        {
            if(!toPastChar())
            {
                return false;
            }
        }
        return true;
    }

    public void setCurrentState(String state)
    {
        currentState = state;
    }

    public void setCurrentChar(char character)
    {
        characters.set(currentPosition, character);
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public String getCurrentState()
    {
        return currentState;
    }

    private char getCurrentChar()
    {
        return characters.get(currentPosition);
    }

    public int getStep()
    {
        return step;
    }

    public int getState()
    {
        return state;
    }

    public Tape getPrevious()
    {
        return previous;
    }

    public TapeViewer getTapeViewer()
    {
        return tapeViewer;
    }

    public boolean isYesLastState()
    {
        return state == STATE_NOT_NEXT_STATE && getCurrentState().equals(InstructionManager.YES);
    }

    public boolean isNoLastState()
    {
        return state == STATE_NOT_NEXT_STATE && getCurrentState().equals(InstructionManager.NO);
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public void setPrevious(Tape previous)
    {
        this.previous = previous;
    }

    private boolean toPastChar()
    {
        if(currentPosition == 0)
        {
            setState(STATE_NOT_CORRECT_POSITION);
            TapeOperation.fifoTapeOperation.offer(new TapeOperation(tapeViewer, currentPosition, currentState, STATE_NOT_CORRECT_POSITION));
            return false;
        }
        currentPosition = currentPosition - 1;
        return true;
    }

    private void toNextChar()
    {
        currentPosition = currentPosition + 1;
        if(currentPosition == characters.size())
        {
            characters.add(DOLLAR_CHAR);
        }
    }

    private Tape cloneTape()
    {
        return new Tape(instructionManager, ((List<Character>)((ArrayList)characters).clone()), executionMdtND, currentPosition, currentState, step, level+1);
    }

    @Override
    public String toString()
    {
        return Integer.toString(level);
    }
}