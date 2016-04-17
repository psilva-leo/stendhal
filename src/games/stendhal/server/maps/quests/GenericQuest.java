package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.core.config.GenericQuestLoader;
import games.stendhal.server.entity.player.Player;

public class GenericQuest {
	
	private static final String QUEST_SLOT = "introduce_players";
	private ArrayList<AbstractQuest> quests = new ArrayList<AbstractQuest>();
	
	public GenericQuest(){
		GenericQuestLoader questLoader = new GenericQuestLoader();
		ArrayList<QuestStructure> questsStructures = new ArrayList<QuestStructure>(questLoader.getQuests());
		
		for(int current=0; current<questsStructures.size(); current++){
			final int i = current;
			AbstractQuest currentQuest = new AbstractQuest() {
				
				@Override
				public String getSlotName() {
					return QUEST_SLOT;
				}

				@Override
				public void addToWorld() {
					fillQuestInfo(
							questsStructures.get(i).getName(),
							questsStructures.get(i).getDescription(),
							false);
					
				}

				@Override
				public List<String> getHistory(Player player) {
					final List<String> res = new ArrayList<String>();
					return res;
				}

				@Override
				public String getName() {
					return questsStructures.get(i).getName();
				}
			};
			quests.add(currentQuest);
		}
	}
	
	public ArrayList<AbstractQuest> getQuests(){
		return quests;
	}
	

}
