package mdnd.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Gui.Body.Rudder.RudderHandler;
import Gui.Body.Rudder.RudderListener;
import mdnd.Instruction.Instruction;
import mdnd.Instruction.InstructionManager;

public class MdtND implements ExecutionMdtND
{
    public static final int DEFAULT_TIME_SLEEP = 1500;

    private List<Tape> tapes;
    private List<Tape> tapesInExe;
    private InstructionManager instructionManager;
    private List<Instruction> instructions;
    private RunningListener allTapesListener;
    private RunningListener tapesAddedListener;
    private RudderHandler rudderHandler;
    private int sleepTime;
    private boolean isStarting;
    private long startTimeExe;
    private long endTimeExe;
    private int countStep = 0;

    public MdtND(InstructionManager instructionManager, Tape tape)
    {
        this.instructionManager = instructionManager;
        this.instructions = instructionManager.getListInstruction();
        sleepTime = DEFAULT_TIME_SLEEP;
        isStarting = false;
        allTapesListener = null;
        tapes = new ArrayList<>();
        tapesInExe = new ArrayList<>();
        tapes.add(tape);
        tapesInExe.add(tape);
    }

    public InstructionManager getInstructionManager()
    {
        return instructionManager;
    }

    public void setSleepTime(int sleepTime)
    {
        this.sleepTime = sleepTime;
    }

    public List getInstructions()
    {
        return instructions;
    }

    public List<Tape> getTapes()
    {
        return tapes;
    }

    public void setListenerAllTapes(RunningListener runningListener)
    {
        this.allTapesListener = runningListener;
    }

    public void setListenerTapesAdded(RunningListener tapesAddedListener)
    {
        this.tapesAddedListener = tapesAddedListener;
    }

    public void start()
    {
        isStarting = true;
        startTimeExe = System.currentTimeMillis();
        endTimeExe = startTimeExe;
        runAllTapesListener(tapes);
        while (isStarting)
        {
            sleep();
            if(isStarting)
            {
                startTimeExe = System.currentTimeMillis();
                step();
                endTimeExe = System.currentTimeMillis();
            }
        }
    }

    public void pause()
    {
        isStarting = false;
    }

    public Tape getRoot()
    {
        return tapes.get(0);
    }

    public void step()
    {
        if(tapesInExe.size() == 0)
        {
            rudderHandler.clickPause();
            return;
        }
        countStep = countStep + 1;
        ResponseTape responseTape;
        int i = 0;
        while (i < tapesInExe.size())
        {
            responseTape = tapesInExe.get(i).exe();
            if(responseTape.getAction() == ResponseTape.ACTION_NOTHING)
            {
                i = i + 1;
            }
            else if(responseTape.getAction() == ResponseTape.ACTION_ADD)
            {
                tapesInExe.remove(i);
                tapesInExe.addAll(i, responseTape.getTapes().stream().filter(tape -> tape.getState() == Tape.STATE_IN_EXE).collect(Collectors.toList()));
                tapes.addAll(responseTape.getTapes());
                runTapesAddedListener(responseTape.getTapes());
                i = i + responseTape.getTapes().size();
            }
            else
            {
                tapesInExe.remove(i);
            }
        }
        runAllTapesListener(tapes);
    }

    public int getStep()
    {
        return countStep;
    }

    private void runAllTapesListener(List<Tape> tapes)
    {
        if(allTapesListener != null)
        {
            allTapesListener.onExe(tapes);
        }
    }

    private void runTapesAddedListener(List<Tape> tapes)
    {
        if(tapesAddedListener != null)
        {
            tapesAddedListener.onExe(tapes);
        }
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(sleepTime - (endTimeExe - startTimeExe));
        }
        catch (Exception e)
        {
        }
    }

    public void addRudder(RudderHandler rudderHandler)
    {
        this.rudderHandler = rudderHandler;
    }

    @Override
    public boolean isInExecution()
    {
        return isStarting;
    }
}