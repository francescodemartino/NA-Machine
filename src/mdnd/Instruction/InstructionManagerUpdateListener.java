package mdnd.Instruction;

public abstract class InstructionManagerUpdateListener
{
    private boolean isNotice = false;

    protected abstract void updateInternal();

    protected abstract void resetInternal();

    public void update()
    {
        if(!isNotice)
        {
            isNotice = true;
            updateInternal();
        }
    }

    public void reset()
    {
        if (isNotice)
        {
            isNotice = false;
            resetInternal();
        }
    }

    public boolean isNotSaved()
    {
        return isNotice;
    }
}