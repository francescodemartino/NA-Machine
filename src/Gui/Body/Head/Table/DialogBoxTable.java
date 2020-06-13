package Gui.Body.Head.Table;

import Gui.Component.Dialog.DialogBox;
import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionView;

abstract class DialogBoxTable extends DialogBox
{
    protected InstructionView instruction;
    protected InstructionManager instructionManager;
    protected int row;

    public DialogBoxTable(InstructionManager instructionManager, int row, String title, int width, int height)
    {
        super(title, width, height);
        this.instructionManager = instructionManager;
        this.row = row;
        if(row >= 0)
        {
            instruction = instructionManager.getListInstructionView().get(row);
        }
    }
}