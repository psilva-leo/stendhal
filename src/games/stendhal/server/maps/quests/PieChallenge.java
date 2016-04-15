package games.stendhal.server.maps.quests;
 
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.CollectRequestedItemsAction;
import games.stendhal.server.entity.npc.action.EquipRandomAmountOfItemAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SayRequiredItemsFromCollectionAction;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestAndModifyKarmaAction;
import games.stendhal.server.entity.npc.action.SetQuestToTimeStampAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.QuestActiveCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStateStartsWithCondition;
import games.stendhal.server.entity.npc.condition.TimePassedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
import games.stendhal.server.util.ItemCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * QUEST: Pie quest
 * 
 * PARTICIPANTS:
 * <ul>
 * <li>Peter (The goatherd who lives in semos mountain)</li>
 * </ul>
 * 
 * STEPS:
 * <ul>
 * <li>Peter introduces himself and asks for a variety of hot pies for his stomach.</li>
 * <li>You collect the items.</li>
 * <li>Peter sees your items, asks for them then thanks you.</li>
 * </ul>
 * 
 * REWARD:
 * <ul>
 * <li>XP: 500</li>
 * <li><1-5>Milk</li>
* <li><1-5>Cheese</li>
 * <li>Karma: 5</li>
 * </ul>
 * 
 * REPETITIONS:
 * none
 * 
 * @author Ankita Sadu
 */
public class PieChallenge extends AbstractQuest {
 
	
	
	/**
	 * NOTE: Reward has not been set, nor has the XP.
	 * left them default here, but in the JUnit test
	 * called reward item "REWARD" temporarily
	 */
	
    public static final String QUEST_SLOT = "pie_collector";
    
    /** 
     * The delay between repeating quests.
     * 1 week
     */
	private static final int REQUIRED_MINUTES = 0;
    
    /**
	 * Required items for the quest.
	 */
	protected static final String NEEDED_ITEMS = "fish pie=1;cherry pie=1;apple pie=1";
 
    @Override
    public void addToWorld() {
        fillQuestInfo("Pies for Peter",
				"Peter the goatherd searches for hot pies to fill his stomach",
				true);
        prepareQuestStep();
        prepareBringingStep();
    }
 
    @Override
    public String getSlotName() {
        return QUEST_SLOT;
    }
 
    @Override
    public String getName() {
        return "PieChallenge";
    }
    
 	@Override
 	public int getMinLevel() {
 		return 0;
 	}
 	
 	@Override
 	public boolean isRepeatable(final Player player) {
 		return new AndCondition(
 					new QuestStateStartsWithCondition(QUEST_SLOT, "done;"),
 					new TimePassedCondition(QUEST_SLOT, 1, REQUIRED_MINUTES)).fire(player, null, null);
 	}
 	
 	@Override
 	public String getRegion() {
 		return Region.SEMOS_SURROUNDS;
 	}
 
 	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		res.add("Peter the goatherd searches for hot pies to fill his stomach.");
		final String questState = player.getQuest(QUEST_SLOT);
		
		if ("rejected".equals(questState)) {
			// quest rejected
			res.add("I decided not find Peter some hot pies, I have better things to do.");
		} else if (!player.isQuestCompleted(QUEST_SLOT)) {
			// not yet finished
			final ItemCollection missingItems = new ItemCollection();
			missingItems.addFromQuestStateString(questState);
			res.add("I still need to bring Peter" + Grammar.enumerateCollection(missingItems.toStringList()) + ".");
		} else if (isRepeatable(player)) {
			// may be repeated now
			res.add("It's been a while since I brought Peter hot pies for his stomach, I wonder if the pies have spoiled?");
        } else {
        	// not (currently) repeatable
        	res.add("I brought Peter the hot pies he needed for his stomach.");
		}
		return res;
	}
    
    public void prepareQuestStep() {
    	SpeakerNPC npc = npcs.get("Peter the goatherd");
    	
    	// various quest introductions
    	
    	// offer quest first time
    	npc.add(ConversationStates.ATTENDING,
    		ConversationPhrases.combine(ConversationPhrases.QUEST_MESSAGES, "pies"),
    		new AndCondition(
    			new QuestNotStartedCondition(QUEST_SLOT),
    			new QuestNotInStateCondition(QUEST_SLOT, "rejected")),
    		ConversationStates.QUEST_OFFERED,
    		"Would you be kind enough to find me some hot pies for my stomach? I'd be ever so grateful!",
    		null);
    	
    	// ask for quest again after rejected
    	npc.add(ConversationStates.ATTENDING, 
    		ConversationPhrases.combine(ConversationPhrases.QUEST_MESSAGES, "stomach"),
    		new QuestInStateCondition(QUEST_SLOT, "rejected"),
    		ConversationStates.QUEST_OFFERED, 
    		"Are you willing to find me hot pies for my stomach yet?",
    		null);
    	
    	// repeat quest
    	npc.add(ConversationStates.ATTENDING,
            ConversationPhrases.combine(ConversationPhrases.QUEST_MESSAGES, "stomach"),
            new AndCondition(
            	new QuestCompletedCondition(QUEST_SLOT),
            	new TimePassedCondition(QUEST_SLOT, 1, REQUIRED_MINUTES)),
            ConversationStates.QUEST_OFFERED,
            "I'm sorry to say that the pies you brought for my stomach aren't very warm anymore. " +
            "Would you be kind enough to find me some more?",
            null);
    	    	
    	// quest inactive    	
    	npc.add(ConversationStates.ATTENDING,
        	ConversationPhrases.combine(ConversationPhrases.QUEST_MESSAGES, "stomach"),
        	new AndCondition(
        		new QuestCompletedCondition(QUEST_SLOT),
        		new NotCondition(new TimePassedCondition(QUEST_SLOT, 1, REQUIRED_MINUTES))),
        	ConversationStates.ATTENDING,
        	"I am very full. I don't need any pies yet, but thanks for enquiring!",
        	null);
    	
    	// end of quest introductions
    	
    	
    	// introduction chat    	
    	npc.add(ConversationStates.ATTENDING,
        	"stomach",
        	new AndCondition(
        		new QuestNotStartedCondition(QUEST_SLOT),
        		new QuestNotInStateCondition(QUEST_SLOT, "rejected")),
        	ConversationStates.ATTENDING,
        	"It's a shame for you to see me starving like this, i really need some hot #pies...",
        	null);
    	
    	// accept quest response
    	npc.add(ConversationStates.QUEST_OFFERED,
    		ConversationPhrases.YES_MESSAGES,
    		null,
    		ConversationStates.QUESTION_1,
    		null,
			new MultipleActions(
				new SetQuestAction(QUEST_SLOT, NEEDED_ITEMS),
				new SayRequiredItemsFromCollectionAction(QUEST_SLOT, "That's wonderful! I'd like these hot pies: [items].")));
    	
    	// reject quest response
    	npc.add(ConversationStates.QUEST_OFFERED,
        	ConversationPhrases.NO_MESSAGES,
        	null,
        	ConversationStates.ATTENDING,
        	"This stomach is hungry you know...",
        	new SetQuestAndModifyKarmaAction(QUEST_SLOT, "rejected", -5.0));
    	
    	// meet again during quest
    	npc.add(ConversationStates.IDLE, 
    		ConversationPhrases.GREETING_MESSAGES,
			new AndCondition(
				new QuestActiveCondition(QUEST_SLOT),
				new GreetingMatchesNameCondition(npc.getName())),
			ConversationStates.ATTENDING,
			"Hello again. If you've brought me some hot pies for my #stomach, I'll happily take them!",
			null);

   	
    	
    	// specific pie info
    	npc.add(ConversationStates.QUESTION_1,
        	"fish pie",
        	new QuestActiveCondition(QUEST_SLOT),
        	ConversationStates.QUESTION_1,
        	"Delicious fish pie !, You see a fish and leek pie. It's not too stodgy so you can eat it faster than a meat pie.",
        	null);
    	
    	npc.add(ConversationStates.QUESTION_1,
            "cherry pie",
            new QuestActiveCondition(QUEST_SLOT),
            ConversationStates.QUESTION_1,
            "Delicious cherry pie!",
            null);
    	
    	npc.add(ConversationStates.QUESTION_1,
        	"apple pie",
        	new QuestActiveCondition(QUEST_SLOT),
        	ConversationStates.QUESTION_1,
        	"Delicious Apple pies!",
        	null);
    }
    
    
    private void prepareBringingStep() {
		final SpeakerNPC npc = npcs.get("Peter the goatherd");
		
		// ask for required items
    	npc.add(ConversationStates.ATTENDING, 
    		ConversationPhrases.combine(ConversationPhrases.QUEST_MESSAGES, "stomach"),
    		new QuestActiveCondition(QUEST_SLOT),
    		ConversationStates.QUESTION_2, 
    		null,
    		new SayRequiredItemsFromCollectionAction(QUEST_SLOT, "I'd still like [items]. Have you brought any?"));
    	
    	// player says he didn't bring any items
		npc.add(ConversationStates.QUESTION_2, 
			ConversationPhrases.NO_MESSAGES,
			new QuestActiveCondition(QUEST_SLOT),
			ConversationStates.QUESTION_1,
			null,
			new SayRequiredItemsFromCollectionAction(QUEST_SLOT, "Oh, that's a shame, do tell me when you find some. I'd still like [items]."));
    	
    	// player says he has a required item with him
		npc.add(ConversationStates.QUESTION_2,
			ConversationPhrases.YES_MESSAGES,
			new QuestActiveCondition(QUEST_SLOT),
			ConversationStates.QUESTION_2, 
			"Wonderful, what hot delights have you brought?",
			null);
    	
		// set up next step
    	ChatAction completeAction = new  MultipleActions(
			new SetQuestAction(QUEST_SLOT, "done"),
			new SayTextAction("Hot pies yum ! Thank you ever so much! Here, take this as a reward."),
			new IncreaseXPAction(500),
			new IncreaseKarmaAction(5),
			// I thought the deal was unfair with just one bottle, so I made it a bit random :)
			new EquipRandomAmountOfItemAction("milk", 1, 5),
			new SetQuestToTimeStampAction(QUEST_SLOT, 1)
		);
    	
    	// add triggers for the item names
    	final ItemCollection items = new ItemCollection();
    	items.addFromQuestStateString(NEEDED_ITEMS);
    	for (final Map.Entry<String, Integer> item : items.entrySet()) {
    		npc.add(ConversationStates.QUESTION_2,
    			item.getKey(),
    			new QuestActiveCondition(QUEST_SLOT),
    			ConversationStates.QUESTION_2,
    			null,
    			new CollectRequestedItemsAction(item.getKey(),
    				QUEST_SLOT,
    				"Wonderful! Did you bring anything else with you?", "I already have enough of those.",
    				completeAction,
    				ConversationStates.ATTENDING));
    	}
    }

	@Override
	public String getNPCName() {
		return "Peter the goatherd";
	}
}