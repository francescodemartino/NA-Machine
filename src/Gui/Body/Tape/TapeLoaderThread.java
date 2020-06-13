package Gui.Body.Tape;

import javax.swing.*;
import java.util.List;

public class TapeLoaderThread extends SwingWorker<Void, TapeViewer>
{
    private boolean notJumpWait = true;
    private TapeLoaderThreadListener tapeLoaderThreadListener;
    private boolean normalExecution = true;

    @Override
    protected Void doInBackground()
    {
        while (true)
        {
            waitNotify();
            while (TapeOperation.fifoTapeOperation.size() != 0)
            {
                TapeOperation tapeOperation = TapeOperation.fifoTapeOperation.poll();
                TapeViewer tapeViewer = tapeOperation.getTapeViewer();
                tapeViewer.setCurrentPosition(tapeOperation.getCurrentPosition());
                tapeViewer.setCurrentState(tapeOperation.getCurrentState());
                tapeViewer.setState(tapeOperation.getState());
                publish(tapeViewer);
            }
        }
    }

    @Override
    protected void process(List<TapeViewer> chunks)
    {
        if(TapeOperation.fifoTapeOperation.size() == 0)
        {
            if(tapeLoaderThreadListener != null)
            {
                tapeLoaderThreadListener.exe();
            }
        }

        for (TapeViewer tapeViewer : chunks)
        {
            tapeViewer.refreshGUI();
        }
    }

    public synchronized void exe()
    {
        notify();
    }

    public void addListener(TapeLoaderThreadListener tapeLoaderThreadListener)
    {
        this.tapeLoaderThreadListener = tapeLoaderThreadListener;
    }

    public void setJumpWaitOneTime(boolean jump)
    {
        notJumpWait = !jump;
    }

    private synchronized void waitNotify()
    {
        if (notJumpWait)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        else
        {
            notJumpWait = true;
        }
    }
}