package Gui.Body.Tape;

import mdnd.Instruction.InstructionManager;
import mdnd.Machine.ExecutionMdtND;
import mdnd.Machine.Tape;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class TapeViewer
{
    public static final int SIZE_CELL_TABLE = 40;

    private InstructionManager instructionManager;
    private List<Character> characters;
    private ExecutionMdtND executionMdtND;
    private TapeViewerCellEditor tapeViewerCellEditorOne;
    private TapeViewerCellEditor tapeViewerCellEditorTwo;
    private TapeViewerFocusListener tapeViewerFocusListener;
    private TapeViewerFocusListener tapeViewerTableListener;
    private JPanel viewOne;
    private JPanel viewTwo;
    private JTable tableOne;
    private JTable tableTwo;
    private JScrollPane jScrollPaneOne;
    private JScrollPane jScrollPaneTwo;
    private DefaultTableModel model;
    private TableColumnModel columnModel;
    private int currentPosition;
    private String currentState;
    private int state;
    private int numberCharacters = 1;
    private boolean goon = true;


    public TapeViewer(InstructionManager instructionManager, List<Character> characters, ExecutionMdtND executionMdtND, int currentPosition, String currentState, int state)
    {
        this.instructionManager = instructionManager;
        this.characters = characters;
        this.executionMdtND = executionMdtND;
        this.currentPosition = currentPosition;
        this.currentState = currentState;
        this.state = state;
        viewOne = new JPanel();
        viewTwo = new JPanel();
        defineTable();
        configureTable();
    }

    public void addExecutionMdtND(ExecutionMdtND executionMdtND)
    {
        this.executionMdtND = executionMdtND;
        tapeViewerCellEditorOne.addExecutionMdtND(executionMdtND);
        tapeViewerCellEditorTwo.addExecutionMdtND(executionMdtND);
    }

    public void addFocusListener(TapeViewerFocusListener tapeViewerFocusListener)
    {
        this.tapeViewerFocusListener = tapeViewerFocusListener;
    }

    public void addFocusTableListener(TapeViewerFocusListener tapeViewerTableListener)
    {
        this.tapeViewerTableListener = tapeViewerTableListener;
    }

    public void removeFocusListener()
    {
        tapeViewerFocusListener = null;
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public String getCurrentState()
    {
        return currentState;
    }

    public int getState()
    {
        return state;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public void setCurrentState(String currentState)
    {
        this.currentState = currentState;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public void refreshGUI()
    {
        markIfEndOfTable();

        if(characters.size() > numberCharacters)
        {
            while (characters.size() > numberCharacters)
            {
                numberCharacters = numberCharacters + 1;
                model.addColumn(numberCharacters);
                refreshSizeTable();
            }
        }
        else if(characters.size() < numberCharacters)
        {
            numberCharacters = numberCharacters - (numberCharacters - characters.size());
            model.setColumnCount(numberCharacters);
            refreshSizeTable();
        }
        else
        {
            model.fireTableDataChanged();
        }

        if(tapeViewerFocusListener != null)
        {
            tapeViewerFocusListener.update();
        }

        if(tapeViewerTableListener != null)
        {
            tapeViewerTableListener.update();
        }

        goToEndOfTableIfNecessary();
    }

    private void markIfEndOfTable()
    {
        if(tapeViewerFocusListener != null && state == Tape.STATE_IN_EXE)
        {
            int maxVisibleArea = jScrollPaneOne.getHorizontalScrollBar().getVisibleAmount() + jScrollPaneOne.getHorizontalScrollBar().getValue();
            if(maxVisibleArea == jScrollPaneOne.getHorizontalScrollBar().getMaximum())
            {
                goon = true;
            }
            else
            {
                goon = false;
            }
        }
    }

    public void goToEndOfTableIfNecessary()
    {
        if (tapeViewerFocusListener != null && state == Tape.STATE_IN_EXE && goon)
        {
            SwingUtilities.invokeLater(() ->
            {
                jScrollPaneOne.getHorizontalScrollBar().setValue(jScrollPaneOne.getHorizontalScrollBar().getMaximum());
            });
        }
    }

    private void refreshSizeTable()
    {
        for (int i=0; i<model.getColumnCount(); i=i+1)
        {
            columnModel.getColumn(i).setMaxWidth(SIZE_CELL_TABLE);
        }
    }

    private void defineTable()
    {
        String[] columnTitles = {""};
        model = new DefaultTableModel(columnTitles, 1);

        tapeViewerCellEditorOne = new TapeViewerCellEditor(instructionManager, characters, executionMdtND);
        tapeViewerCellEditorTwo = new TapeViewerCellEditor(instructionManager, characters, executionMdtND);

        tableOne = new JTable(model);
        tableTwo = new JTable(model);
        defineFirstPart(tableOne, tapeViewerCellEditorOne);
        defineFirstPart(tableTwo, tapeViewerCellEditorTwo);

        columnModel = tableOne.getColumnModel();
        tableTwo.setColumnModel(columnModel);
        columnModel.getColumn(0).setMinWidth(SIZE_CELL_TABLE);
        columnModel.getColumn(0).setMaxWidth(SIZE_CELL_TABLE);

        configureCellTable();

        defineSecondPart(tableOne);
        defineSecondPart(tableTwo);
    }

    private void defineFirstPart(JTable table, TapeViewerCellEditor tapeViewerCellEditor)
    {
        table.getTableHeader().setUI(null);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setDefaultEditor(Object.class, tapeViewerCellEditor);
        table.setCellSelectionEnabled(false);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionForeground(Color.black);
        table.setFont(new Font("Arial", Font.PLAIN, 15));

        table.setRowHeight(SIZE_CELL_TABLE);
        table.setGridColor(new Color(240, 240, 240));
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    private void defineSecondPart(JTable table)
    {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void configureCellTable()
    {
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, characters.get(column), isSelected, hasFocus, row, column);
                int left = 0;
                int right = 0;
                if(column == 0)
                {
                    left = 1;
                }
                else if((characters.size() - 1) == column)
                {
                    right = 1;
                }
                ((JComponent)c).setBorder(BorderFactory.createMatteBorder(1, left, 1, right, new Color(125, 125, 125)));

                if(currentPosition == column)
                {
                    c.setBackground(new Color(39, 104, 254));
                }
                else
                {
                    c.setBackground(column%2==0 ? Color.white : new Color(242, 242, 242));
                }
                return c;
            }
        };

        tableOne.setDefaultRenderer(Object.class, tableCellRenderer);
        tableTwo.setDefaultRenderer(Object.class, tableCellRenderer);
    }

    private void configureTable()
    {
        jScrollPaneOne = new JScrollPane(tableOne);
        configureTableGeneric(jScrollPaneOne, viewOne);
        jScrollPaneTwo = new JScrollPane(tableTwo);
        configureTableGeneric(jScrollPaneTwo, viewTwo);
    }

    private void configureTableGeneric(JScrollPane jScrollPane, JPanel jPanel)
    {
        jPanel.setLayout(new BorderLayout());
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        jPanel.add(jScrollPane);
    }

    public JPanel first()
    {
        return viewOne;
    }

    public JPanel second()
    {
        return viewTwo;
    }
}
