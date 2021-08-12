package dustren.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import dustren.main.Skill.SkillType;

public class Dustren extends JavaPlugin {
	
	private static Dustren instance;
	private SQLDatabase db;
	private List<PlayerSkills> list = new ArrayList<>();
	
	public static Dustren getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		File config = new File(getDataFolder() + File.separator + "config.yml");
		if (!config.exists()) {
			getLogger().info("New config has been created");
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		
		try {
			db = new SQLDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(instance);
		}
				
		Bukkit.getPluginManager().registerEvents(new Handler(), this);
	}

	public void onDisable() {
		save();
	}
	
	public PlayerSkills getPlayerWithName(String name) {
		for (PlayerSkills ps : list) {
			if (ps.getName().equals(name)) {
				return ps;
			}
		}
		return null;
	}

	public void addPlayer(String name) {
		List<Skill> skills = new ArrayList<>();
		for (SkillType skill : SkillType.values()) {
			skills.add(new Skill(skill, 1, 0));
		}
		PlayerSkills ps = new PlayerSkills(name, skills);
		list.add(ps);
		
		Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				PlayerSkills ps = getPlayerWithName(name);
				ps = db.getData(name);
				
				if (ps == null) {
					db.createData(name);
				}
				
				ps = db.getData(name);
				//ps.getSkill(Skill.SkillType.FENCING).setLvl(2);

				//list.add(ps);
				getLogger().info(String.format("New player loaded to the list [%d]\nPlayer: %s, skills: %d", list.size(), ps.getName(), ps.getSkills().size()));
			}
			
		});
	}
	
	public SQLDatabase getDb() {
		return db;
	}

	public void setDb(SQLDatabase db) {
		this.db = db;
	}

	public void save() {
		synchronized (instance) {
			db.saveData(list);
			list.clear();
		}
	}
	
	public void savePlayer(String name) {
		synchronized (instance) {
			PlayerSkills ps = getPlayerWithName(name);
			db.updateData(ps);
			list.remove(ps);
		}
	}

}
