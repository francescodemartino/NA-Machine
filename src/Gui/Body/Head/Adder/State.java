package Gui.Body.Head.Adder;

import mdnd.Exception.StateCharacterUsedException;
import mdnd.Instruction.InstructionManager;

public class State extends Box
{
    public State(InstructionManager instructionManager)
    {
        super(instructionManager, instructionManager.getListState(), DialogAddElement.INFINITY_LENGTH_JTEXTFIELD);
    }

    @Override
    protected String getNameColumn()
    {
        return "State";
    }

    @Override
    void removeElement(int row) throws StateCharacterUsedException
    {
        instructionManager.deleteState(instructionManager.getListState().get(row));
    }

    @Override
    public void exeAction(String text)
    {
        instructionManager.addState(text);
    }

    @Override
    public void orderElements()
    {
        instructionManager.sortListState();
        refreshGUI();
    }
}
