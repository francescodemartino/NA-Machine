package Gui.Body.Foot.Graph;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import mdnd.Machine.Tape;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ClickMouseListener implements MouseListener
{
    private VisualizationViewer<Tape, Integer> vv;
    private GraphElementAccessor<Tape, Integer> pickedVertexState;
    private ClickTapeListener clickTapeListener;

    public ClickMouseListener(VisualizationViewer<Tape, Integer> vv)
    {
        this.vv = vv;
        pickedVertexState = vv.getPickSupport();
    }

    public void setClickTapeListener(ClickTapeListener clickTapeListener)
    {
        this.clickTapeListener = clickTapeListener;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Tape picked = pickedVertexState.getVertex(vv.getGraphLayout(), e.getX(), e.getY());
        if(picked != null && clickTapeListener != null)
        {
            clickTapeListener.onClick(picked);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}
