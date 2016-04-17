package games.stendhal.server.core.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import games.stendhal.server.entity.npc.NPCStructure;



public class GenericNPCLoader {
	private String npcsPath = "./data/conf/npcs/speakerNPC.xml";
	private ArrayList<NPCStructure> npcsList =  new ArrayList<NPCStructure>();
	
	private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	
	public GenericNPCLoader(){
		
		DocumentBuilder dBuilder;
		try {
			// Open document
			File xmlFile = new File(npcsPath); 
			dBuilder = builderFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.normalize();
			
			// Get NPCs
			NodeList rootNodes = document.getElementsByTagName("npcs");
			Element rootElement = (Element) rootNodes.item(0);
			NodeList npcs = rootElement.getElementsByTagName("npc");
 
			
			String zone = getZone();

			// For each NPC
			for(int i=0; i<npcs.getLength(); i++){
				// Compare current zone to NPC's zone
				// If it is the same create NPC, skip otherwise
				Element currentNPC = (Element) npcs.item(i);
				if(!currentNPC.getAttribute("zone").equals(zone))
					continue;
				
				// Add NPC to list
				npcsList.add(i,new NPCStructure());
				
				// Get name
				npcsList.get(i).setName(currentNPC.getAttribute("name"));
				
				// Get class
				npcsList.get(i).setClassz(currentNPC.getAttribute("class"));
				
				// Get x,y coordinates
				if(!currentNPC.getAttribute("x").equals(""))
					npcsList.get(i).setX(Integer.parseInt(currentNPC.getAttribute("x")));
				if(!currentNPC.getAttribute("y").equals(""))
					npcsList.get(i).setY(Integer.parseInt(currentNPC.getAttribute("y")));
				
				// Get NPC's description
				npcsList.get(i).setDescription(currentNPC.getElementsByTagName("description").item(0).getTextContent());
				
				// Get Quest messages
				Element currentNode = (Element) currentNPC.getElementsByTagName("ai").item(0);
				currentNode = (Element) currentNode.getElementsByTagName("says").item(0);
				Element currentElement = (Element) currentNode.getElementsByTagName("quest").item(0);
				if(currentElement != null){
					npcsList.get(i).setQuest("true");
					npcsList.get(i).setIntroduceQuestMessage(currentElement.getAttribute("introduce"));
					npcsList.get(i).setDuringQuestMessage(currentElement.getAttribute("during"));
					npcsList.get(i).setCompletedQuestMessage(currentElement.getAttribute("completed"));
				}
					
				// Get Job description
				currentElement = (Element) currentNPC.getElementsByTagName("job").item(0);
				if(currentElement != null)
					npcsList.get(i).setJob(currentElement.getAttribute("message"));
				
				// Get help description
				currentElement = (Element) currentNPC.getElementsByTagName("help").item(0);
				if(currentElement != null)
					npcsList.get(i).setHelp(currentElement.getAttribute("message"));
				
				// Get replies
				currentElement = (Element) currentNPC.getElementsByTagName("ai").item(0);
				currentElement = (Element) currentElement.getElementsByTagName("says").item(0);
				NodeList replies = currentElement.getElementsByTagName("reply");
				for(int j=0; j<replies.getLength(); j++){
					Element currentReply = (Element) replies.item(j);
					npcsList.get(i).setReply(currentReply.getAttribute("keyword"), currentReply.getAttribute("message"));
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
	
	public ArrayList<NPCStructure> getNPCs(){
		return npcsList;
	}
	
	public String getZone() {
        try {
            FileReader file = new FileReader("./log/server.log");
            BufferedReader buffer = new BufferedReader(file);
            String line;
            String lastLine = "";

            while ((line = buffer.readLine()) != null) {
            	lastLine = line;
            }

            buffer.close();
            file.close();
            
            Pattern p = Pattern.compile("\\[(.*?)\\]");
            Matcher m = p.matcher(lastLine);
            
            while (m.find()) {
                lastLine = m.group(1).toString();
            }
            
            
            return lastLine;

        } catch (java.io.IOException e) {
            System.out.print("Error reading log. ");
            return "";
        }
    }
	
}
