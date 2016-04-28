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
	private String questsPath = "./data/conf/quests/quests2.xml";
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
				Element currentQuest = (Element) quests.item(i);
				
				// Add Quest to list
				questsList.add(i,new QuestStructure());
				
				// Get name
				questsList.get(i).setName(currentQuest.getAttribute("name"));
				
				// Get repeatable
				questsList.get(i).setRepeatable(currentQuest.getAttribute("repeatable").equals("true"));
				
				// Get Quests' description
				questsList.get(i).setDescription(currentQuest.getElementsByTagName("description").item(0).getTextContent());
				
				// Get Phases
				NodeList phases = currentQuest.getElementsByTagName("phase");
				for(int j=0; j<phases.getLength(); j++){
					// Add Phase to list
					questsList.get(i).addPhase();
					
					// Get Phase name
					Element currentPhase = (Element) phases.item(j);
					questsList.get(i).getPhase(j).setName(currentPhase.getAttribute("name"));
					
					// Set NPC for Phase
					questsList.get(i).getPhase(j).setNPC(currentPhase.getAttribute("npc"));
					
					// Get collectables items
					Element currentNode = (Element) currentPhase.getElementsByTagName("collectables").item(0);
					if(currentNode != null){
						NodeList collectableItems = currentNode.getElementsByTagName("item");
						for(int k=0; k<collectableItems.getLength(); k++){
							Element currentItem = (Element) collectableItems.item(k);
							questsList.get(i).getPhase(j).setCollectableItem(currentItem.getAttribute("name"), currentItem.getAttribute("quantity"));
						}
					}
					
					// Get items to create
					currentNode = (Element) currentPhase.getElementsByTagName("createItem").item(0);
					if(currentNode != null){
						questsList.get(i).getPhase(j).setItemToCreate(currentNode.getAttribute("itemToCreate"));
						questsList.get(i).getPhase(j).setItemToCreateMessage(currentNode.getAttribute("message"));
						NodeList ingredients = currentNode.getElementsByTagName("item");
						for(int k=0; k<ingredients.getLength(); k++){
							Element currentItem = (Element) ingredients.item(k);
							questsList.get(i).getPhase(j).setIngredients(currentItem.getAttribute("name"), currentItem.getAttribute("quantity"));
						}
					}
					
					// Get rewards
					currentNode = (Element) currentPhase.getElementsByTagName("rewards").item(0);
					if(currentNode != null){
						NodeList rewardItems = currentNode.getElementsByTagName("item");
						for(int k=0; k<rewardItems.getLength(); k++){
							Element currentItem = (Element) rewardItems.item(k);
							questsList.get(i).getPhase(j).setRewardItem(currentItem.getAttribute("name"), currentItem.getAttribute("quantity"));
						}
					}
					
					// Get Message for completedQuest message
					Element currentTag = (Element) currentPhase.getElementsByTagName("questCompleted").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setQuestCompleted(currentTag.getAttribute("message"));
					}
					
					// Get Message for offerQuest message
					currentTag = (Element) currentPhase.getElementsByTagName("offerQuest").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setOfferQuest(currentTag.getAttribute("message"));
					}
					
					// Get replies with offer messages
					NodeList repliesWithOffer = currentPhase.getElementsByTagName("replyWithOffer");
					for(int k=0; k<repliesWithOffer.getLength(); k++){
						Element currentReply = (Element) repliesWithOffer.item(k);
						questsList.get(i).getPhase(j).setRepliesOffer(currentReply.getAttribute("key"), currentReply.getAttribute("message"));
					}
					
					// Get Quest Accepted message
					currentTag = (Element) currentPhase.getElementsByTagName("questAccepted").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setQuestAccepted(currentTag.getAttribute("message"));
					}
					
					// Get Quest Refused message
					currentTag = (Element) currentPhase.getElementsByTagName("questRefused").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setQuestRefused(currentTag.getAttribute("message"));
					}
					
					// Reply (generic?)
					// TODO implement the reply. May be a generic reply or different types. Need to sleep on it.
					NodeList replies = currentPhase.getElementsByTagName("reply");
					if(replies != null){
						for(int k=0; k<replies.getLength(); k++){
							Element currentReply = (Element) replies.item(k);
							questsList.get(i).getPhase(j).setReplyMessage(currentReply.getAttribute("key"), currentReply.getAttribute("message"));
						}
					}
					
					
					// Reminder player without necessary items. Same question for any item
					currentTag = (Element) currentPhase.getElementsByTagName("remindWithoutItem").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setRemindWithoutItem(currentTag.getAttribute("message"));
					}
					
					// Remind PLayer about the quest when he says something about it (e.g. quest, task)
					currentTag = (Element) currentPhase.getElementsByTagName("remindQuest").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setRemindQuest(currentTag.getAttribute("message"));
					}
					
					// Get Greeting message
					currentTag = (Element) currentPhase.getElementsByTagName("greeting").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setGreeting(currentTag.getAttribute("message"));
					}
					
					// Get Complete Last Phase Talk
					currentNode = (Element) currentPhase.getElementsByTagName("completeLastPhaseTalk").item(0);
					if(currentNode != null){
						questsList.get(i).getPhase(j).getCompleteLastPhaseTalk().setDrop(currentNode.getAttribute("drop").equals("true"));
						questsList.get(i).getPhase(j).setHasCompleteLastPhaseTalk(true);
						currentTag = (Element) currentNode.getElementsByTagName("greeting").item(0);
						if(currentTag != null){
							questsList.get(i).getPhase(j).getCompleteLastPhaseTalk().setGreeting(currentTag.getAttribute("message"));
						}
						
						replies = currentNode.getElementsByTagName("reply");
						for(int k=0; k<replies.getLength(); k++){
							Element currentReply = (Element) replies.item(k);
							questsList.get(i).getPhase(j).getCompleteLastPhaseTalk().setReplyMessage(currentReply.getAttribute("key"), currentReply.getAttribute("message"));
						}
						
						currentTag = (Element) currentNode.getElementsByTagName("greetingWithoutItem").item(0);
						if(currentTag != null){
							questsList.get(i).getPhase(j).getCompleteLastPhaseTalk().setGreetingWithoutItem(currentTag.getAttribute("message"));
						}
						
					}
					
					NodeList images = currentPhase.getElementsByTagName("showImage");
					if(images != null){
						for(int k=0; k<images.getLength(); k++){
							currentTag = (Element) images.item(k);
							String image = currentTag.getAttribute("image"); 
							String title = currentTag.getAttribute("title");
							String caption = currentTag.getAttribute("caption");
							String key = currentTag.getAttribute("key");
							String message = currentTag.getAttribute("message");
							questsList.get(i).getPhase(j).setImage(image, title, caption, key, message);
						}
					}
					
					// Get goodbyeNotStarted message
					currentTag = (Element) currentPhase.getElementsByTagName("infoQuestOnBye").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setGoodbyeNotStarted(currentTag.getAttribute("message"));
					}
					
					// Get goodbyeNotCompleted message
					currentTag = (Element) currentPhase.getElementsByTagName("remindOnBye").item(0);
					if(currentTag != null){
						questsList.get(i).getPhase(j).setGoodbyeNotCompleted(currentTag.getAttribute("message"));
					}
					
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
