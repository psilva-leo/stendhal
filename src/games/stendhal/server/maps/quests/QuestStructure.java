package games.stendhal.server.maps.quests;

import java.util.ArrayList;

public class QuestStructure {
	private String name = "";
	private String description = "";
	private ArrayList<String> npcs = new ArrayList<String>();

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
	public ArrayList<String> getNpcs() {
		return npcs;
	}
	public void addNPC(String name) {
		npcs.add(name);
	}
	

}
