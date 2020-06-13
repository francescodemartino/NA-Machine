package Gui.Body.Rudder;

import Gui.Utility;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RudderHandler extends JPanel
{
    public static final int DEFAULT_PERCENTAGE_SLIDER = 50;

    private JButton playButton;
    private JButton pauseButton;
    private JButton stepButton;
    private JButton stopButton;
    private JSlider slider;
    private RudderListener rudderListener;

    public RudderHandler()
    {
        configureLayout();
    }

    public void addListener(RudderListener rudderListener)
    {
        this.rudderListener = rudderListener;
        addListenerComponents();
    }

    private void configureLayout()
    {
        JPanel marginFirst = new JPanel();
        marginFirst.setMinimumSize(new Dimension(15, 20));
        JPanel marginSecond = new JPanel();
        marginSecond.setMinimumSize(new Dimension(50, 20));

        playButton = Utility.createButton("play.png");
        pauseButton = Utility.createButton("pause.png");
        stepButton = Utility.createButton("step.png");
        stopButton = Utility.createButton("stop.png");
        slider = new JSlider();
        slider.setValue(DEFAULT_PERCENTAGE_SLIDER);
        slider.setMinimum(0);
        slider.setMaximum(99);
        slider.setMinimumSize(new Dimension(400, 50));

        Panel rudder = new Panel();
        GroupLayout groupLayout = new GroupLayout(rudder);
        rudder.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(playButton)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(marginFirst)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(pauseButton)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(marginFirst)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(stepButton)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(marginFirst)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(stopButton)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(marginSecond)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(slider)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(playButton)
                                .addComponent(marginFirst)
                                .addComponent(pauseButton)
                                .addComponent(marginFirst)
                                .addComponent(stepButton)
                                .addComponent(marginFirst)
                                .addComponent(stopButton)
                                .addComponent(marginSecond)
                                .addComponent(slider)
                        )
        );

        add(rudder);
    }

    public void clickPause()
    {
        pauseButton.doClick();
    }

    private void addListenerComponents()
    {
        playButton.addActionListener(e -> rudderListener.onClickPlay());
        pauseButton.addActionListener(e -> rudderListener.onClickPause());
        stepButton.addActionListener(e -> rudderListener.onClickStep());
        stopButton.addActionListener(e -> rudderListener.onClickStop());
        slider.addChangeListener(e -> rudderListener.onChangeSlider(slider.getValue()));
    }
}