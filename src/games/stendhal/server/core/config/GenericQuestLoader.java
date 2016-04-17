package games.stendhal.server.core.config;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import games.stendhal.server.maps.quests.QuestStructure;



public class GenericQuestLoader {
	private String questsPath = "./data/conf/quests/quests.xml";
	private ArrayList<QuestStructure> questsList =  new ArrayList<QuestStructure>();
	
	private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	
	public GenericQuestLoader(){
		
		DocumentBuilder dBuilder;
		try {
			// Open document
			File xmlFile = new File(questsPath); 
			dBuilder = builderFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.normalize();
			
			// Get NPCs
			NodeList rootNodes = document.getElementsByTagName("quests");
			Element rootElement = (Element) rootNodes.item(0);
			NodeList quests = rootElement.getElementsByTagName("quest");
 

			// For each NPC
			for(int i=0; i<quests.getLength(); i++){
				// Compare current zone to NPC's zone
				// If it is the same create NPC, skip otherwise
				Element currentQuest = (Element) quests.item(i);
				
				// Add NPC to list
				questsList.add(i,new QuestStructure());
				
				// Get name
				questsList.get(i).setName(currentQuest.getAttribute("name"));
				
				// Get NPC's description
				questsList.get(i).setDescription(currentQuest.getElementsByTagName("description").item(0).getTextContent());
				
				// Get participants NPCs
				NodeList npcs = currentQuest.getElementsByTagName("npc");
				for(int j=0; j<npcs.getLength(); j++){
					Element currentNPC = (Element) npcs.item(j);
					questsList.get(i).addNPC(currentNPC.getAttribute("name"));
				}
									
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<QuestStructure> getQuests(){
		return questsList;
	}
	
	
	
}
