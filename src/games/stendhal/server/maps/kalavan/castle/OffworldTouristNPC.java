package games.stendhal.server.maps.kalavan.castle;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class OffworldTouristNPC implements ZoneConfigurator {

	@Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(StendhalRPZone zone) {
		SpeakerNPC npc = new SpeakerNPC("Sark") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			public void createDialog() {
				addGreeting("Hello.  Bonjour.  Nnọọ.  Buna.");
				addJob("Back home, I am a transferase reduction specialist.  Just now, I am visiting your world as an envoy for my people."); 
				addQuest("I'm making a record of my visit.  Will you help me gather some representative objects from your culture?");
				addGoodbye("Goodbye.  Au revoir.  Ka ọ dị.  La revedere.");
			}
		};
		npc.setEntityClass("grandadnpc");
		npc.setDescription("You see a small alien-like creature.  It is carrying a strange flat box, which it looks at often.");
		npc.setPosition(13, 5);
		npc.initHP(100);
		zone.add(npc);
	}
}