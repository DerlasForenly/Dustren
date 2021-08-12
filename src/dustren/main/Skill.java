package dustren.main;

public class Skill {
	private int id;
	private int lvl;
	private int freeExp;
	
	public static enum SkillType {
		SWORD_MASTERY,
		AXE_MASTERY,
		HAND_MASTERY,
		ARCHERY, 
		BLOCK, 
		ALCHEMY, 
		CRAFT, 
		COOKING,
		STEALTH, 
		TRADING, 
		ATHLETICS, 
		THROWING, 
		FISHING, 
		FARMING, 
		MINING,
		MERCHANTING,
		CASTING,
		LUMBERING;
	}
	
	public boolean addExpNaturally(int exp) {
		freeExp += exp;
		int expForNextLvl = getExpForNextLvl();
		
		if (freeExp >= expForNextLvl) {
			lvl += 1;
			freeExp -= expForNextLvl;
			return true;
		}
		
		return false;
	}
	
	public int getExpToNextLvl() {
		return getExpForNextLvl() - freeExp;
	}
	
	public int getExpForNextLvl() {
		return lvl * 100 + (100 * lvl) * lvl / 50;
	}

	public Skill(int id, int lvl, int freeExp) {
		this.setId(id);
		this.setLvl(lvl);
		this.setFreeExp(freeExp);
	}
	
	public Skill(SkillType skill, int lvl, int freeExp) {
		this.setId(skill.ordinal() + 1);
		this.setLvl(lvl);
		this.setFreeExp(freeExp);
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getFreeExp() {
		return freeExp;
	}

	public void setFreeExp(int freeExp) {
		this.freeExp = freeExp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
