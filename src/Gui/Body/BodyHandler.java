package Gui.Body;

import Gui.Body.Foot.FootHandler;
import Gui.Body.Head.HeadHandler;
import Gui.Body.Rudder.RudderHandler;
import Gui.Body.Rudder.RudderListener;
import Gui.Body.Tape.TapeHandler;
import Gui.Body.Tape.TapeLoaderThread;
import Gui.Body.Tape.TapeLoaderThreadListener;
import Gui.Body.Tape.TapeOperation;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.MdtND;
import mdnd.Machine.MdtNDThreadHandler;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.awt.*;

public class BodyHandler extends JPanel
{
    public static final int MAX_TIME_SLIDER = 3000;
    public static final int HEIGHT_HEAD = 320;
    public static final int HEIGHT_RUDDER = 50;
    public static final int HEIGHT_TAPE = 80;
    public static final int HEIGHT_FOOT = 460;
    public static final int HEIGHT = HEIGHT_HEAD + HEIGHT_RUDDER + HEIGHT_TAPE + HEIGHT_FOOT;

    private MdtND mdtND;
    private MdtNDThreadHandler mdtNDThreadHandler;
    private InstructionManager instructionManager;
    private TapeLoaderThread tapeLoaderThread;
    private Tape firstTape;
    private HeadHandler headHandler;
    private RudderHandler rudderHandler;
    private TapeHandler tapeHandler;
    private FootHandler footHandler;
    private boolean isMdtNDCreated = false;
    private boolean isMdtNDPaused = false;
    private int level;

    public BodyHandler(InstructionManager instructionManager)
    {
        this.instructionManager = instructionManager;
        createFirstTape();
        //createTapeLoader();
        createMdtNDThreadHandler();
        createComponents();
        configureContainerHandler();
        setRudderHandler();
    }

    private void createFirstTape()
    {
        firstTape = new Tape(instructionManager, "");
    }

    private void createTapeLoader()
    {
        tapeLoaderThread = new TapeLoaderThread();
        tapeLoaderThread.setJumpWaitOneTime(true);
        tapeLoaderThread.execute();
    }

    private void createMdtNDThreadHandler()
    {
        mdtNDThreadHandler = new MdtNDThreadHandler();
        mdtNDThreadHandler.start();
    }

    private void createComponents()
    {
        headHandler = new HeadHandler(instructionManager);
        rudderHandler = new RudderHandler();
        tapeHandler = new TapeHandler(instructionManager, tapeLoaderThread);
        tapeHandler.viewTape(firstTape);
        footHandler = new FootHandler(instructionManager, tapeHandler);
        /*tapeLoaderThread.addListener(() ->
        {
            footHandler.executeAfterTapeLoaderThreadFinish();
        });*/
    }

    private void configureContainerHandler()
    {
        Panel body = new Panel();
        GroupLayout groupLayout = new GroupLayout(body);
        body.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(headHandler)
                                .addComponent(rudderHandler)
                                .addComponent(tapeHandler)
                                .addComponent(footHandler)
                        )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(headHandler,HEIGHT_HEAD - 20, HEIGHT_HEAD, HEIGHT_HEAD + 20)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(rudderHandler, HEIGHT_RUDDER - 1, HEIGHT_RUDDER, HEIGHT_RUDDER + 1)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(tapeHandler, HEIGHT_TAPE - 1, HEIGHT_TAPE, HEIGHT_TAPE + 1)
                        )
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(footHandler, HEIGHT_FOOT - 40, HEIGHT_FOOT, HEIGHT_FOOT + 200)
                        )
        );

        setLayout(new BorderLayout());
        add(body);
    }

    private void setRudderHandler()
    {
        level = RudderHandler.DEFAULT_PERCENTAGE_SLIDER;

        rudderHandler.addListener(new RudderListener()
        {
            @Override
            public void onClickPlay()
            {
                if(isMdtNDCreated)
                {
                    if(isMdtNDPaused)
                    {
                        isMdtNDPaused = false;
                        mdtNDThreadHandler.exe(mdtND);
                        tapeHandler.setEnabled(false);
                    }
                }
                else
                {
                    isMdtNDCreated = true;
                    isMdtNDPaused = false;
                    mdtND = new MdtND(instructionManager, firstTape);
                    mdtND.addRudder(rudderHandler);
                    firstTape.addExecutionMdtND(mdtND);
                    setSleepTimeMdtND(level);
                    mdtNDThreadHandler.exe(mdtND);
                    footHandler.startMdtND(mdtND);
                    setListenerMdtND();
                    tapeHandler.setEnabled(false);
                }
            }

            @Override
            public void onClickPause()
            {
                if(isMdtNDCreated && !isMdtNDPaused)
                {
                    isMdtNDPaused = true;
                    mdtND.pause();
                    tapeHandler.setEnabled(true);
                }
            }

            @Override
            public void onClickStep()
            {
                if(isMdtNDCreated && isMdtNDPaused)
                {
                    mdtND.step();
                }
            }

            @Override
            public void onClickStop()
            {
                if(isMdtNDCreated)
                {
                    isMdtNDCreated = false;
                    mdtND.pause();
                    footHandler.stopMdtND();
                    createFirstTape();
                    //tapeLoaderThread.exe();
                    tapeHandler.viewTape(firstTape);
                    tapeHandler.setEnabled(true);
                }
            }

            @Override
            public void onChangeSlider(int level)
            {
                BodyHandler.this.level = level;
                setSleepTimeMdtND(level);
            }
        });
    }

    private void setListenerMdtND()
    {
        mdtND.setListenerAllTapes(tapes ->
        {
            footHandler.actionWhenAllTapes(tapes);
            //tapeLoaderThread.exe();
            footHandler.executeAfterTapeLoaderThreadFinish();
        });
        mdtND.setListenerTapesAdded(tapes ->
        {
            footHandler.actionWhenTapesAdded(tapes);
        });
    }

    private void setSleepTimeMdtND(int percentage)
    {
        if(mdtND != null)
        {
            mdtND.setSleepTime(MAX_TIME_SLIDER - (MAX_TIME_SLIDER * percentage / 100));
        }
    }
}
