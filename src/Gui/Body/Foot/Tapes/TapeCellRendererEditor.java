package Gui.Body.Foot.Tapes;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TapeCellRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor
{
    private TapesContainerTableManager tapesContainerTableManager;

    TapeCellRendererEditor(TapesContainerTableManager tapesContainerTableManager)
    {
        this.tapesContainerTableManager = tapesContainerTableManager;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column)
    {
        return tapesContainerTableManager.getTapeContainer(row);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        return tapesContainerTableManager.getTapeContainer(row);
    }

    @Override
    public Object getCellEditorValue()
    {
        return null;
    }
}