package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.List;

import games.stendhal.server.core.config.GenericQuestLoader;
import games.stendhal.server.entity.player.Player;

public class GenericQuest {
	
	private ArrayList<AbstractQuest> quests = new ArrayList<AbstractQuest>();
	
	public GenericQuest(){
		GenericQuestLoader questLoader = new GenericQuestLoader();
		ArrayList<QuestStructure> questsStructures = new ArrayList<QuestStructure>(questLoader.getQuests());
		
		for(int current=0; current<questsStructures.size(); current++){
			final int i = current;
			final String QUEST_SLOT = questsStructures.get(i).getName().toLowerCase().replace(" ", "_");
			
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
					if (player.hasQuest(questsStructures.get(i).getPhase(0).getNPC()+"FirstChat")) {
						res.add("Met"+questsStructures.get(i).getPhase(0).getNPC());
					}
					if (!player.hasQuest(QUEST_SLOT)) {
						return res;
					}
					
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
