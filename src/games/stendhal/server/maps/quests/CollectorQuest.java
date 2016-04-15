package games.stendhal.server.maps.quests;

import java.util.Arrays;
import java.util.List;

import games.stendhal.common.ItemTools;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.parser.Expression;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
import games.stendhal.server.maps.quests.logic.BringListOfItemsQuest;
import games.stendhal.server.maps.quests.logic.BringListOfItemsQuestLogic;

public class CollectorQuest extends AbstractQuest implements BringListOfItemsQuest 
{

	private BringListOfItemsQuestLogic bringItems;
	
	@Override
	public List<String> getHistory(final Player player) {
		return bringItems.getHistory(player);
	}

	private void setupAbstractQuest() {
		final BringListOfItemsQuest concreteQuest = this;
		bringItems = new BringListOfItemsQuestLogic(concreteQuest);
		bringItems.addToWorld();
	}

	@Override
	public void addToWorld() {
		step_1();
		setupAbstractQuest();
		fillQuestInfo(
				//you should pass an argument here.
				false);
	}
	
	//asking for the items 
	private void step_1(/*npc, item, dialogue1, dialogue2, dialogue3 */ ) {
		final SpeakerNPC npc = npcs.get(/*npc*/);

		// player asks about an individual cloak before accepting the quest
		for(final String itemName : /*item*/) {
			npc.add(ConversationStates.QUEST_OFFERED, itemName, null,
				ConversationStates.QUEST_OFFERED, null,
				new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
						Expression obj = sentence.getObject(0);
						if (obj!=null && !obj.getNormalized().equals(itemName)) {
							raiser.say(/*dialogue1*/);
						} else {
							final Item item = SingletonRepository.getEntityManager().getItem(itemName);
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(/*dialogue2*/);

							if (item == null) {
								stringBuilder.append(itemName);
							} else {
								stringBuilder.append(ItemTools.itemNameToDisplayName(item.getItemSubclass()));
							}

							stringBuilder.append(/*dialogue3*/);
							raiser.say(stringBuilder.toString());
						}
					}
					
					@Override
					public List<String> getAdditionalTriggerPhraseForQuest(/*args*/) {
						return Arrays.asList(/*args*/);
					}

					@Override
					public SpeakerNPC getNPC(/*args*/) {
						return npcs.get(/*args*/);
					}

					@Override
					public List<String> getNeededItems(/*args*/) {
						return /*args*/
					}

					@Override
					public String getSlotName(/*args*/) {
						return /*args*/
					}

					@Override
					public List<String> getTriggerPhraseToEnumerateMissingItems(/*args*/) {
						return Arrays.asList(/*args*/);
					}

					@Override
					public double getKarmaDiffForQuestResponse(/*args*/) {
						return 5.0;
					}

					@Override
					public boolean shouldWelcomeAfterQuestIsCompleted(/*args*/) {
						return false;
					}

					@Override
					public String welcomeBeforeStartingQuest(/*args*/) {
						return /*args*/
					}

					@Override
					public String welcomeDuringActiveQuest(/*args*/) {
						return /*args*/
					}
					
					@Override
					public String welcomeAfterQuestIsCompleted(/*args*/) {
						return /*args*/
					}

					@Override
					public String respondToQuest(/*args*/) {
						return /*args*/;
				     }

					@Override
					public String respondToQuestAcception(/*args*/) {
						// player.addKarma(5.0);
						return /*args*/
					}

					@Override
					public String respondToQuestAfterItHasAlreadyBeenCompleted(/*args*/) {
						return /*args*/
					}

					@Override
					public String respondToQuestRefusal(/*args*/) {
						// player.addKarma(-5.0);
						return /*args*/
					}

					@Override
					public String askForItemsAfterPlayerSaidHeHasItems(/*args*/) {
						return /*args*/
					}

					@Override
					public String firstAskForMissingItems(final List<String> missingItems, /*args*/) {
						return "I want " + Grammar.quantityplnoun(missingItems.size(), /*args*/, "a")
								+ ". That's " + Grammar.enumerateCollection(missingItems)
								+ ". Will you find them?";
					}

					@Override
					public String askForMissingItems(final List<String> missingItems, /*args*/) {
						return "I want " + Grammar.quantityplnoun(missingItems.size(), /*args*/, "a")
								+ ". That's " + Grammar.enumerateCollection(missingItems)
								+ ". Did you bring any?";
					}

					@Override
					public String respondToItemBrought() {
						return "Wow, thank you! What else did you bring?";
					}

					@Override
					public String respondToLastItemBrought(/*args*/) {
						return /*args*/
					}

					@Override
					public String respondToOfferOfNotExistingItem(final String itemName) {
						return "Oh, I'm disappointed. You don't really have " + Grammar.a_noun(itemName) + " with you.";
					}

					@Override
					public String respondToOfferOfNotMissingItem(/*args*/) {
						return "You've already brought that /*args*/ to me.";
					}

					@Override
					public String respondToOfferOfNotNeededItem(/*args*/) {
						return "Sorry, that's not a /*args*/ I asked you for.";
					}

					@Override
					public String respondToPlayerSayingHeHasNoItems(final List<String> missingItems) {
						return "Okay then. Come back later.";
					}

					@Override
					public void rewardPlayer(final Player player, /*args*/) {
						final Item blackcloak = SingletonRepository.getEntityManager().getItem(/*args*/);
						blackcloak.setBoundTo(player.getName());
						player.equipOrPutOnGround(blackcloak);
						player.addKarma(5.0);
						player.addXP(10000);
					}

					@Override
					public String getName(/*args*/) {
						return /*args*/;
					}
					
					// You can start collecting just with a simple cloak which you can buy, but maybe not a good idea to send to Fado too early.
					@Override
					public int getMinLevel(/*args*/) {
						return /*args*/;
					}

					@Override
					public String getRegion(/*args*/) {
						return /*args*/;
					}

					@Override
					public String getNPCName(/*args*/) {
						return /*args*/
					}
				}


}
