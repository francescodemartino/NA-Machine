package Gui.Body.Foot;

import Gui.Body.Foot.Graph.Graph;
import Gui.Body.Foot.Tapes.Visualizer;
import Gui.Body.Foot.Tendency.TendencyHandler;
import Gui.Body.Tape.TapeAdder;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.MdtND;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FootHandler extends JPanel
{
    private MdtND mdtND;
    private Graph graph;
    private InstructionManager instructionManager;
    private TapeAdder tapeAdder;
    private JTabbedPane jTabbedPane;
    private JPanel contentProgress;
    private JPanel contentTree;
    private JPanel contentTapes;
    private JPanel jPanelEmpty;
    private Visualizer visualizer;
    private TendencyHandler tendencyHandler;

    public FootHandler(InstructionManager instructionManager, TapeAdder tapeAdder)
    {
        this.instructionManager = instructionManager;
        this.tapeAdder = tapeAdder;
        jPanelEmpty = new JPanel();
        setLayout(new BorderLayout());
        setField();
    }

    private void setField()
    {
        contentProgress = new JPanel();
        contentProgress.setLayout(new BorderLayout());
        contentTree = new JPanel();
        contentTree.setLayout(new BorderLayout());
        contentTapes = new JPanel();
        contentTapes.setLayout(new BorderLayout());

        jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Statistics", contentProgress);
        jTabbedPane.add("ND Tree", contentTree);
        jTabbedPane.add("Instantaneous description", contentTapes);
        jTabbedPane.setFont(new Font("Dialog", Font.BOLD, 20));
        add(jTabbedPane);

        visualizer = new Visualizer(tapeAdder);
        tendencyHandler = new TendencyHandler();

        refreshJPanelJTabbed(contentProgress, tendencyHandler);
    }

    public void startMdtND(MdtND mdtND)
    {
        this.mdtND = mdtND;
        initializeGraph();
        visualizer.addTapes(mdtND.getTapes());
    }

    public void actionWhenTapesAdded(List<Tape> tapes)
    {
        tapes.forEach(tape -> graph.addNode(tape));
        visualizer.addTapes(tapes);
    }

    public void actionWhenAllTapes(List<Tape> tapes)
    {
        tendencyHandler.update(tapes, mdtND.getStep());
        graph.refreshGraph();
    }

    public void executeAfterTapeLoaderThreadFinish()
    {
        visualizer.refreshTable();
    }

    public void stopMdtND()
    {
        cleanGraph();
        visualizer.reset();
        tendencyHandler.reset();
    }

    private void initializeGraph()
    {
        graph = new Graph(mdtND.getRoot());
        graph.setClickTapeListener(tape -> tapeAdder.viewTape(tape));
        refreshJPanelJTabbed(contentTree, graph.getGraph());

        refreshJPanelJTabbed(contentTapes, visualizer);
    }

    private void cleanGraph()
    {
        refreshJPanelJTabbed(contentTree, jPanelEmpty);
    }

    private void refreshJPanelJTabbed(JPanel tabbed, JPanel content)
    {
        tabbed.removeAll();
        tabbed.add(content);
        tabbed.revalidate();
        tabbed.repaint();
    }
}
