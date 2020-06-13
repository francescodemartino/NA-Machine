package Gui.Body.Foot.Graph;

import mdnd.Instruction.InstructionManager;
import mdnd.Machine.Tape;
import org.apache.commons.collections15.Transformer;

import java.awt.*;

class TransformerVertexPaint implements Transformer<Tape, Paint>
{
    @Override
    public Paint transform(Tape tape)
    {
        switch (tape.getState())
        {
            case Tape.STATE_IN_EXE:
                return Color.WHITE;
            case Tape.STATE_CLONED:
                return Color.CYAN;
            case Tape.STATE_NOT_CORRECT_POSITION:
                return Color.ORANGE;
            case Tape.STATE_NOT_NEXT_STATE:
                return tape.isYesLastState() ? Color.GREEN : (tape.isNoLastState() ? new Color(255, 153, 153) : Color.YELLOW);
        }
        return Color.GRAY;
    }
}