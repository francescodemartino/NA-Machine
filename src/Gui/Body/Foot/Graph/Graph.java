package Gui.Body.Foot.Graph;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import mdnd.Machine.Tape;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class Graph
{
    private Tape root;
    private int edge = 0;
    private DelegateTree<Tape, Integer> tree;
    private VisualizationViewer<Tape, Integer> vv;
    private GraphZoomScrollPane graphZoomScrollPane;
    private ClickMouseListener clickMouseListener;

    public Graph(Tape root)
    {
        this.root = root;
        createGraph();
    }

    public JPanel getGraph()
    {
        return graphZoomScrollPane;
    }

    public void setClickTapeListener(ClickTapeListener clickTapeListener)
    {
        clickMouseListener.setClickTapeListener(clickTapeListener);
    }

    public void addNode(Tape tape)
    {
        tree.addChild(edge, tape.getPrevious(), tape);
        edge = edge + 1;
    }

    public void refreshGraph()
    {
        HorizontalTreeLayout<Tape, Integer> layout;
        layout = new HorizontalTreeLayout<>(tree);
        vv.setGraphLayout(layout);
    }

    private void createGraph()
    {
        tree = new DelegateTree<>();
        tree.setRoot(root);
        vv = new VisualizationViewer<>(new FRLayout<>(tree));
        setLayout();
        RenderContext<Tape, Integer> renderContext = vv.getRenderContext();
        setTransformerVertexPaint(renderContext);
        setSizeVertex(renderContext);
        setVertexLabel(renderContext);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<>());
        setClickMouseListener();
        setMouseHandler();
        setScrollPane();
    }

    private void setLayout()
    {
        HorizontalTreeLayout<Tape,Integer> layout;
        layout = new HorizontalTreeLayout<>(tree);
        vv.setGraphLayout(layout);
    }

    private void setTransformerVertexPaint(RenderContext<Tape, Integer> renderContext)
    {
        renderContext.setVertexFillPaintTransformer(new TransformerVertexPaint());
    }

    private void setSizeVertex(RenderContext<Tape, Integer> renderContext)
    {
        Transformer<Tape, Shape> vertexSize = tape -> new Ellipse2D.Double(-16, -16, 32, 32);
        renderContext.setVertexShapeTransformer(vertexSize);
    }

    private void setVertexLabel(RenderContext<Tape, Integer> renderContext)
    {
        Transformer<Tape, String> vertexTransformer = new ToStringLabeller<>();
        renderContext.setVertexLabelTransformer(vertexTransformer);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
    }

    private void setClickMouseListener()
    {
        clickMouseListener = new ClickMouseListener(vv);
        vv.addMouseListener(clickMouseListener);
    }

    private void setMouseHandler()
    {
        PluggableGraphMouse gm = new PluggableGraphMouse();
        TranslatingGraphMousePlugin translatingGraphMousePlugin = new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK);
        translatingGraphMousePlugin.setCursor(Cursor.getDefaultCursor());
        gm.add(translatingGraphMousePlugin);
        gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 0.9f, 1.1f));
        vv.setGraphMouse(gm);
    }

    private void setScrollPane()
    {
        graphZoomScrollPane = new GraphZoomScrollPane(vv);
    }
}