package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Exception.InstructionAlreadyExistsException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

class DialogUpdateConfiguration extends DialogConfiguration
{
    boolean isNotUpdated = false;

    public DialogUpdateConfiguration(InstructionManager instructionManager, int row)
    {
        super(instructionManager, row, "Modifica configurazione");
    }

    @Override
    protected String getValueInitialState()
    {
        return instruction.getInitialState();
    }

    @Override
    protected String getValueInitialCharacter()
    {
        return Character.toString(instruction.getInitialCharacter());
    }

    @Override
    protected void actionBeforeDispose() throws IncorrectInputDataTableException
    {
        if(!(instruction.getInitialState().equals(fieldState.getText().trim()) && instruction.getInitialCharacter() == fieldCharacter.getText().trim().charAt(0)))
        {
            if(fieldCharacter.getText().trim().charAt(0) == Tape.FIRST_CHAR && instruction.getTransitionDirection() != Direction.RIGHT)
            {
                throw new IncorrectInputDataTableException("Incorrect first char");
            }
            try
            {
                instructionManager.updateInstruction(fieldState.getText().trim(), fieldCharacter.getText().trim().charAt(0), instruction.getTransitionState(), instruction.getTransitionCharacter(), instruction.getTransitionDirection(), instruction.getDescription(), instruction);
            }
            catch (InstructionAlreadyExistsException e)
            {
                isNotUpdated = true;
            }
        }
    }

    @Override
    protected void actionAfterDispose()
    {
        if(isNotUpdated)
        {
            openMessageError("La configurazione che hai scelto renderebbe l'istruzione identica a una già esistente");
        }
    }
}
