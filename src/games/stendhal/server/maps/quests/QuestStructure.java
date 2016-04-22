package games.stendhal.server.maps.quests;

import java.util.ArrayList;

public class QuestStructure {
	private String name = "";
	private String description = "";
	private ArrayList<Phase> phases = new ArrayList<Phase>();

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Phase getPhase(int index) {
		return phases.get(index);
	}
	public void addPhase() {
		phases.add(new Phase());
	}

	public class Phase{
		private String name = "";
		private String npc = "";

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public String getNPC() {
			return npc;
		}
		public void setNPC(String name) {
			this.npc = name;
		}
		
	}
	

}
