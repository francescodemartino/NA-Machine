package Gui.Body.Tape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TapeOperation
{
    public static LinkedList<TapeOperation> fifoTapeOperation = new LinkedList<>();

    private TapeViewer tapeViewer;
    private int currentPosition;
    private String currentState;
    private int state;

    public TapeOperation(TapeViewer tapeViewer, int currentPosition, String currentState, int state)
    {
        this.tapeViewer = tapeViewer;
        this.currentPosition = currentPosition;
        this.currentState = currentState;
        this.state = state;
    }

    public TapeViewer getTapeViewer()
    {
        return tapeViewer;
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public String getCurrentState()
    {
        return currentState;
    }

    public int getState()
    {
        return state;
    }
}