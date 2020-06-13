package Gui.Body.Foot.Tapes;

import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class TapesTable extends JPanel
{
    private static int ROW_HEIGHT = 62;

    private JTable table;
    private DefaultTableModel model;
    private List<Tape> listTape;
    private TapesContainerTableManager tapesContainerTableManager;

    public TapesTable(List<Tape> listTape, TapesContainerTableManager tapesContainerTableManager)
    {
        this.listTape = listTape;
        this.tapesContainerTableManager = tapesContainerTableManager;
        createTable();
        configureStyleTable();
        configureTable();
        configureRenderedData();
    }

    private void createTable()
    {
        String[] columnTitles = {""};
        model = new DefaultTableModel(columnTitles, 0);
        table = new JTable(model);
    }

    private void configureStyleTable()
    {
        /*try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        table.setCellSelectionEnabled(true);

        table.setTableHeader(null);

        table.setRowHeight(ROW_HEIGHT);
        table.setGridColor(new Color(230, 230, 230));
    }

    private void configureTable()
    {
        setLayout(new BorderLayout());
        table.setDefaultEditor(Object.class, null);
        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane);
    }

    private void configureRenderedData()
    {
        TapeCellRendererEditor tapeCellRendererEditor = new TapeCellRendererEditor(tapesContainerTableManager);
        table.setDefaultRenderer(Object.class, tapeCellRendererEditor);
        table.setDefaultEditor(Object.class, tapeCellRendererEditor);
    }

    public void defineFilter(RowFilter<TableModel, Integer> rowFilter)
    {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>((table.getModel()));
        sorter.setRowFilter(rowFilter);
        table.setRowSorter(sorter);
    }

    public void setRowTable(int row)
    {
        model.setNumRows(row);
    }

    public void refreshGUI()
    {
        SwingUtilities.invokeLater(() ->
        {
            model.fireTableDataChanged();
        });
    }

    public void refreshSize()
    {
        model.setNumRows(listTape.size());
    }
}
