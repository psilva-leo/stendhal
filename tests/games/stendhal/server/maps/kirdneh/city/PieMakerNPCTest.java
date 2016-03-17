package games.stendhal.server.maps.kirdneh.city;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.kirdneh.inn.PieMakerNPC;
import marauroa.common.game.RPObject.ID;
import marauroa.server.game.db.DatabaseFactory;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

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
				"Hello! Glad to see you in my kitchen where I make #pies",
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
				"Hello! Glad to see you in my kitchen where I make #pies",
				getReply(npc));
		en.step(player, "make");
		assertTrue(npc.isTalking());
		assertEquals(
				"I can only make a pie if you bring me 2 #'sacks of flour', 4 #'pieces of cheese', and an #onion.",
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
		final SpeakerNPC npc = getNPC("Crusty");
		final Engine en = npc.getEngine();
		
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hello! Glad to see you in my kitchen where I make #pies",
				getReply(npc));
		final StackableItem cheese = new StackableItem("cheese", "", "", null);
		cheese.setQuantity(4);
		cheese.setID(new ID(4, ZONE_NAME));
		player.getSlot("bag").add(cheese);
		final StackableItem flour = new StackableItem("flour", "", "", null);
		flour.setQuantity(2);
		flour.setID(new ID(2, ZONE_NAME));
		player.getSlot("bag").add(flour);
		final StackableItem onion = new StackableItem("onion", "", "", null);
		onion.setID(new ID(1, ZONE_NAME));
		player.getSlot("bag").add(onion);
		assertEquals(4, player.getNumberOfEquipped("cheese"));
		assertEquals(2, player.getNumberOfEquipped("flour"));
		assertEquals(1, player.getNumberOfEquipped("onion"));

		en.step(player, "make");
		assertTrue(npc.isTalking());
		assertEquals("I need you to fetch me 2 #'sacks of flour', 4 #'pieces of cheese', and an #onion for this job, which will take 7 minutes. Do you have what I need?",
				getReply(npc));
		en.step(player, "yes");
		
		assertTrue(npc.isTalking());
		assertEquals("OK, I will make a pie for you, but that will take some time. Please come back in 7 minutes.",
				getReply(npc));
		assertEquals(0, player.getNumberOfEquipped("cheese"));
		assertEquals(0, player.getNumberOfEquipped("flour"));
		assertEquals(0, player.getNumberOfEquipped("onion"));
		en.step(player, "bye");
		assertFalse(npc.isTalking());

		en.step(player, "hi");
		assertEquals(
				"Welcome back! I'm still busy with your order to make a pie for you. Come back in 7 minutes to get it.",
				getReply(npc));
		assertEquals(0, player.getNumberOfEquipped("pie"));
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}