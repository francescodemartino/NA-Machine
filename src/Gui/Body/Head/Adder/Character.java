package Gui.Body.Head.Adder;

import mdnd.Exception.StateCharacterUsedException;
import mdnd.Instruction.InstructionManager;

public class Character extends Box
{
    public Character(InstructionManager instructionManager)
    {
        super(instructionManager, instructionManager.getListChar(), 1);
    }

    @Override
    protected String getNameColumn()
    {
        return "Alphabet";
    }

    @Override
    void removeElement(int row) throws StateCharacterUsedException
    {
        instructionManager.deleteChar(instructionManager.getListChar().get(row));
    }

    @Override
    public void exeAction(String text)
    {
        instructionManager.addChar(text.charAt(0));
    }

    @Override
    public void orderElements()
    {
        instructionManager.sortListChar();
        refreshGUI();
    }
}
