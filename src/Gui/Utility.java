package Gui;

import javax.swing.*;
import java.awt.*;

public class Utility
{
    public static JButton createButton(String srcImage)
    {
        ImageIcon image = new ImageIcon(Utility.class.getResource("/img/" + srcImage));
        JButton button = new JButton("");
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setIcon(image);
        button.setMinimumSize(new Dimension(40, 40));
        return button;
    }
}
