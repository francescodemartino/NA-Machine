package Gui.Body.Foot.Graph;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.util.TreeUtils;

import java.awt.*;
import java.util.Collection;

/**
 * Scaricata da https://stackoverflow.com/questions/13408980/how-to-enforce-a-custom-layout-in-jung-graph
 * Permette di trasformare un albero verticale in orizzontale
 */
class HorizontalTreeLayout<V, E> extends TreeLayout<V, E>
{

    public HorizontalTreeLayout(Forest<V, E> g)
    {
        super(g);
    }

    @Override
    protected void buildTree() {
        this.m_currentPoint = new Point(0, 20);
        Collection<V> roots = TreeUtils.getRoots(graph);
        if (roots.size() > 0 && graph != null) {
            calculateDimensionY(roots);
            for (V v : roots) {
                calculateDimensionY(v);
                m_currentPoint.y += this.basePositions.get(v) / 2 + this.distY;
                buildTree(v, this.m_currentPoint.y);
            }
        }
    }

    @Override
    protected void buildTree(V v, int y) {
        if (!alreadyDone.contains(v)) {
            alreadyDone.add(v);

            this.m_currentPoint.x += this.distX;
            this.m_currentPoint.y = y;

            this.setCurrentPositionFor(v);

            int sizeYofCurrent = basePositions.get(v);

            int lastY = y - sizeYofCurrent / 2;

            int sizeYofChild;
            int startYofChild;

            for (V element : graph.getSuccessors(v)) {
                sizeYofChild = this.basePositions.get(element);
                startYofChild = lastY + sizeYofChild / 2;
                buildTree(element, startYofChild);
                lastY = lastY + sizeYofChild + distY;
            }
            this.m_currentPoint.x -= this.distX;
        }
    }

    private int calculateDimensionY(V v) {
        int size = 0;
        int childrenNum = graph.getSuccessors(v).size();

        if (childrenNum != 0) {
            for (V element : graph.getSuccessors(v)) {
                size += calculateDimensionY(element) + distY;
            }
        }
        size = Math.max(0, size - distY);
        basePositions.put(v, size);

        return size;
    }

    private int calculateDimensionY(Collection<V> roots) {
        int size = 0;
        for (V v : roots) {
            int childrenNum = graph.getSuccessors(v).size();

            if (childrenNum != 0) {
                for (V element : graph.getSuccessors(v)) {
                    size += calculateDimensionY(element) + distY;
                }
            }
            size = Math.max(0, size - distY);
            basePositions.put(v, size);
        }

        return size;
    }

}