package dustren.main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.mysql.jdbc.Statement;

import dustren.main.Skill.SkillType;

@SuppressWarnings("unused")
public class SQLDatabase {
	
	private String url;
	private String user;
	private String password;
	
	public SQLDatabase() throws Exception {
		FileConfiguration c = Dustren.getInstance().getConfig();
		
		Connection conn;
		
		if (c.getBoolean("MySQL.enable")) {
			String host = c.getString("MySQL.host");
			int port = c.getInt("MySQL.port");
			user = c.getString("MySQL.user");
			password = c.getString("MySQL.password");
			String dbname = c.getString("MySQL.dbname");
			url = "jdbc:mysql://" + host + ":" + port + "/" + dbname; 
			
			try {
				Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
				conn = getConnection();
				Dustren.getInstance().getLogger().info("Using MySQL");
			} catch (Exception e) {
				e.printStackTrace();
				url = "jdbc:sqlite:" + Dustren.getInstance().getDataFolder() + File.separator + "database.db";
				user = null;
				Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
				conn = getConnection();
				Dustren.getInstance().getLogger().warning("Failed to connect to MySQL, using sqlite");
			}
			
		} else {
			url = "jdbc:sqlite:" + Dustren.getInstance().getDataFolder() + File.separator + "database.db";
			user = null;
			Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
			conn = getConnection();
			Dustren.getInstance().getLogger().info("Using sqlite");
		}
		
		java.sql.Statement s = conn.createStatement();
		
		String statement = "CREATE TABLE IF NOT EXISTS player_skills (";
		statement += "'id' INTEGER PRIMARY KEY AUTOINCREMENT,";
		statement += "'player_name' TEXT NOT NULL,";
		statement += "'skill_id' INT NOT NULL,";
		statement += "'lvl' INT NOT NULL,";
		statement += "'free_exp' INT NOT NULL)";
		s.executeUpdate(statement);
		
	}
	
	public Connection getConnection() throws SQLException {
		if (user != null) return DriverManager.getConnection(url, user, password);
		else return DriverManager.getConnection(url);
	}
	
	public void saveData(List<PlayerSkills> list) {
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
//			for (PlayerSkills player : list) {
//				
//			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<PlayerSkills> getData() {
		List<PlayerSkills> data = new ArrayList<>();
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			ResultSet result = s.executeQuery("SELECT * from player_skills");
			
			while (result.next()) {
				List<Skill> skills = new ArrayList<>();
				
				skills.add(new Skill(result.getInt("skill_id"), result.getInt("lvl"), result.getInt("free_exp")));
				
				data.add(new PlayerSkills(result.getString("player_name"), skills));
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public PlayerSkills getData(String name) {
		PlayerSkills data = null;
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			ResultSet result = s.executeQuery(String.format("SELECT * from player_skills WHERE player_name='%s'", name));
			
			List<Skill> skills = new ArrayList<>();
			String player_name = null;
			
			while (result.next()) {
				player_name = result.getString("player_name");
				skills.add(new Skill(result.getInt("skill_id"), result.getInt("lvl"), result.getInt("free_exp")));
			}
			
			if (skills.size() != 0) {
				data = new PlayerSkills(player_name, skills);
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public void saveData(PlayerSkills ps) {
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			String player_name = ps.getName();
			
			for (Skill skill : ps.getSkills()) {
				String statement = "INSERT INTO player_skills ('player_name', 'skill_id', 'lvl', 'free_exp') VALUES ('%s', %d, %d, %d)";
				s.executeUpdate(String.format(statement, player_name, skill.getId(), skill.getLvl(), skill.getFreeExp()));
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateData(PlayerSkills ps) {
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			String player_name = ps.getName();
			
			for (Skill skill : ps.getSkills()) {
				String statement = "UPDATE player_skills SET lvl = %d, free_exp = %d WHERE player_name='%s' AND skill_id = %d";
				s.executeUpdate(String.format(statement, skill.getLvl(), skill.getFreeExp(), player_name, skill.getId()));
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createData(String name) {
		Dustren.getInstance().getLogger().warning("Player data was not found, creating a new fields");
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			for (SkillType skill : SkillType.values()) {
				String statement = "INSERT INTO player_skills ('player_name', 'skill_id', 'lvl', 'free_exp') VALUES ('%s', %d, %d, %d)";
				s.executeUpdate(String.format(statement, name, skill.ordinal() + 1, 1, 0));
				
				//Dustren.getInstance().getLogger().warning(String.format(statement, name, skill.ordinal() + 1, 1, 0));
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int clear() {
		int i = 0;
		try {
			Connection c = getConnection();
			java.sql.Statement s = c.createStatement();
			
			i = s.executeUpdate("DELETE");
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
}
