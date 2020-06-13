package Trash;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Gui extends JFrame
{
    public Gui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fatto

        final JTable table; //fatto

        String[] columnTitles = { "", "A", "B", "C", "D" }; //fatto

        DefaultTableModel model = new DefaultTableModel(columnTitles, 10); //fatto

        model.setNumRows(20); //fatto

        table = new JTable(model); //fatto

        table.getColumnModel().getColumn(0).setMaxWidth(40); //fatto
        table.getColumnModel().getColumn(0).setResizable(false); //fatto

        table.setCellSelectionEnabled(true); //fatto
        ListSelectionModel cellSelectionModel = table.getSelectionModel(); //fatto
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //fatto



        table.getTableHeader().setBackground(new Color(245, 245, 245)); //fatto
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); //fatto
        table.getTableHeader().setReorderingAllowed(false); //fatto
        TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer(); //fatto
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JComponent c = (JComponent)baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBorder(new LineBorder(Color.lightGray, 1));
                return c;
            }
        });



        table.setRowHeight(25); //fatto
        table.setGridColor(new Color(230, 230, 230)); //fatto
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c;
                if(column == 0)
                {
                    c = super.getTableCellRendererComponent(table, row, isSelected, hasFocus, row, column);
                    c.setBackground(new Color(245, 245, 245));
                    c.setFont(new Font("Arial", Font.ITALIC, 12));
                }
                else
                {
                    c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row%2==0 ? Color.white : new Color(242, 242, 242));
                }
                return c;
            }
        });

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class); //fatto
        renderer.setHorizontalAlignment(SwingConstants.CENTER); //fatto

        cellSelectionModel.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String selectedData = null;

                int[] selectedRow = table.getSelectedRows();
                int[] selectedColumns = table.getSelectedColumns();

                for (int i = 0; i < selectedRow.length; i++)
                {
                    for (int j = 0; j < selectedColumns.length; j++)
                    {
                        selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
                        table.setValueAt("ciao", selectedRow[i], selectedColumns[j]);
                    }
                }
                System.out.println("Selected: " + selectedData);
            }
        });

        add(new JScrollPane(table)); //fatto



        setSize(300, 200); //fatto
        setVisible(true); //fatto
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui();
            }
        });
    }
}
