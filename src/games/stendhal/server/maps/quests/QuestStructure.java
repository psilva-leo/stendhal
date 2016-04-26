package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestStructure {
	private String name = "";
	private String description = "";
	private ArrayList<Phase> phases = new ArrayList<Phase>();
	private boolean repeatable = false;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Phase getPhase(int index) {
		return phases.get(index);
	}
	public void addPhase() {
		phases.add(new Phase());
	}
	public int getPhaseSize(){
		return phases.size();
	}

	public boolean isRepeatable() {
		return repeatable;
	}
	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public class Phase{
		private String name = "";
		private String npc = "";
		private Map<String, String> collectablesMap = new HashMap<String, String>();
		private ArrayList<String> collectables = new ArrayList<String>();
		private Map<String, String> rewardsMap = new HashMap<String, String>();
		private ArrayList<String> rewards = new ArrayList<String>();
		private String questCompleted = "";
		private String offerQuest = "";
		private Map<String, String> repliesCompletedMap = new HashMap<String, String>();
		private ArrayList<String> repliesCompleted = new ArrayList<String>();
		private Map<String, String> repliesOfferMap = new HashMap<String, String>();
		private ArrayList<String> repliesOffer = new ArrayList<String>();
		private String questAccepted = "";
		private String questRefused = "";
		private String remindWithoutItem = "";
		private String remindQuest = "";

		/* Phase Name */
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		/* NPC Name */
		public String getNPC() {
			return npc;
		}
		public void setNPC(String name) {
			this.npc = name;
		}
		
		/* Collectables */
		public String getCollectableItemQuantity(String item) {
			return collectablesMap.get(item);
		}
		public void setCollectableItem(String name, String quantity) {
			collectablesMap.put(name, quantity);
			collectables.add(name);
		}
		
		public ArrayList<String> getCollectables(){
			return collectables;
		}
		
		/* Rewards */
		public String getRewardItemQuantity(String item) {
			return rewardsMap.get(item);
		}
		public void setRewardItem(String name, String quantity) {
			rewardsMap.put(name, quantity);
			rewards.add(name);
		}
		
		public ArrayList<String> getRewards(){
			return rewards;
		}
		
		/* Quest Completed Message */
		public String getQuestCompleted() {
			return questCompleted;
		}
		public void setQuestCompleted(String questCompleted) {
			this.questCompleted = questCompleted;
		}
		
		/* Offer Completed Message */
		public String getOfferQuest() {
			return offerQuest;
		}
		public void setOfferQuest(String offerQuest) {
			this.offerQuest = offerQuest;
		}
		
		/* Replies if Completed Messages */
		public String getReplyCompletedMessage(String key) {
			return repliesCompletedMap.get(key);
		}
		public void setReplyCompletedMessage(String key, String message) {
			repliesCompletedMap.put(key, message);
			repliesCompleted.add(key);
		}
		
		public ArrayList<String> getRepliesCompleted() {
			return repliesCompleted;
		}
			
		/* Replies if quest offered (not accepted neither rejected) Messages */
		public String getRepliesOfferMessage(String key) {
			return repliesOfferMap.get(key);
		}
		public void setRepliesOffer(String key, String message) {
			repliesOfferMap.put(key, message);
			repliesOffer.add(key);
		}
		
		public ArrayList<String> getRepliesOffers() {
			return repliesOffer;
		}
		
		/* Quest Accepted Message */
		public String getQuestAccepted() {
			return questAccepted;
		}
		public void setQuestAccepted(String questAccepted) {
			this.questAccepted = questAccepted;
		}
		
		/* Quest Refused Message */
		public String getQuestRefused() {
			return questRefused;
		}
		public void setQuestRefused(String questRefused) {
			this.questRefused = questRefused;
		}
		
		/* Quest Reminder without collectable item(ns) Message */
		public String getRemindWithoutItem() {
			return remindWithoutItem;
		}
		public void setRemindWithoutItem(String remindWithoutItem) {
			this.remindWithoutItem = remindWithoutItem;
		}
		
		/* Quest Reminder Message (Player asking for quest)*/
		public String getRemindQuest() {
			return remindQuest;
		}
		public void setRemindQuest(String remindQuest) {
			this.remindQuest = remindQuest;
		}
		
		
		
		
		
	}
	

}
