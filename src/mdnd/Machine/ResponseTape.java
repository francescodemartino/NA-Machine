package mdnd.Machine;

import java.util.List;

public class ResponseTape
{
    public static int ACTION_NOTHING = 0;
    public static int ACTION_ADD = 1;
    public static int ACTION_REMOVE = 2;

    private int action;
    private List<Tape> tapes;

    public ResponseTape(int action, List<Tape> tapes)
    {
        this.action = action;
        this.tapes = tapes;
    }

    public int getAction()
    {
        return action;
    }

    public List<Tape> getTapes()
    {
        return tapes;
    }
}
