package mdnd.Instruction;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Exception.InstructionAlreadyExistsException;
import mdnd.Exception.StateCharacterUsedException;
import mdnd.Machine.Tape;

import java.util.*;
import java.util.stream.Collectors;

public class InstructionManager
{
    public static final String YES = "yes";
    public static final String NO = "no";

    private List<Instruction> listInstruction;
    private List<InstructionView> listInstructionView;
    private List<String> listState;
    private List<Character> listChar;
    private InstructionManagerUpdateListener instructionManagerUpdateListener;

    public InstructionManager()
    {
        listInstruction = new ArrayList<>();
        listInstructionView = new ArrayList<>();
        listState = new ArrayList<>();
        listState.add("q0");
        listState.add(YES);
        listState.add(NO);
        listChar = new ArrayList<>();
        listChar.add(Tape.FIRST_CHAR);
        listChar.add('$');
    }

    public void addState(String state)
    {
        if(!listState.contains(state))
        {
            noticeChange();
            listState.add(state);
        }
    }

    public void addChar(char charList)
    {
        if(!listChar.contains(charList))
        {
            noticeChange();
            listChar.add(charList);
        }
    }

    public List<String> getListState()
    {
        return listState;
    }

    public List<Character> getListChar()
    {
        return listChar;
    }

    public void sortListState()
    {
        Collections.sort(listState);
    }

    public void sortListChar()
    {
        Collections.sort(listChar);
    }

    public void sortInstruction()
    {
        Collections.sort(listInstructionView);
    }

    public void deleteState(String state) throws StateCharacterUsedException
    {
        if(isStateUsed(state))
        {
            throw new StateCharacterUsedException("state is used");
        }
        else
        {
            noticeChange();
            listState.remove(state);
        }
    }

    public void deleteChar(char character) throws StateCharacterUsedException
    {
        if(isCharUsed(character))
        {
            throw new StateCharacterUsedException("char is used");
        }
        else
        {
            noticeChange();
            listChar.remove(Character.valueOf(character));
        }
    }

    public boolean isStateUsed(String state)
    {
        Optional<InstructionView> optional = listInstructionView.stream().filter(instructionView -> (instructionView.getInitialState().equals(state) || instructionView.getTransitionState().equals(state))).findFirst();
        return optional.isPresent();
    }

    public boolean isCharUsed(char character)
    {
        Optional<InstructionView> optional = listInstructionView.stream().filter(instructionView -> (instructionView.getInitialCharacter() == character || instructionView.getTransitionCharacter() == character)).findFirst();
        return optional.isPresent();
    }

    public boolean hasState(String state)
    {
        Optional<String> optional = listState.stream().filter(value -> value.equals(state)).findFirst();
        return optional.isPresent();
    }

    public boolean hasCharacter(char character)
    {
        return isCharPresent(character);
    }

    public List<InstructionView> getListInstructionView()
    {
        return listInstructionView;
    }

    public List<Instruction> getListInstruction()
    {
        return listInstruction;
    }

    public int size()
    {
        return listInstructionView.size();
    }

    public boolean existsInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition)
    {
        Optional<InstructionView> optional = listInstructionView.stream().filter(ins -> (ins.getInitialState().equals(state) && ins.getInitialCharacter() == character && ins.getTransitionState().equals(stateTransition) && ins.getTransitionCharacter() == characterTransition && ins.getTransitionDirection() == directionTransition)).findFirst();
        return optional.isPresent();
    }

    public void addInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, String description) throws IncorrectInputDataTableException
    {
        noticeChange();
        InstructionView instructionView = new InstructionView(state, character, stateTransition, characterTransition, directionTransition, description);
        addInstruction(state, character, stateTransition, characterTransition, directionTransition, instructionView, true);
    }

    public void removeInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition)
    {
        removeInstruction(state, character, stateTransition, characterTransition, directionTransition, true);
    }

    public void removeInstruction(InstructionView instructionView)
    {
        removeInstruction(instructionView.getInitialState(), instructionView.getInitialCharacter(), instructionView.getTransitionState(), instructionView.getTransitionCharacter(), instructionView.getTransitionDirection(), true);
    }

    public void updateInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, String description, InstructionView instructionView) throws IncorrectInputDataTableException, InstructionAlreadyExistsException
    {
        updateInstruction(state, character, stateTransition, characterTransition, directionTransition, description, instructionView, true);
    }

    public void updateInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, String description, InstructionView instructionView, boolean checkExists) throws IncorrectInputDataTableException, InstructionAlreadyExistsException
    {
        if(checkExists && existsInstruction(state, character, stateTransition, characterTransition, directionTransition))
        {
            throw new InstructionAlreadyExistsException("Cannot update instruction");
        }

        if(!(isStatePresent(state, stateTransition) && isCharPresent(character, characterTransition)))
        {
            throw new IncorrectInputDataTableException("Incorrect state or character");
        }
        else
        {
            noticeChange();
            removeInstruction(instructionView.getInitialState(), instructionView.getInitialCharacter(), instructionView.getTransitionState(), instructionView.getTransitionCharacter(), instructionView.getTransitionDirection(), false);
            instructionView.setState(state);
            instructionView.setCharacter(character);
            instructionView.setStateTransition(stateTransition);
            instructionView.setCharacterTransition(characterTransition);
            instructionView.setDirectionTransition(directionTransition);
            instructionView.setDescription(description);
            addInstruction(state, character, stateTransition, characterTransition, directionTransition, instructionView, false);
        }
    }

    public void addListenerInstructionManagerUpdate(InstructionManagerUpdateListener instructionManagerUpdateListener)
    {
        this.instructionManagerUpdateListener = instructionManagerUpdateListener;
    }

    private void noticeChange()
    {
        if(instructionManagerUpdateListener != null)
        {
            instructionManagerUpdateListener.update();
        }
    }

    private boolean isCharPresent(char... charList)
    {
        for(char value: charList)
        {
            if(!listChar.contains(value))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isStatePresent(String... stateList)
    {
        for(String value: stateList)
        {
            if(!listState.contains(value))
            {
                return false;
            }
        }
        return true;
    }

    private void addInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, InstructionView instructionView, boolean addToInstructionView) throws IncorrectInputDataTableException
    {
        if(!(isStatePresent(state, stateTransition) && isCharPresent(character, characterTransition)))
        {
            throw new IncorrectInputDataTableException("Incorrect state or character");
        }
        else
        {
            Optional<Instruction> optionalInstruction = listInstruction.stream().filter(ins -> ins.getInitialState().equals(state)).filter(ins -> ins.getInitialCharacter() == character).findFirst();
            if(optionalInstruction.isPresent())
            {
                Instruction instruction = optionalInstruction.get();
                boolean flag = true;
                for (int i=0; i<instruction.sizeTransition(); i=i+1)
                {
                    if(instruction.getState().get(i).equals(stateTransition) && instruction.getCharacter().get(i) == characterTransition && instruction.getDirections().get(i) == directionTransition)
                    {
                        flag = false;
                        break;
                    }
                }
                if(flag)
                {
                    if(addToInstructionView)
                    {
                        listInstructionView.add(instructionView);
                    }
                    instruction.addTransition(stateTransition, characterTransition, directionTransition, instructionView);
                }
            }
            else
            {
                if(addToInstructionView)
                {
                    listInstructionView.add(instructionView);
                }
                listInstruction.add(new Instruction(state, character, stateTransition, characterTransition, directionTransition, instructionView));
            }
        }
    }

    private void removeInstruction(String state, char character, String stateTransition, char characterTransition, Direction directionTransition, boolean removeFromListInstructionView)
    {
        Optional<Instruction> optionalInstruction = listInstruction.stream().filter(ins -> ins.getInitialState().equals(state)).filter(ins -> ins.getInitialCharacter() == character).findFirst();
        if(optionalInstruction.isPresent())
        {
            noticeChange();
            Instruction instruction = optionalInstruction.get();
            boolean flag = false;
            int index = -1;
            int size = instruction.sizeTransition();
            for (int i=0; i<size; i=i+1)
            {
                if(instruction.getState().get(i).equals(stateTransition) && instruction.getCharacter().get(i) == characterTransition && instruction.getDirections().get(i) == directionTransition)
                {
                    flag = true;
                    index = i;
                    break;
                }
            }
            if (flag)
            {
                if(removeFromListInstructionView)
                {
                    listInstructionView.remove(instruction.getInstructionViews().get(index));
                }
                instruction.getState().remove(index);
                instruction.getCharacter().remove(index);
                instruction.getDirections().remove(index);
                instruction.getInstructionViews().remove(index);
                if(size == 1)
                {
                    listInstruction.remove(instruction);
                }
            }
        }
    }
}
