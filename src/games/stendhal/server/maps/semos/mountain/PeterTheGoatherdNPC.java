package games.stendhal.server.maps.semos.mountain;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.ProducerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.ProducerBehaviour;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;

import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;

/**
 * Goatherd
 * 
 * @author Ankita Sadu
 *
 */
public class PeterTheGoatherdNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
		
		// Create the NPC
		final SpeakerNPC goatherd = new SpeakerNPC("Peter the Goatherd") {

			@Override
			protected void createPath() {
				// NPC moves
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(11,13));
				nodes.add(new Node(26,24));
				nodes.add(new Node(14,33));
				nodes.add(new Node(19,37));
				nodes.add(new Node(30,12));
				nodes.add(new Node(10,3));
				setPath(new FixedPath(nodes,true));
			}
				
				
	
			

			@Override
			protected void createDialog() {
				addGreeting("Hello, I hope you enjoy the cool wind here.");
				addHelp("So cold here ! I can give you my goat's products, Can you help me ?");
				addJob("I am a Goatherd. I can give you a bottle of milk, if only you get me one of each kind of pie. What more can I say?");
				addGoodbye("Farewell, return to me whenever you need my help.");   
				
			final Map<String, Integer> requiredResources = new TreeMap<String, Integer>();
				requiredResources.put("Fish pie", Integer.valueOf(1));
				requiredResources.put("Apple pie", Integer.valueOf(1));
				requiredResources.put("Cherry pie", Integer.valueOf(1));
				requiredResources.put("Strawberry pie", Integer.valueOf(1));

				final ProducerBehaviour behaviour = new ProducerBehaviour("peter_give_milk", "give", "milk",
				        requiredResources, 4 * 60);

				new ProducerAdder().addProducer(this, behaviour,
				        "Hi! I bet you've heard about my famous fresh goat milk and want me to #give one for you, am I right?");
				//player.addXP(500);
			}
		};

		goatherd.setEntityClass("oldfishermannpc");
		goatherd.setPosition(11, 13);
		goatherd.initHP(100);
		goatherd.setDescription("You can see a Goatherd,his name is Peter, he gives a bottle of fresh goat milk");
		goatherd.setResistance(0);
		
		zone.add(goatherd);
	}
	
}