package Launcher.File;

import mdnd.Exception.IncorrectInputDataTableException;
import mdnd.Instruction.Direction;
import mdnd.Instruction.InstructionManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class FileLoader
{
    private File xmlFile;
    private boolean lastFile = false;

    public FileLoader(String path)
    {
        xmlFile = new File(path);
    }

    public FileLoader(File xmlFile)
    {
        this.xmlFile = xmlFile;
    }

    public InstructionManager getInstructionManager()
    {
        InstructionManager instructionManager = new InstructionManager();
        Document document = null;
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            lastFile = false;
            return instructionManager;
        }
        addStates(instructionManager, document);
        addCharacters(instructionManager, document);
        addInstructions(instructionManager, document);
        lastFile = true;

        return instructionManager;
    }

    public boolean existsLastFileOpen()
    {
        return lastFile;
    }

    private void addStates(InstructionManager instructionManager, Document document)
    {
        NodeList states = document.getElementsByTagName("state");
        for(int i=0; i<states.getLength(); i=i+1)
        {
            instructionManager.addState(states.item(i).getTextContent());
        }
    }

    private void addCharacters(InstructionManager instructionManager, Document document)
    {
        NodeList characters = document.getElementsByTagName("char");
        for(int i=0; i<characters.getLength(); i=i+1)
        {
            instructionManager.addChar(characters.item(i).getTextContent().charAt(0));
        }
    }

    private void addInstructions(InstructionManager instructionManager, Document document)
    {
        NodeList instructions = document.getElementsByTagName("instruction");
        for(int i=0; i<instructions.getLength(); i=i+1)
        {
            Element element = (Element)instructions.item(i);
            addInstructionSupport(
                    instructionManager,
                    element,
                    element.getElementsByTagName("state").item(0).getTextContent(),
                    element.getElementsByTagName("character").item(0).getTextContent().charAt(0),
                    element.getElementsByTagName("state_transition").item(0).getTextContent(),
                    element.getElementsByTagName("character_transition").item(0).getTextContent().charAt(0),
                    convertStringToDirection(element.getElementsByTagName("direction").item(0).getTextContent()),
                    element.getElementsByTagName("description").item(0).getTextContent()
            );
        }
    }

    private void addInstructionSupport(InstructionManager instructionManager, Element element, String state, char character, String stateTransition, char characterTransition, Direction directionTransition, String description)
    {
        try
        {
            instructionManager.addInstruction(
                    state,
                    character,
                    stateTransition,
                    characterTransition,
                    directionTransition,
                    description
            );
        }
        catch (IncorrectInputDataTableException e)
        {
            instructionManager.addState(state);
            instructionManager.addChar(character);
            instructionManager.addState(stateTransition);
            instructionManager.addChar(characterTransition);
            addInstructionSupport(
                    instructionManager,
                    element,
                    state,
                    character,
                    stateTransition,
                    characterTransition,
                    directionTransition,
                    description
            );
        }
    }

    private Direction convertStringToDirection(String direction)
    {
        if(direction.equals(Direction.RIGHT.toString()))
        {
            return Direction.RIGHT;
        }
        else if(direction.equals(Direction.LEFT.toString()))
        {
            return Direction.LEFT;
        }
        else
        {
            return Direction.STOP;
        }
    }
}
