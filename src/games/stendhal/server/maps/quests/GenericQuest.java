package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.core.config.GenericQuestLoader;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.player.Player;

public class GenericQuest {
	
	private ArrayList<AbstractQuest> quests = new ArrayList<AbstractQuest>();
	
	public GenericQuest(){
		GenericQuestLoader questLoader = new GenericQuestLoader();
		final ArrayList<QuestStructure> questsStructures = new ArrayList<QuestStructure>(questLoader.getQuests());
		
		for(int current=0; current<questsStructures.size(); current++){
			final int i = current;
			
			AbstractQuest currentQuest = new AbstractQuest() {
				private final String QUEST_SLOT = questsStructures.get(i).getName().toLowerCase().replace(" ", "_");
				
				@Override
				public String getSlotName() {
					return QUEST_SLOT;
				}
				

				@Override
				public void addToWorld() {
					fillQuestInfo(
							questsStructures.get(i).getName(),
							questsStructures.get(i).getDescription(),
							false);
					// questsStructures.get(i).isRepeatable()
					
					for(int j=0; j<questsStructures.get(i).getPhaseSize(); j++){
						final SpeakerNPC npc = npcs.get(questsStructures.get(i).getPhase(j).getNPC());
						
						// Quest Already Completed message
						if(!questsStructures.get(i).getPhase(j).getQuestCompleted().equals("")){
							questCompleted(npc, questsStructures.get(i).getPhase(j).getQuestCompleted());
						}
						
						// Offer Quest to player message
						if(!questsStructures.get(i).getPhase(j).getOfferQuest().equals("")){
							offerQuest(npc, questsStructures.get(i).getPhase(j).getOfferQuest());
						}
						
						// Reply to player for certain words if he has already completed the quest
						for(int k=0; k<questsStructures.get(i).getPhase(j).getRepliesCompleted().size(); k++){
							String key = questsStructures.get(i).getPhase(j).getRepliesCompleted().get(k);
							replyCompleted(npc, key, questsStructures.get(i).getPhase(j).getReplyCompletedMessage(key));
						}
						
						// Reply to player for certain words offering the quest
						for(int k=0; k<questsStructures.get(i).getPhase(j).getRepliesOffers().size(); k++){
							String key = questsStructures.get(i).getPhase(j).getRepliesOffers().get(k);
							replyWithOffer(npc, key, questsStructures.get(i).getPhase(j).getReplyOfferMessage(key));
						}
						
						// Reply to player for certain words
						for(int k=0; k<questsStructures.get(i).getPhase(j).getReplies().size(); k++){
							String key = questsStructures.get(i).getPhase(j).getReplies().get(k);
							reply(npc, key, questsStructures.get(i).getPhase(j).getReplyMessage(key));
						}
						
						// Player accepts Question
						if(!questsStructures.get(i).getPhase(j).getQuestAccepted().equals("")){
							questAccepted(npc, questsStructures.get(i).getPhase(j).getQuestAccepted(), questsStructures.get(i).getPhase(j).getName());
						}

						// Player refuses Question
						if(!questsStructures.get(i).getPhase(j).getQuestRefused().equals("")){
							questRefused(npc, questsStructures.get(i).getPhase(j).getQuestRefused());
						}

						// Remind player about quest if he still don`t have the item
						// Trigger: any of the items
						if(!questsStructures.get(i).getPhase(j).getRemindWithoutItem().equals("")){
							remindWithoutItem(npc, questsStructures.get(i).getPhase(j).getRemindWithoutItem(), questsStructures.get(i).getPhase(j).getCollectables(), questsStructures.get(i).getPhase(j).getName());
						}
						
						// Remind player about quest if he says something about it
						// Trigger: quest, task, etc
						if(!questsStructures.get(i).getPhase(j).getRemindQuest().equals("")){
							remindQuest(npc, questsStructures.get(i).getPhase(j).getRemindQuest(), questsStructures.get(i).getPhase(j).getName());
						}
						
						// Complete last phase talk
						if(questsStructures.get(i).getPhase(j).isHasCompleteLastPhaseTalk()){
							if(!questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getGreeting().equals("")){
								//if(questsStructures.get(i).getPhase(j-1).getCollectables().size() > 0){
								greeting(npc, questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getGreeting(),
										questsStructures.get(i).getPhase(j-1), questsStructures.get(i).getPhase(j));
								//}
							}
							
							if(!questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getGreetingWithoutItem().equals(""))
								greetingWithoutItem(npc, questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getGreetingWithoutItem(), questsStructures.get(i).getPhase(j-1));
							
							
							// Reply to player for certain words
							for(int k=0; k<questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getReplies().size(); k++){
								String key = questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getReplies().get(k);
								reply(npc, key, questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().getReplyMessage(key));
							}
							
							// TODO if there drop is false get the collectables from prev phase
							if(!questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().isDrop()){
								for(int k=0; k<questsStructures.get(i).getPhase(j-1).getCollectables().size(); k++){
									String item = questsStructures.get(i).getPhase(j-1).getCollectables().get(k);
									questsStructures.get(i).getPhase(j).setCollectableItem(item, questsStructures.get(i).getPhase(j-1).getCollectableItemQuantity(item));
								}
								
							}
						}else{
							// MEMO maybe?
							// if there drop is false get the collectables from prev phase
							if(!questsStructures.get(i).getPhase(j).getCompleteLastPhaseTalk().isDrop() && j > 0){
								for(int k=0; k<questsStructures.get(i).getPhase(j-1).getCollectables().size(); k++){
									String item = questsStructures.get(i).getPhase(j-1).getCollectables().get(k);
									questsStructures.get(i).getPhase(j).setCollectableItem(item, questsStructures.get(i).getPhase(j-1).getCollectableItemQuantity(item));
								}
								
							}
						}
						
						// greeting message
						// Has to stay beneath Complete Last Phase Talk
						if(!questsStructures.get(i).getPhase(j).getGreeting().equals("")){
							greeting(npc, questsStructures.get(i).getPhase(j).getGreeting(), questsStructures.get(i).getPhase(j));
						}
						
						// Get goodbyeNotCompleted message
						if(!questsStructures.get(i).getPhase(j).getGoodbyeNotCompleted().equals("")){
							goodbyeNotCompleted(npc, questsStructures.get(i).getPhase(j).getGoodbyeNotCompleted());
						}
						
						// Get goodbyeNotStarted message
						if(!questsStructures.get(i).getPhase(j).getGoodbyeNotStarted().equals("")){
							goodbyeNotStarted(npc, questsStructures.get(i).getPhase(j).getGoodbyeNotStarted());
						}
						
						
						if(questsStructures.get(i).getPhase(j).getImages().size() > 0){
							for(int k=0; k<questsStructures.get(i).getPhase(j).getImages().size(); k++){							
								showImage(npc, questsStructures.get(i).getPhase(j), questsStructures.get(i).getPhase(j).getImages().get(k));
							}
						}
						
					}
					
				}

				@Override
				public List<String> getHistory(Player player) {
					final List<String> res = new ArrayList<String>();
					if (player.hasQuest(questsStructures.get(i).getPhase(0).getNPC().replace(" ", "")+"FirstChat")) {
						res.add("Met "+questsStructures.get(i).getPhase(0).getNPC());
					}
					if (!player.hasQuest(QUEST_SLOT)) {
						return res;
					}
					res.add("Debug1");
					res.add("Debug2");
					res.add("Debug3");
					
					return res;
				}

				@Override
				public String getName() {
					return questsStructures.get(i).getName().replace(" ", "");
				}
				
				
				/* Methods to facilitate reading the addToWorld where all the NPCs dialogs are written */
				private void questCompleted(SpeakerNPC npc, String message){
					npc.add(ConversationStates.ATTENDING,
							ConversationPhrases.QUEST_MESSAGES,
							new QuestCompletedCondition(QUEST_SLOT),
							ConversationStates.ATTENDING,
							message,
							null);
				}
				
				private void offerQuest(SpeakerNPC npc, String message){
					npc.add(ConversationStates.ATTENDING,
							ConversationPhrases.QUEST_MESSAGES,
							new QuestNotStartedCondition(QUEST_SLOT),
							ConversationStates.QUEST_OFFERED,
							message,
							null);
				}
				
				private void replyCompleted(SpeakerNPC npc, String key, String message){
					npc.add(ConversationStates.ATTENDING,
							key,
							new QuestCompletedCondition(QUEST_SLOT),
							ConversationStates.ATTENDING,
							message,
							null);
				}
				
				private void reply(SpeakerNPC npc, String key, String message){
					npc.add(ConversationStates.ATTENDING,
							key,
							null,
							ConversationStates.ATTENDING,
							message,
							null);
				}
				
				private void replyWithOffer(SpeakerNPC npc, String key, String message){
					npc.add(ConversationStates.QUEST_OFFERED,
							key,
							new QuestNotStartedCondition(QUEST_SLOT),
							ConversationStates.QUEST_OFFERED,
							message,
							null);
				}
				
				private void questAccepted(SpeakerNPC npc, String message, String state){
					npc.add(ConversationStates.QUEST_OFFERED,
							ConversationPhrases.YES_MESSAGES,
							null,
							ConversationStates.ATTENDING,
							message,
							new SetQuestAction(QUEST_SLOT, 0, state));
				}
				
				private void questRefused(SpeakerNPC npc, String message){
					npc.add(ConversationStates.QUEST_OFFERED,
							ConversationPhrases.NO_MESSAGES,
							null,
							ConversationStates.ATTENDING,
							message,
							null);
				}
				
				private void remindWithoutItem(SpeakerNPC npc, String message, ArrayList<String> collectables, String state){
					for(int k=0; k<collectables.size(); k++){
						npc.add(ConversationStates.ATTENDING,
								collectables.get(k),
								new AndCondition(
										new QuestInStateCondition(QUEST_SLOT, 0, state),
										new NotCondition(new PlayerHasItemWithHimCondition(collectables.get(k)))),
								ConversationStates.ATTENDING,
								message,
								null);
					}
				}
				
				private void remindQuest(SpeakerNPC npc, String message, String state){
					npc.add(ConversationStates.ATTENDING,
			                ConversationPhrases.QUEST_MESSAGES,
			                new QuestInStateCondition(QUEST_SLOT, 0, state),
			                ConversationStates.ATTENDING,
			                message,
			                null);
				}
				
				private void greeting(SpeakerNPC npc, String message, QuestStructure.Phase prevPhase, QuestStructure.Phase currentPhase){
					final List<ChatAction> processStep = new LinkedList<ChatAction>();
					
					// Drop items if needed
					if(currentPhase.getCompleteLastPhaseTalk().isDrop()){
						ArrayList<String> collectables = prevPhase.getCollectables();
						for(int i=0; i<collectables.size(); i++){
							processStep.add(new DropItemAction(collectables.get(i).toLowerCase(), Integer.parseInt(prevPhase.getCollectableItemQuantity(collectables.get(i)))));
						}
					}
					
					// Give rewards if any
					for(int i=0; i<currentPhase.getRewards().size(); i++){
						String item = currentPhase.getRewards().get(i);
						if(item.toLowerCase().equals("xp")){
							processStep.add(new IncreaseXPAction(Integer.parseInt(currentPhase.getRewardItemQuantity(item))));
						}else{
							if(item.toLowerCase().equals("karma")){
								processStep.add(new IncreaseKarmaAction(Integer.parseInt(currentPhase.getRewardItemQuantity(item))));
							}else{
								processStep.add(new EquipItemAction(item.toLowerCase(), Integer.parseInt(currentPhase.getRewardItemQuantity(item))));
							}
						}
					}
					
					// Go to next phase status
					processStep.add(new SetQuestAction(QUEST_SLOT, 0, currentPhase.getName()));
					
					// Conditions to chat
					// Right player, correct state, has collectable items
					final  List<ChatCondition> conditions = new LinkedList<ChatCondition>();
					conditions.add(new GreetingMatchesNameCondition(npc.getName()));
					conditions.add(new QuestInStateCondition(QUEST_SLOT, 0, prevPhase.getName()));
					
					for(int i=0; i<prevPhase.getCollectables().size(); i++){
						String item = prevPhase.getCollectables().get(i);
						if(!item.toLowerCase().equals("xp") && !item.toLowerCase().equals("karma")){
							conditions.add(new PlayerHasItemWithHimCondition(item.toLowerCase(),Integer.parseInt(prevPhase.getCollectableItemQuantity(item))));
						}
					}
					
					// Greeting message
					npc.add(ConversationStates.IDLE,
							ConversationPhrases.GREETING_MESSAGES,
							new AndCondition(conditions),
							ConversationStates.ATTENDING,
							message,
							new MultipleActions(processStep));
				}
				
				private void greeting(SpeakerNPC npc, String message, QuestStructure.Phase currentPhase){								
					// Conditions to chat
					// Right player, correct state, has collectable items
					final  List<ChatCondition> conditions = new LinkedList<ChatCondition>();
					conditions.add(new GreetingMatchesNameCondition(npc.getName()));
					conditions.add(new QuestInStateCondition(QUEST_SLOT, 0, currentPhase.getName()));
					
					for(int i=0; i<currentPhase.getCollectables().size(); i++){
						String item = currentPhase.getCollectables().get(i);
						if(!item.toLowerCase().equals("xp") && !item.toLowerCase().equals("karma")){
							conditions.add(new PlayerHasItemWithHimCondition(item.toLowerCase(),Integer.parseInt(currentPhase.getCollectableItemQuantity(item))));
						}
					}
					
					// Greeting message
					npc.add(ConversationStates.IDLE,
							ConversationPhrases.GREETING_MESSAGES,
							new AndCondition(conditions),
							ConversationStates.ATTENDING,
							message,
							null);
				}
				
				private void greetingWithoutItem(SpeakerNPC npc, String message, QuestStructure.Phase prevPhase){
					
					for(int i=0; i<prevPhase.getCollectables().size(); i++){
						String item = prevPhase.getCollectables().get(i);
						if(!item.toLowerCase().equals("xp") && !item.toLowerCase().equals("karma")){
							
							// Conditions to chat
							// Right player, correct state, has collectable items
							final  List<ChatCondition> conditions = new LinkedList<ChatCondition>();
							conditions.add(new GreetingMatchesNameCondition(npc.getName()));
							conditions.add(new QuestInStateCondition(QUEST_SLOT, 0, prevPhase.getName()));
							conditions.add(new NotCondition(new PlayerHasItemWithHimCondition(item.toLowerCase(),Integer.parseInt(prevPhase.getCollectableItemQuantity(item)))));
							
							npc.add(ConversationStates.IDLE,
									ConversationPhrases.GREETING_MESSAGES,
									new AndCondition(conditions),
									ConversationStates.ATTENDING,
									message,
									null);
							
						}
					}
					
					
				}
				
				private void showImage(SpeakerNPC npc, QuestStructure.Phase currentPhase, QuestStructure.Phase.Image image){
					ChatAction showArandulaDrawing = new ExamineChatAction(image.getImage(), image.getTitle(), image.getCaption());
					ChatAction flagDrawingWasShown = new SetQuestAction(QUEST_SLOT, 1, currentPhase.getName()+"drawing");
					npc.add(
							ConversationStates.ATTENDING,
							image.getKey(),
							new AndCondition(
									new QuestInStateCondition(QUEST_SLOT, 0, currentPhase.getName()),
									new NotCondition(new QuestInStateCondition(QUEST_SLOT, 1, currentPhase.getName()+"drawing"))),
							ConversationStates.ATTENDING,
							image.getMessage(),
							new MultipleActions(showArandulaDrawing, flagDrawingWasShown));
				}
				
//				private void itemToCreate(SpeakerNPC npc, String message, String state){
//					// TODO if needed
//				}
				
				private void goodbyeNotCompleted(SpeakerNPC npc, String message){
					npc.add(ConversationStates.ATTENDING,
			        		ConversationPhrases.GOODBYE_MESSAGES,
			        		new AndCondition(
			        				new QuestStartedCondition(QUEST_SLOT),
			        				new QuestNotCompletedCondition(QUEST_SLOT)),
			                ConversationStates.IDLE,
			                message,
			                null);
				}
				
				private void goodbyeNotStarted(SpeakerNPC npc, String message){
					npc.add(ConversationStates.ATTENDING,
			        		ConversationPhrases.GOODBYE_MESSAGES,
			        		new QuestNotStartedCondition(QUEST_SLOT),
			                ConversationStates.IDLE,
			                message,
			                null);
				}
				
			};
			quests.add(currentQuest);
		}
	}
	
	public ArrayList<AbstractQuest> getQuests(){
		return quests;
	}
	

}
