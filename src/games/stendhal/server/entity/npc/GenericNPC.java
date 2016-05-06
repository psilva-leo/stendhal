package games.stendhal.server.entity.npc;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.GenericNPCLoader;
import games.stendhal.server.core.config.ZoneConfigurator;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GenericNPC implements ZoneConfigurator{

	ArrayList<SpeakerNPC> npclist = new ArrayList<SpeakerNPC>();
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		
		GenericNPCLoader npcLoader = new GenericNPCLoader();
		final ArrayList<NPCStructure> npcs = npcLoader.getNPCs();
		
		for(int ii=0; ii<npcs.size(); ii++){
		final int i = ii;
		final SpeakerNPC npc = new SpeakerNPC(npcs.get(i).getName()){
			
			
			@Override
			protected void createDialog() {
                // Lets the NPC reply when a player says "job"
                addJob(npcs.get(i).getJob());
                // Lets the NPC reply when a player asks for help
                addHelp(npcs.get(i).getHelp());
                // respond about a special trigger word
                ArrayList<String> keys = npcs.get(i).getKeys();
                for(int j=0; j<keys.size(); j++){
                	addReply(keys.get(j),npcs.get(i).getReply(keys.get(j)));
                }
                
                // Create quest dialog. First time they meet, during the quest and post quest
                if(npcs.get(i).hasQuest()){
	                add(ConversationStates.IDLE,
							ConversationPhrases.GREETING_MESSAGES,
							new AndCondition(
									new GreetingMatchesNameCondition(getName()),
									new QuestNotStartedCondition(npcs.get(i).getQuestName().toLowerCase().replace(" ", "_")),
									new ChatCondition() {
										@Override
										public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
											return !player.isGhost();
										}
									}),
					        ConversationStates.ATTENDING,
					        null,
					        new SayTextAction(npcs.get(i).getIntroduceQuestMessage()));
					
					// this is the condition for any other case while the quest is active, not covered by the quest.
					add(ConversationStates.IDLE,
							ConversationPhrases.GREETING_MESSAGES,
							new GreetingMatchesNameCondition(getName()), true,
					        ConversationStates.ATTENDING,
					        npcs.get(i).getDuringQuestMessage(),
					        null);
					
					add(ConversationStates.IDLE,
							ConversationPhrases.GREETING_MESSAGES,
							new AndCondition(new GreetingMatchesNameCondition(getName()),
									new QuestCompletedCondition(npcs.get(i).getQuestName().toLowerCase().replace(" ", "_"))),
					        ConversationStates.ATTENDING,
					        null,
					        new SayTextAction(npcs.get(i).getCompletedQuestMessage()));
                }else{
                	addGreeting();
                }
                
                // use standard goodbye, but you can also set one inside the ()
                addGoodbye();
			};
			
			@Override
			protected void createPath() {
				List<Node> nodes=new LinkedList<Node>();
                nodes.add(new Node(9,5));
                nodes.add(new Node(14,5));
                setPath(new FixedPath(nodes, true));
			};
			
		};
		npclist.add(npc);
		
		// Set quest
		if(npcs.get(i).hasQuest()){
			npc.addInitChatMessage(null, new ChatAction() {
				@Override
				public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
					if (!player.hasQuest(npcs.get(i).getName().replace(" ", "")+"FirstChat")) {
						player.setQuest(npcs.get(i).getName().replace(" ", "")+"FirstChat", "done");
						((SpeakerNPC) raiser.getEntity()).listenTo(player, "hi");
					}
				}
			});
		}
		
		// This determines how the NPC will look like. welcomernpc.png is a picture in data/sprites/npc/
        npc.setEntityClass(npcs.get(i).getClassz());
        // set a description for when a player does 'Look'
        npc.setDescription(npcs.get(i).getDescription());
        // Set the initial position to be the first node on the Path you defined above.
        npc.setPosition(npcs.get(i).getX(), npcs.get(i).getY());
        npc.initHP(100);

        zone.add(npc);
		}
	}
	
	public ArrayList<SpeakerNPC> getNPCs(){
		return npclist;
	}
}
