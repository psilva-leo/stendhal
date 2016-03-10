package games.stendhal.server.maps.kirdneh.inn;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.kirdneh.city.FlowerSellerNPC;
import games.stendhal.server.maps.semos.bakery.ChefNPC;
import marauroa.common.game.RPObject.ID;
import marauroa.server.game.db.DatabaseFactory;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class PieMakerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "testzone";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		
		StendhalRPZone zone = new StendhalRPZone("admin_test");
		new PieMakerNPC().configureZone(zone, null);
		
		setupZone(ZONE_NAME);
	}//set up before class
	
	public PieMakerNPCTest() {
		super(ZONE_NAME, "Crusty");
	}

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		
		final SpeakerNPC npc = getNPC("Crusty");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hello! I make #CheeseAndOnionPies.",
				getReply(npc));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
		assertEquals("Bye.", getReply(npc));
	}

	/**
	 * Tests for hiAndMakeNoStuff.
	 */
	@Test
	public void testHiAndMakeNoStuff() {
		final SpeakerNPC npc = getNPC("Crusty");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hello! I make #CheeseAndOnionPies.",
				getReply(npc));
		en.step(player, "make");
		assertTrue(npc.isTalking());
		assertEquals(
				"I can only make a pie if you bring me 2 #'sacks of flour', 4 #'pieces 
of cheese', and an #'onion'.",
				getReply(npc));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
		assertEquals("Bye.", getReply(npc));
	}

	/**
	 * Tests for hiAndMakeWithStuffSingle.
	 */
	@Test
	public void testHiAndMakeWithStuffSingle() {
		final SpeakerNPC npc = getNPC("Leander");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hallo! Glad to see you in my kitchen where I make #pizza and 
#sandwiches.",
				getReply(npc));
		final StackableItem cheese = new StackableItem("cheese", "", "", null);
		cheese.setQuantity(2);
		cheese.setID(new ID(2, ZONE_NAME));
		player.getSlot("bag").add(cheese);
		final StackableItem bread = new StackableItem("bread", "", "", null);
		bread.setQuantity(1);
		bread.setID(new ID(1, ZONE_NAME));
		player.getSlot("bag").add(bread);
		final StackableItem ham = new StackableItem("ham", "", "", null);
		ham.setID(new ID(3, ZONE_NAME));
		player.getSlot("bag").add(ham);
		assertEquals(2, player.getNumberOfEquipped("cheese"));
		assertEquals(1, player.getNumberOfEquipped("bread"));
		assertEquals(1, player.getNumberOfEquipped("ham"));

		en.step(player, "make");
		assertTrue(npc.isTalking());
		assertEquals(
				"I need you to fetch me 2 #'pieces of cheese', a #'loaf of bread', and a 
#'piece of ham' for this job, which will take 3 minutes. Do you have what I need?",
				getReply(npc));
		en.step(player, "yes");
		final String[] questStatus = player.getQuest(QUEST).split(";");
		final String[] expected = { "1", "sandwich", "" };
		assertEquals("amount", expected[0], questStatus[0]); 
		assertEquals("item", expected[1], questStatus[1]); 

		assertTrue(npc.isTalking());
		assertEquals(
				"OK, I will make a sandwich for you, but that will take some time. 
Please come back in 3 minutes.",
				getReply(npc));
		assertEquals(0, player.getNumberOfEquipped("cheese"));
		assertEquals(0, player.getNumberOfEquipped("bread"));
		assertEquals(0, player.getNumberOfEquipped("ham"));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
		player.setQuest(QUEST, "1;;0");

		en.step(player, "hi");
		assertEquals(
				"Welcome back! I'm done with your order. Here you have the sandwich.",
				getReply(npc));
		assertEquals(1, player.getNumberOfEquipped("sandwich"));
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
