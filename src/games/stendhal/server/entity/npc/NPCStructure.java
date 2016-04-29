package games.stendhal.server.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NPCStructure {
	private String name = "unknown";
	private String classz = "welcomernpc";
	private String description = "Generic NPC";
	private int x = 20;
	private int y = 30;
	private String job = "";
	private String help = "";
	private Map<String, String> replies = new HashMap<String, String>();
	private ArrayList<String> keywords = new ArrayList<String>();
	private String questName = "";
	private boolean hasQuest = false;
	private String introduceQuestMessage = "";
	private String duringQuestMessage = "";
	private String completedQuestMessage = "";
	
	private int hp = 100;
	private String sellsWhat = ""; 
	private boolean canHeal = false;
	private int healMul = 0;
	private int healMod = 0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(!name.equals(""))
		this.name = name;
	}
	
	public String getClassz() {
		return classz;
	}
	public void setClassz(String classz) {
		this.classz = classz;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void setJob(String job){
		if(!job.equals(""))
			this.job = job;
	}	
	public String getJob(){
		return job;
	}
	
	public void setHelp(String help){
		if(!help.equals(""))
		this.help = help;
	}	
	public String getHelp(){
		return help;
	}
	
	public void setReply(String keyword, String message){
		replies.put(keyword, message);
		keywords.add(keyword);
	}
	
	public String getReply(String keyword){
		return replies.get(keyword);
	}
	
	public ArrayList<String> getKeys(){
		return keywords;
	}
	
	public String getQuestName() {
		return questName;
	}
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	
	public boolean hasQuest(){
		return hasQuest;
	}
	public void setQuest(String hasQuest){
		this.hasQuest = hasQuest.equals("true");
	}
	
	public String getIntroduceQuestMessage() {
		return introduceQuestMessage;
	}
	public void setIntroduceQuestMessage(String introduceQuestMessage) {
		this.introduceQuestMessage = introduceQuestMessage;
	}
	
	public String getDuringQuestMessage() {
		return duringQuestMessage;
	}
	public void setDuringQuestMessage(String duringQuestMessage) {
		this.duringQuestMessage = duringQuestMessage;
	}
	
	public String getCompletedQuestMessage() {
		return completedQuestMessage;
	}
	public void setCompletedQuestMessage(String postQuestMessage) {
		this.completedQuestMessage = postQuestMessage;
	}
	
	public int getHP() {
		return hp;
	}
	
	public void setHP(int hp){
		this.hp = hp;
	}
	public String getSellsWhat() {
		return sellsWhat;
	}
	public void setSellsWhat(String sellsWhat) {
		this.sellsWhat = sellsWhat;
	}
	public boolean isCanHeal() {
		return canHeal;
	}
	public void setCanHeal(boolean canHeal) {
		this.canHeal = canHeal;
	}
	public int getHealMul() {
		return healMul;
	}
	public void setHealMul(int healMul) {
		this.healMul = healMul;
	}
	public int getHealMod() {
		return healMod;
	}
	public void setHealMod(int healMod) {
		this.healMod = healMod;
	}
	
	
}
