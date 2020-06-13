package Launcher.File;

import mdnd.Instruction.InstructionManager;
import mdnd.Instruction.InstructionView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class FileCreator
{
    private JFrame context;
    private File xmlFile;
    private InstructionManager instructionManager;
    private boolean saved = false;

    public FileCreator(JFrame context, InstructionManager instructionManager, File xmlFile)
    {
        this.context = context;
        this.instructionManager = instructionManager;
        this.xmlFile = xmlFile;
    }

    public File saveFile()
    {
        if(xmlFile == null)
        {
            FileChooserMdtND fileChooser = new FileChooserMdtND();
            fileChooser.showSaveDialog(context);
            File fileChoose = fileChooser.getSelectedFile();
            if(fileChoose != null)
            {
                manageExtension(fileChoose);
            }
        }
        if(xmlFile != null)
        {
            saved = true;
            createXml();
        }

        return xmlFile;
    }

    public boolean isSaved()
    {
        return saved;
    }

    private void createXml()
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);
            Element states = doc.createElement("states");
            rootElement.appendChild(states);
            Element characters = doc.createElement("characters");
            rootElement.appendChild(characters);
            Element instructions = doc.createElement("instructions");
            rootElement.appendChild(instructions);

            for(String stateString : instructionManager.getListState())
            {
                Element state = doc.createElement("state");
                state.setTextContent(stateString);
                states.appendChild(state);
            }
            for(char characterChar : instructionManager.getListChar())
            {
                Element character = doc.createElement("char");
                character.setTextContent(Character.toString(characterChar));
                characters.appendChild(character);
            }
            for(InstructionView instructionView : instructionManager.getListInstructionView())
            {
                Element instruction = doc.createElement("instruction");
                instructions.appendChild(instruction);

                Element state = doc.createElement("state");
                state.setTextContent(instructionView.getInitialState());
                instruction.appendChild(state);
                Element character = doc.createElement("character");
                character.setTextContent(Character.toString(instructionView.getInitialCharacter()));
                instruction.appendChild(character);
                Element stateTransition = doc.createElement("state_transition");
                stateTransition.setTextContent(instructionView.getTransitionState());
                instruction.appendChild(stateTransition);
                Element characterTransition = doc.createElement("character_transition");
                characterTransition.setTextContent(Character.toString(instructionView.getTransitionCharacter()));
                instruction.appendChild(characterTransition);
                Element direction = doc.createElement("direction");
                direction.setTextContent(instructionView.getTransitionDirection().name());
                instruction.appendChild(direction);
                Element description = doc.createElement("description");
                description.setTextContent(instructionView.getDescription());
                instruction.appendChild(description);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException | TransformerException e)
        {
            e.printStackTrace();
        }
    }

    private void manageExtension(File file)
    {
        boolean hasExtension = file.getName().indexOf(".") != -1;
        if(hasExtension)
        {
            xmlFile = file;
        }
        else
        {
            xmlFile = new File(file.getAbsolutePath() + ".mdtnd");
        }
    }
}
