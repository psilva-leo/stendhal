package games.stendhal.server.maps.semos.mountain;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Map;

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
				addGreeting("Hello, I hope you enjoy the cold wind here.");
				addHelp("So cold here ! I can give you my goat's products, Can you help me ?");
				addJob("I am a Goatherd. I can give you a bottle of milk, if only you get me one of each kind of pie. What more can I say?");
				addGoodbye("Farewell, return to me whenever you need my help.");   
				
			}
		};

		goatherd.setEntityClass("goatherdnpc");
		goatherd.setAlternativeImage("goatherdnpc");
		goatherd.setPosition(11, 13);
		goatherd.initHP(100);
		goatherd.setDescription("You can see a Goatherd");
		goatherd.setResistance(0);
		
		zone.add(goatherd);
	}
	
}