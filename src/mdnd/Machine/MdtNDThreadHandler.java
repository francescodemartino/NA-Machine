package mdnd.Machine;

public class MdtNDThreadHandler extends Thread
{
    private MdtND mdtND;
    private boolean cannotContinue = true;

    @Override
    public void run()
    {
        while (true)
        {
            if (cannotContinue)
            {
                waitNotify();
            }
            cannotContinue = true;
            mdtND.start();
        }
    }

    public synchronized void exe(MdtND mdtND)
    {
        this.mdtND = mdtND;
        cannotContinue = false;
        notify();
    }

    private synchronized void waitNotify()
    {
        try
        {
            wait();
        }
        catch (InterruptedException e)
        {
        }
    }
}
