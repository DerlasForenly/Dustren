package dustren.main;

import java.util.List;

public class PlayerSkills {
	private List<Skill> skills;
	private String name;
	
	private long sneakStart;
	private long sprintStart;
	
	public double getBonus(Skill.SkillType skillType) {
		double bonus = 0;
		Skill s = getSkill(skillType);
		
		switch (skillType) {
		case SWORD_MASTERY:
			bonus = (double) s.getLvl() / 10;
			break;
		case AXE_MASTERY:
			bonus = (double) s.getLvl() / 10;
			break;
		case HAND_MASTERY:
			bonus = (double) s.getLvl() / 10;
			break;
		default:
			break;
		}
		
		return bonus;
	}

	public PlayerSkills(String name, List<Skill> skills) {
		this.name = name;
		this.setSkills(skills);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public void setSkill(Skill skill) {
		for (Skill s : skills) {
			if (s.getId() == skill.getId()) {
				s = skill;
			}
		}
	}
	
	public Skill getSkill(Skill.SkillType skill) {
		for (Skill s : skills) {
			if (s.getId() == skill.ordinal() + 1) {
				return s;
			}
		}
		return null;
	}

	public long getSneakStart() {
		return sneakStart;
	}

	public void setSneakStart(long sneakStart) {
		this.sneakStart = sneakStart;
	}

	public long getSprintStart() {
		return sprintStart;
	}

	public void setSprintStart(long sprintStart) {
		this.sprintStart = sprintStart;
	}
}
