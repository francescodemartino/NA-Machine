package Gui.Body.Head.Table;

import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Table extends JPanel implements RefreshTable
{
    private static int FONT_SIZE = 15;
    private static int ROW_HEIGHT = 35;

    private JTable table;
    private DefaultTableModel model;
    private InstructionManager instructionManager;
    private ActionRefresh actionRefresh;

    public Table(InstructionManager instructionManager)
    {
        this.instructionManager = instructionManager;
        createTable();
        configureStyleTable();
        configureTable();
        configureRenderedData();
        addListenerClick();
    }

    private void createTable()
    {
        String[] columnTitles = {"i", "Configuration", "Action", "Description"};
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
        table.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setResizable(false);

        table.setCellSelectionEnabled(false);

        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBorder(new LineBorder(Color.gray));

        ((JLabel)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

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
        model.setNumRows(instructionManager.size());
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = null;

                List<InstructionView> list = instructionManager.getListInstructionView();

                switch (column)
                {
                    case 0:
                        c = super.getTableCellRendererComponent(table, row, isSelected, hasFocus, row, column);
                        c.setBackground(new Color(245, 245, 245));
                        c.setFont(new Font("Arial", Font.ITALIC, FONT_SIZE));
                        break;
                    case 1:
                        c = super.getTableCellRendererComponent(table, list.get(row).getInitialState() + "  " + list.get(row).getInitialCharacter(), isSelected, hasFocus, row, column);
                        c.setBackground(row%2==0 ? Color.white : new Color(242, 242, 242));
                        break;
                    case 2:
                        c = super.getTableCellRendererComponent(table, list.get(row).getTransitionState() + "  " + list.get(row).getTransitionCharacter() + "  " + list.get(row).getTransitionDirection().name().substring(0, 1), isSelected, hasFocus, row, column);
                        c.setBackground(row%2==0 ? Color.white : new Color(242, 242, 242));
                        break;
                    case 3:
                        c = super.getTableCellRendererComponent(table, list.get(row).getDescription().trim(), isSelected, hasFocus, row, column);
                        c.setBackground(row%2==0 ? Color.white : new Color(242, 242, 242));
                        break;
                }
                return c;
            }
        });

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addListenerClick()
    {
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                if(evt.getClickCount() == 2)
                {
                    switch (col)
                    {
                        case 1:
                            DialogUpdateConfiguration dialogUpdateConfiguration = new DialogUpdateConfiguration(instructionManager, row);
                            dialogUpdateConfiguration.showDialog();
                            break;
                        case 2:
                            DialogUpdateAction dialogUpdateAction = new DialogUpdateAction(instructionManager, row);
                            dialogUpdateAction.showDialog();
                            break;
                        case 3:
                            DialogUpdateDescription dialogUpdateDescription = new DialogUpdateDescription(instructionManager, row);
                            dialogUpdateDescription.showDialog();
                            break;
                    }
                    callActionRefresh();
                }
            }
        });
    }

    @Override
    public void refreshGUI()
    {
        model.setNumRows(instructionManager.size());
        model.fireTableDataChanged();
    }

    public void addInstruction()
    {
        DialogCreateConfiguration dialogCreateConfiguration = new DialogCreateConfiguration(instructionManager, this);
        dialogCreateConfiguration.showDialog();
        callActionRefresh();
    }

    public void removeInstruction()
    {
        int row = table.getSelectedRow();
        if(row >= 0)
        {
            instructionManager.removeInstruction(instructionManager.getListInstructionView().get(row));
            refreshGUI();
        }
    }

    private void callActionRefresh()
    {
        if(actionRefresh != null)
        {
            actionRefresh.doRefresh();
        }
    }

    public void addListenerActionRefresh(ActionRefresh actionRefresh)
    {
        this.actionRefresh = actionRefresh;
    }
}
