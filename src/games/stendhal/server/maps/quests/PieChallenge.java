/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
import games.stendhal.server.maps.quests.logic.BringListOfItemsQuest;
import games.stendhal.server.maps.quests.logic.BringListOfItemsQuestLogic;

import java.util.Arrays;
import java.util.List;

/**
 * QUEST: The Pie Challenge
 * <p>
 * PARTICIPANTS:
 * <ul>
 * <li> Peter, a goatherd living on a mountain in Semos surroundings </li>
 * </ul>
 * <p>
 * STEPS:
 * <ul>
 * <li> Peter asks you for some pies. </li>
 * <li> You get one of the pies somehow, e.g. by asking in the bakery. </li>
 * <li> You bring the pies up the mountain and give it to Peter. </li>
 * <li> Repeat until Peter received all weapons. (Of course you can bring up several pies at the same time.) </li>
 * <li> Peter gives you milk in exchange. </li>
 * </ul>
 * <p>
 * REWARD:
 * <ul>
 * <li> Milk </li>
 * <li> 500 XP </li>
 * </ul>
 * <p>
 * REPETITIONS: yes
 */
public class PieChallenge extends AbstractQuest implements
		BringListOfItemsQuest {

	private static final List<String> neededPies = Arrays.asList("Fish pie","Apple pie", "Cherry Pie", "Strawberry pie");

	private static final String QUEST_SLOT = "pie_collector";
	
	private static final int REQUIRED_MINUTES = 1440;
	
	private BringListOfItemsQuestLogic bringItems;
	
	@Override
	public List<String> getHistory(final Player player) {
		return bringItems.getHistory(player);
	}

	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Pie Collector",
				"Peter, who lives in Semos mountain wants to get a pie of each kind.",
				true);
		setupAbstractQuest();
	}

	private void setupAbstractQuest() {
		final BringListOfItemsQuest concreteQuest = this;
		bringItems = new BringListOfItemsQuestLogic(concreteQuest);
		bringItems.addToWorld();
	}

	@Override
	public SpeakerNPC getNPC() {
		return npcs.get("Peter the Goatherd");
	}

	@Override
	public List<String> getNeededItems() {
		return neededPies;
	}

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public List<String> getTriggerPhraseToEnumerateMissingItems() {
		return Arrays.asList("collection");
	}

	@Override
	public List<String> getAdditionalTriggerPhraseForQuest() {
		return ConversationPhrases.EMPTY;
	}

	@Override
	public double getKarmaDiffForQuestResponse() {
		return 0;
	}

	@Override
	public String welcomeBeforeStartingQuest() {
		return "Greetings. I am Peter. Are you interested in a bottle of milk? "
				+ "I certainly am, I have been giving them since I was "
				+ "young. Maybe you can do a little #task for me.";
	}

	@Override
	public String welcomeDuringActiveQuest() {
		return "Welcome back. I hope you have come to help me by bring me some #pies.";
	}

	@Override
	public String welcomeAfterQuestIsCompleted() {
		return "Welcome! Thanks again for completing my challenge.";
	}

	@Override
	public boolean shouldWelcomeAfterQuestIsCompleted() {
		// because of WeaponsCollector2
		return false; 
	}

	@Override
	public String respondToQuest() {
		return "Although I have been giving my goat products for such a long time, I "
				+ "still don't have everything I want. I need "
				+ "help to bring me some #pies.";
	}

	@Override
	public String respondToQuestAfterItHasAlreadyBeenCompleted() {
		return "My collection is now complete! Thanks again.";
	}

	@Override
	public String respondToQuestAcception() {
		return "If you help me bring me some pies, I will give you "
				+ "something very delicious and healthy in exchange. Bye";
	}

	@Override
	public String respondToQuestRefusal() {
		return "Well, maybe someone else will happen by and help me. Bye";
	}

	@Override
	public String askForMissingItems(final List<String> missingItems) {
		return "There " + Grammar.isare(missingItems.size()) + " "
				+ Grammar.quantityplnoun(missingItems.size(), "pie", "a")
				+ " still missing from what I have: "
				+ Grammar.enumerateCollection(missingItems)
				+ ". Do you have anything of that nature with you?";
	}
	@Override
	public String firstAskForMissingItems(final List<String> missingItems) {
		return "There " + Grammar.isare(missingItems.size()) + " "
				+ Grammar.quantityplnoun(missingItems.size(), "pie", "a")
				+ " missing from what I have: "
				+ Grammar.enumerateCollection(missingItems)
				+ ". Will you bring them to me?";
	}


	@Override
	public String respondToPlayerSayingHeHasNoItems(final List<String> missingItems) {
		return "Let me know as soon as you get "
				+ Grammar.itthem(missingItems.size()) + ". Farewell.";
	}

	@Override
	public String askForItemsAfterPlayerSaidHeHasItems() {
		return "What is it that you got?";
	}

	@Override
	public String respondToItemBrought() {
		return "Thank you very much! Do you have anything else for me?";
	}

	@Override
	public String respondToLastItemBrought() {
		return "At last, my desire is complete! Thank you very much; "
				+ "here, take this #'milk' in exchange!";
	}

	@Override
	public void rewardPlayer(final Player player) {
		final Item milk = SingletonRepository.getEntityManager().getItem("milk");
		milk.setBoundTo(player.getName());
		player.equipOrPutOnGround(milk);
		player.addXP(500);
	}

	@Override
	public String respondToOfferOfNotExistingItem(final String itemName) {
		return "I may be old, but I'm not senile, and you clearly don't have "
				+ Grammar.a_noun(itemName)
				+ ". What do you really have for me?";
	}

	@Override
	public String respondToOfferOfNotMissingItem() {
		return "I already have that one. Do you have any other pie for me?";
	}

	@Override
	public String respondToOfferOfNotNeededItem() {
		return "Oh, that is not an interesting hot food item I want";
	}

	@Override
	public String getName() {
		return "PieChallenge";
	}
	
	// it can be a long quest so they can always start it before they can necessarily finish all
	@Override
	public int getMinLevel() {
		return 30;
	}

	@Override
	public String getNPCName() {
		return "Peter the Goatherd";
	}
	
	@Override
	public String getRegion() {
		return Region.SEMOS_SURROUNDS;
	}
}


