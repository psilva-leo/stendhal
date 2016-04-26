package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.core.config.GenericQuestLoader;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
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
						
						// Reply to player for certain words if he has not already completed the quest and offer him the quest
						for(int k=0; k<questsStructures.get(i).getPhase(j).getRepliesOffers().size(); k++){
							String key = questsStructures.get(i).getPhase(j).getRepliesOffers().get(k);
							replyWithOffer(npc, key, questsStructures.get(i).getPhase(j).getRepliesOfferMessage(key));
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
						if(!questsStructures.get(i).getPhase(j).getQuestRefused().equals("")){
							remindWithoutItem(npc, questsStructures.get(i).getPhase(j).getQuestRefused(), questsStructures.get(i).getPhase(j).getCollectables(), questsStructures.get(i).getPhase(j).getName());
						}
						
						// Remind player about quest if he says something about it
						// Trigger: quest, task, etc
						if(!questsStructures.get(i).getPhase(j).getQuestRefused().equals("")){
							remindQuest(npc, questsStructures.get(i).getPhase(j).getQuestRefused(), questsStructures.get(i).getPhase(j).getName());
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
				
			};
			quests.add(currentQuest);
		}
	}
	
	public ArrayList<AbstractQuest> getQuests(){
		return quests;
	}
	

}
