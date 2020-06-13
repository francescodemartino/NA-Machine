package Trash;

import Gui.Body.BodyHandler;
import Gui.Body.Head.HeadHandler;
import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import mdnd.Machine.RunningListener;
import mdnd.Machine.Tape;

import javax.swing.*;
import java.util.List;

public class UseTableOld
{
    public static void main(String[] args)
    {
        InstructionManager instructionManager  = new InstructionManager();
        instructionManager.addState("q0");
        instructionManager.addState("yes");
        instructionManager.addState("no");
        instructionManager.addState("q1");
        instructionManager.addState("q2");
        instructionManager.addState("q3");
        instructionManager.addState("q4");
        instructionManager.addState("q5");
        instructionManager.addState("q6");
        instructionManager.addChar('$');
        instructionManager.addChar('1');
        try
        {
            /*instructionManager.addInstruction("q0", '#', "q1", '#', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '$', "q1", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '$', "q1", '$', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '1', "q2", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '1', "q1", '$', Direction.RIGHT, "");*/
            instructionManager.addInstruction("q0", '#', "q1", '#', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '$', "q1", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q1", '1', "q2", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q2", '$', "q3", '$', Direction.RIGHT, "");
            instructionManager.addInstruction("q2", '$', "q6", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q3", '$', "q3", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q6", '1', "q5", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '1', "yes", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '$', "no", '$', Direction.LEFT, "");
            instructionManager.addInstruction("q6", '$', "q4", '$', Direction.LEFT, "a me non so");
            instructionManager.addInstruction("q5", '$', "q4", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q5", '1', "q4", '1', Direction.RIGHT, "");
            instructionManager.addInstruction("q4", '$', "q4", '$', Direction.LEFT, "ciao come va la vita");
            instructionManager.addInstruction("q4", '#', "q4", '#', Direction.LEFT, "ciao come va la vita");
            instructionManager.addInstruction("q4", '1', "q4", '$', Direction.RIGHT, "");
        }
        catch (IncorrectInputDataTableException e)
        {
            e.printStackTrace();
        }

        /*JFrame jFrame = new JFrame();
        jFrame.setSize(HeadHandler.WIDTH, 580);
        jFrame.add(new HeadHandler(instructionManager));
        jFrame.setVisible(true);*/

        JFrame jFrame = new JFrame();
        jFrame.setSize(HeadHandler.WIDTH, BodyHandler.HEIGHT);
        jFrame.add(new BodyHandler(instructionManager));
        jFrame.setVisible(true);

        /*new java.util.Timer().schedule(
                new java.util.TimerTask()
                {
                    @Override
                    public void run()
                    {
                        MdtND mdtND = new MdtND(instructionManager, "$$$$1");
                        //mdtND.setListenerAllTapes(allTapesListener);
                        //mdtND.setListenerTapesAdded(tapesAddedListener);


                        Graph graph = new Graph(mdtND.getRoot());
                        JFrame jFrameGraph = new JFrame();
                        jFrameGraph.setSize(HeadHandler.WIDTH, 580);
                        jFrameGraph.add(graph.getGraph());
                        //jFrameGraph.setVisible(true);

                        mdtND.setListenerTapesAdded(tapes ->
                        {
                            tapes.forEach(tape -> graph.addNode(tape));
                        });

                        mdtND.setListenerAllTapes(tapes ->
                        {
                            graph.refreshGraph();
                            allTapesListener.onExe(tapes);
                        });


                        try
                        {
                            mdtND.start();
                        }
                        catch (IncorrectPositionException e)
                        {
                            e.printStackTrace();
                        }
                        catch (IncorrentInstructionException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                2000
        );*/
    }

    private static RunningListener tapesAddedListener = new RunningListener()
    {
        @Override
        public void onExe(List<Tape> tapes)
        {
            System.out.println("\n\n---------------------------------------------------------------------------------");
            for(int i=0; i<tapes.size(); i=i+1)
            {
                String state = "";
                if(tapes.get(i).getState() == Tape.STATE_IN_EXE)
                {
                    state = "IN_EXE";
                }
                else if(tapes.get(i).getState() == Tape.STATE_CLONED)
                {
                    state = "CLONED";
                }
                else if(tapes.get(i).getState() == Tape.STATE_NOT_NEXT_STATE)
                {
                    state = "NOT_NEXT_STATE";
                }
                else if(tapes.get(i).getState() == Tape.STATE_NOT_CORRECT_POSITION)
                {
                    state = "NOT_CORRECT_POSITION";
                }
                System.out.println(i + ") " + state + " - " + tapes.get(i).getStep() + " - " + tapes.get(i).getCurrentPosition() + " - " + tapes.get(i).getCurrentState() + " - " + tapes.get(i).getCharacters());
            }
            System.out.println("---------------------------------------------------------------------------------\n\n");
        }
    };

    private static RunningListener allTapesListener = new RunningListener()
    {
        @Override
        public void onExe(List<Tape> tapes)
        {
            for(int i=0; i<tapes.size(); i=i+1)
            {
                String state = "";
                if(tapes.get(i).getState() == Tape.STATE_IN_EXE)
                {
                    state = "IN_EXE";
                }
                else if(tapes.get(i).getState() == Tape.STATE_CLONED)
                {
                    state = "CLONED";
                }
                else if(tapes.get(i).getState() == Tape.STATE_NOT_NEXT_STATE)
                {
                    state = "NOT_NEXT_STATE";
                }
                else if(tapes.get(i).getState() == Tape.STATE_NOT_CORRECT_POSITION)
                {
                    state = "NOT_CORRECT_POSITION";
                }
                System.out.println(i + ") " + state + " - " + tapes.get(i).getStep() + " - " + tapes.get(i).getCurrentPosition() + " - " + tapes.get(i).getCurrentState() + " - " + tapes.get(i).getCharacters());
            }
            System.out.println("");
        }
    };
}
