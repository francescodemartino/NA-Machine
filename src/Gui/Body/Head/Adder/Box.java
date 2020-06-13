package Gui.Body.Head.Adder;

import Gui.Component.AddRemove.Button;
import Gui.Component.AddRemove.Listener;
import mdnd.Exception.StateCharacterUsedException;
import mdnd.Instruction.InstructionManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

abstract public class Box extends JPanel implements JTextFieldLister
{
    private static int FONT_SIZE = 15;
    private static int ROW_HEIGHT = 25;

    protected InstructionManager instructionManager;
    private JTable table;
    private DefaultTableModel model;
    protected java.util.List list;
    private int limitLengthJTextField;

    public Box(InstructionManager instructionManager, List list, int limitLengthJTextField)
    {
        this.instructionManager = instructionManager;
        this.list = list;
        this.limitLengthJTextField = limitLengthJTextField;
        String[] columnTitles = {getNameColumn()};
        model = new DefaultTableModel(columnTitles, 0);
        table = new JTable(model);
        configureStyleTable();
        defineDataTable();
        configureTable();
    }

    abstract protected String getNameColumn();

    abstract void removeElement(int row) throws StateCharacterUsedException;

    abstract void orderElements();

    private void configureStyleTable()
    {
        table.setSelectionForeground(Color.black);

        table.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(ROW_HEIGHT);
        table.setGridColor(new Color(230, 230, 230));
    }

    private void configureTable()
    {
        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);
        table.setDefaultEditor(Object.class, null);
        JScrollPane jScrollPane = new JScrollPane(table);

        Button buttonAdder = new Button(getListenerButton());

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane)
                                .addComponent(buttonAdder)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(buttonAdder, 34, 35, 36)
                        )
        );
    }

    public void refreshGUI()
    {
        model.setNumRows(list.size());
        model.fireTableDataChanged();
    }

    private Listener getListenerButton()
    {
        return new Listener()
        {
            @Override
            protected void actionPlusButton()
            {
                DialogAddElement dialogAddElement = new DialogAddElement("Aggiunti", Box.this, limitLengthJTextField);
                dialogAddElement.showDialog();
                refreshGUI();
            }

            @Override
            protected void actionLessButton()
            {
                if(removeLine())
                {
                    refreshGUI();
                }
                else
                {
                    JOptionPane.showMessageDialog(Box.this, "Attenzione, da questo elemento dipendono delle istruzioni\nPrima di eliminarlo bisogna rimuovere tutte le dipendenze", "Attenzione", JOptionPane.UNDEFINED_CONDITION);
                }
            }

            @Override
            protected void actionOrderButton()
            {
                orderElements();
            }
        };
    }

    private boolean removeLine()
    {
        int row = table.getSelectedRow();
        if(row >= 0)
        {
            try
            {
                removeElement(row);
                return true;
            }
            catch (StateCharacterUsedException e)
            {
                return false;
            }
        }
        return false;
    }

    private void defineDataTable()
    {
        model.setNumRows(list.size());
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Object[] arrayString = list.toArray();
                Component c = super.getTableCellRendererComponent(table, arrayString[row].toString(), isSelected, hasFocus, row, column);
                c.setBackground(row%2==0 ? Color.white : new Color(242, 242, 242));
                return c;
            }
        });

        table.getTableHeader().setDefaultRenderer(new DefaultRenderer(table));

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private class DefaultRenderer extends DefaultTableCellRenderer
    {
        DefaultTableCellRenderer renderer;

        public DefaultRenderer(JTable table)
        {
            renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            JComponent c = (JComponent) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBorder(new LineBorder(Color.lightGray, 1));
            return c;
        }
    }
}
