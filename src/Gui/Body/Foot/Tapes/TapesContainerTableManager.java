package Gui.Body.Foot.Tapes;

import java.util.ArrayList;
import java.util.List;

public class TapesContainerTableManager
{
    private List<TapeContainer> listTapeContainerFirst;
    private List<TapeContainer> listTapeContainerSecond;
    private boolean switchList;

    public TapesContainerTableManager()
    {
        listTapeContainerFirst = new ArrayList<>();
        listTapeContainerSecond = new ArrayList<>();
        switchList = true;
    }

    public void addToPreemptiveList(TapeContainer tape)
    {
        if(!switchList)
        {
            listTapeContainerFirst.add(tape);
        }
        else
        {
            listTapeContainerSecond.add(tape);
        }
    }

    public TapeContainer getTapeContainer(int row)
    {
            if(switchList)
            {
                if(row >= listTapeContainerFirst.size())
                {
                    return null;
                }
                return listTapeContainerFirst.get(row);
            }
            else
            {
                if(row >= listTapeContainerSecond.size())
                {
                    return null;
                }
                return listTapeContainerSecond.get(row);
            }
    }

    public void doSwitch()
    {
        if(switchList)
        {
            listTapeContainerFirst.clear();
        }
        else
        {
            listTapeContainerSecond.clear();
        }
        switchList = !switchList;
    }

    public void reset()
    {
        listTapeContainerFirst.clear();
        listTapeContainerSecond.clear();
    }
}