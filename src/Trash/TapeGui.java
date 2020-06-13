package Trash;

import javax.swing.*;

public class TapeGui extends JFrame
{
    public static final int SIZE_CELL_TABLE = 40;

    public TapeGui()
    {
        /*TableColumnModel columnModel = table.getColumnModel();
        LoaderTape loaderTape = new LoaderTape(model, columnModel);
        loaderTape.execute();*/
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new TapeGui();
            }
        });
    }
}
