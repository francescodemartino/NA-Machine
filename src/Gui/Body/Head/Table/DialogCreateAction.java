package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

class DialogCreateAction extends DialogAction
{
    private String initialState;
    private char initialCharacter;
    private RefreshTable refreshTable;

    public DialogCreateAction(InstructionManager instructionManager, RefreshTable refreshTable, String initialState, char initialCharacter)
    {
        super(instructionManager, -1, "Crea azione");
        this.initialState = initialState;
        this.initialCharacter = initialCharacter;
        locationTitleHorizontal = locationTitleHorizontal + 20;
        this.refreshTable = refreshTable;
    }

    @Override
    protected String getValueInitialState()
    {
        return "";
    }

    @Override
    protected String getValueInitialCharacter()
    {
        return "";
    }

    @Override
    protected String getValueDirection()
    {
        return "";
    }

    @Override
    protected void actionBeforeDispose() throws IncorrectInputDataTableException
    {
        String state = fieldState.getText().trim();
        char character = fieldCharacter.getText().trim().charAt(0);
        Direction direction = getDirection();
        if(initialCharacter == Tape.FIRST_CHAR && direction != Direction.RIGHT)
        {
            throw new IncorrectInputDataTableException("Incorrect first char");
        }
        if(!(instructionManager.hasState(state) && instructionManager.hasCharacter(character)))
        {
            throw new IncorrectInputDataTableException("Incorrect state or character");
        }
        instructionManager.addInstruction(initialState, initialCharacter, state, character, direction, "");
    }

    @Override
    protected void actionAfterDispose()
    {
        refreshTable.refreshGUI();
    }
}
