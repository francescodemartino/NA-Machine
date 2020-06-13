package Gui.Body.Tape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

public class Circle extends JPanel
{
    private Color color;

    public Circle(Color color)
    {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Shape circle = new Ellipse2D.Double(0, 2, 26, 26);
        Shape circleFill = new Ellipse2D.Double(0, 2, 26, 26);
        g2.setColor(color);
        g2.fill(circleFill);
        g2.setColor(Color.black);
        g2.draw(circle);
    }
}
