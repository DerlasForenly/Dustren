package dustren.main;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class DustrenScoreboard {
	private static ScoreboardManager sm;
	private static Scoreboard sb;
	
	public DustrenScoreboard() {}
	
	public static Scoreboard sideBar() {
		sm = Bukkit.getScoreboardManager();
		sb = sm.getNewScoreboard();
		Objective obj = sb.registerNewObjective("test", "dummy", ChatColor.BLUE + "title");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score score = obj.getScore(ChatColor.GOLD + "money: $" + ChatColor.GREEN + "1");
		score.setScore(3);
		
		return sb;
	}
}
