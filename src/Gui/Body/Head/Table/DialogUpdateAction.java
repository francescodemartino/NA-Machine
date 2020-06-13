package Gui.Body.Head.Table;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Exception.InstructionAlreadyExistsException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;

class DialogUpdateAction extends DialogAction
{
    boolean isNotUpdated = false;

    public DialogUpdateAction(InstructionManager instructionManager, int row)
    {
        super(instructionManager, row, "Modifica azione");
    }

    @Override
    protected String getValueInitialState()
    {
        return instruction.getTransitionState();
    }

    @Override
    protected String getValueInitialCharacter()
    {
        return Character.toString(instruction.getTransitionCharacter());
    }

    @Override
    protected String getValueDirection()
    {
        return Character.toString(instruction.getTransitionDirection().name().charAt(0));
    }

    @Override
    protected void actionBeforeDispose() throws IncorrectInputDataTableException
    {
        if(!(instruction.getTransitionState().equals(fieldState.getText().trim()) && instruction.getTransitionCharacter() == fieldCharacter.getText().trim().charAt(0) && instruction.getTransitionDirection() == getDirection()))
        {
            if(instruction.getInitialCharacter() == Tape.FIRST_CHAR && getDirection() != Direction.RIGHT)
            {
                throw new IncorrectInputDataTableException("Incorrect first char");
            }
            try
            {
                instructionManager.updateInstruction(instruction.getInitialState(), instruction.getInitialCharacter(), fieldState.getText().trim(), fieldCharacter.getText().trim().charAt(0), getDirection(), instruction.getDescription(), instruction);
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
