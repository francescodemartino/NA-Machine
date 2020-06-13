package Trash;

import java.util.List;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Exception.IncorrentInstructionException;
import mdnd.Exception.IncorrectPositionException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.MdtND;
import mdnd.Machine.RunningListener;
import mdnd.Machine.Tape;

public class UseMdtND
{
    public static void main(String[] args) throws IncorrentInstructionException, IncorrectPositionException
    {
        InstructionManager instructionManager  = new InstructionManager();
        try
        {
            instructionManager.addInstruction("q0", '#', "q1", '#', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '$', "q1", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '1', "q2", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q2", '$', "q3", '$', Direction.RIGHT, "");
            instructionManager.addInstruction("q2", '$', "q6", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q3", '$', "q3", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q6", '1', "q5", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '1', "q4", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '$', "q5", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '$', "q4", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q5", '$', "q4", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q5", '1', "q4", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q4", '$', "q4", '$', Direction.RIGHT, "");
            instructionManager.addInstruction("q4", '1', "q4", '$', Direction.RIGHT, "");
        }
        catch (IncorrectInputDataTableException e)
        {
            e.printStackTrace();
        }

        Tape tape = new Tape(instructionManager, "$$$$1");
        MdtND mdtND = new MdtND(instructionManager, tape);
        tape.addExecutionMdtND(mdtND);
        mdtND.setListenerAllTapes(runningListener);

        mdtND.start();
    }

    private static RunningListener runningListener = new RunningListener()
    {
        @Override
        public void onExe(List<Tape> tapes)
        {
            for(int i=0; i<tapes.size(); i=i+1)
            {
                System.out.println(i + ") " + tapes.get(i).getCurrentPosition() + " - " + tapes.get(i).getCurrentState() + " - " + tapes.get(i).getCharacters());
            }
            System.out.println("");
        }
    };
}
