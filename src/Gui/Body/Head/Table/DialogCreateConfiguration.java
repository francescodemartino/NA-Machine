package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.InstructionManager;

class DialogCreateConfiguration extends DialogConfiguration
{
    private String state;
    private char character;
    private RefreshTable refreshTable;

    public DialogCreateConfiguration(InstructionManager instructionManager, RefreshTable refreshTable)
    {
        super(instructionManager, -1, "Crea configurazione");
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
    protected void actionBeforeDispose() throws IncorrectInputDataTableException
    {
        state = fieldState.getText().trim();
        character = fieldCharacter.getText().trim().charAt(0);
        if(!(instructionManager.hasState(state) && instructionManager.hasCharacter(character)))
        {
            throw new IncorrectInputDataTableException("Incorrect state or character");
        }
    }

    @Override
    protected void actionAfterDispose()
    {
        DialogCreateAction dialogCreateAction = new DialogCreateAction(instructionManager, refreshTable, state, character);
        dialogCreateAction.showDialog();
    }
}
