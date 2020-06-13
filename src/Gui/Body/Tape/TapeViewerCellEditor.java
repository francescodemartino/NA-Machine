package Gui.Body.Tape;

import mdnd.Instruction.InstructionManager;
import mdnd.Machine.ExecutionMdtND;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;

public class TapeViewerCellEditor extends DefaultCellEditor implements TableCellEditor
{
    private InstructionManager instructionManager;
    private List<Character> characters;
    private ExecutionMdtND executionMdtND;
    private  int column;
    private JTextField editorCell;

    public TapeViewerCellEditor(InstructionManager instructionManager, List<Character> characters, ExecutionMdtND executionMdtND)
    {
        super(new JTextField());
        this.instructionManager = instructionManager;
        this.characters = characters;
        this.executionMdtND = executionMdtND;
        editorCell = (JTextField)editorComponent;
        editorCell.setFont(new Font("Arial", Font.PLAIN, 15));
        editorCell.setHorizontalAlignment(JTextField.CENTER);
    }

    public void addExecutionMdtND(ExecutionMdtND executionMdtND)
    {
        this.executionMdtND = executionMdtND;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.column = column;
        editorCell.setText(Character.toString(characters.get(column).charValue()));
        editorCell.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                if (editorCell.getText().length() >= 1)
                {
                    e.consume();
                }
            }
        });
        return editorCell;
    }

    @Override
    public Object getCellEditorValue()
    {
        String cellText = editorCell.getText();
        if(cellText.length() == 0 || !instructionManager.hasCharacter(cellText.charAt(0)))
        {
            openMessage();
        }
        else
        {
            characters.set(column, cellText.charAt(0));
        }
        return editorCell.getText();
    }

    @Override
    public boolean isCellEditable(EventObject aAnEvent)
    {
        if(executionMdtND != null && executionMdtND.isInExecution())
        {
            return false;
        }

        boolean cellEditable = super.isCellEditable(aAnEvent);

        if (cellEditable && aAnEvent instanceof MouseEvent)
        {
            cellEditable = ((MouseEvent)aAnEvent).getClickCount() == 2;
        }

        return cellEditable;
    }

    private void openMessage()
    {
        JOptionPane.showMessageDialog(null,
                "Valore non consentito",
                "Attenzione",
                JOptionPane.PLAIN_MESSAGE);
    }
}
