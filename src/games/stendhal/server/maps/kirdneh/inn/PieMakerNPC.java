package games.stendhal.server.maps.kirdneh.inn;

import games.stendhal.common.Direction;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.ProducerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.ProducerBehaviour;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


  	
public class PieMakerNPC 
{
 		
    @Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}//configure zone
private void buildNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Crusty") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(10, 4));
				nodes.add(new Node(10, 8));
				nodes.add(new Node(15, 8));
				nodes.add(new Node(15, 2));
				nodes.add(new Node(3, 2));
				nodes.add(new Node(3, 7));
				nodes.add(new Node(10, 7));
				setPath(new FixedPath(nodes, true));
			}//createPath

			@Override
			public void createDialog() {
			    addGreeting("Hello!");
				addJob("I'm the local pie maker. I #make the most amazing pies!");
				addReply(Arrays.asList("pie", "pies"), "I make amazing pies!. If you want one, just tell me to #'make 1 pie'.");
				addQuest("I don't have any quests for you, but I can make delicious pies!"); 
				addHelp("Ask me to make you a cheese and onion pie.");
				addReply(Arrays.asList("flour", "cheese", "onion"), "I get all my ingredients from Semos city!");
				
				addGoodbye();

				// Crusty makes pies if you bring him flour, cheese, and an onion.
				final Map<String, Integer> requiredResources = new TreeMap<String, Integer>();
				requiredResources.put("flour", 2);
				requiredResources.put("cheese", 4);
				requiredResources.put("onion", 1);

				final ProducerBehaviour behaviour = new ProducerBehaviour(
						"crusty_make_pie", "make", "pie",
						requiredResources, 7 * 60);

				new ProducerAdder().addProducer(this, behaviour,
				"Hello! Glad to see you in my kitchen where I make #pies");


			}//createDialogue
			};//speaker
			npc.setPosition(15, 3);
			npc.setEntityClass("bakernpc");
			npc.setDescription("Crusty makes amazing pies.");
			zone.add(npc);		
	}//buildNPC
}//class
