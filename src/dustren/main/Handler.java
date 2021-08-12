package dustren.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import org.bukkit.event.entity.*;

@SuppressWarnings("unused")
public class Handler implements Listener {
	
	private boolean isSword(Material material) {
		switch (material) {
		case WOODEN_SWORD:
			return true;
		case STONE_SWORD:
			return true;
		case IRON_SWORD:
			return true;
		case GOLDEN_SWORD:
			return true;
		case DIAMOND_SWORD:
			return true;
		case NETHERITE_SWORD:
			return true;
		default:
			return false;
		}
	}
	
	private boolean isAxe(Material material) {
		switch (material) {
		case WOODEN_AXE:
			return true;
		case STONE_AXE:
			return true;
		case IRON_AXE:
			return true;
		case GOLDEN_AXE:
			return true;
		case DIAMOND_AXE:
			return true;
		case NETHERITE_AXE:
			return true;
		default:
			return false;
		}
	}
	
	private boolean isMob(EntityType entity) {
		return true;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {	
		Player p = e.getPlayer();
		p.setScoreboard(DustrenScoreboard.sideBar());
		Dustren.getInstance().addPlayer(p.getName());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Dustren.getInstance().savePlayer(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent e) {
		String name = e.getPlayer().getName();
		PlayerSkills ps = Dustren.getInstance().getPlayerWithName(name);
		
		Skill skill = ps.getSkill(Skill.SkillType.THROWING);
		skill.setFreeExp(skill.getFreeExp() + 1);
		
		e.getPlayer().sendMessage("throwing: +1");
	}
	
	@EventHandler
	public void onFishing(PlayerFishEvent e) {
		Player p = e.getPlayer();
		int exp = 1;
		
		switch (e.getState()) {
		case BITE:
			break;
		case CAUGHT_ENTITY:
			break;
		case CAUGHT_FISH:
			exp = 3;
			break;
		case FISHING:
			break;
		default:
			break;
		}
		
		String name = p.getName();
		PlayerSkills ps = Dustren.getInstance().getPlayerWithName(name);
		
		Skill skill = ps.getSkill(Skill.SkillType.FISHING);
		skill.addExpNaturally(exp);
		
		NmsManager.sendTitle(p, "hello");
		NmsManager.sendSubtitle(p, "hello");
		NmsManager.sendActionBar(p, "hello");
		
	}

	@EventHandler
	public void onHarwest(PlayerHarvestBlockEvent e) {
		Player p = e.getPlayer();
		int exp = 1;
		
		String name = p.getName();
		PlayerSkills ps = Dustren.getInstance().getPlayerWithName(name);
		
		Skill skill = ps.getSkill(Skill.SkillType.FARMING);
		skill.addExpNaturally(exp);
		
		//((CraftPlayer) p).getHandle().playerConnection.sendPacket(Packet.getExpMessage(exp));
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

	}
	
	@EventHandler
	public void onPlayerIsDamager(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && isMob(e.getEntity().getType())) {
			int exp = 1;
			Player p = (Player) e.getDamager();
			PlayerSkills ps = Dustren.getInstance().getPlayerWithName(p.getName());
			
			if (isSword(p.getInventory().getItemInMainHand().getType())) {
				e.setDamage(e.getDamage() + ps.getBonus(Skill.SkillType.SWORD_MASTERY));
				Skill skill = ps.getSkill(Skill.SkillType.SWORD_MASTERY);
				if (skill.addExpNaturally(exp)) p.sendMessage(String.format("SWORD_MASTERY LVL UP: %s", skill.getLvl()));
				
			} else if (isAxe(p.getInventory().getItemInMainHand().getType())) {
				e.setDamage(e.getDamage() + ps.getBonus(Skill.SkillType.AXE_MASTERY));
				Skill skill = ps.getSkill(Skill.SkillType.AXE_MASTERY);
				if (skill.addExpNaturally(exp)) p.sendMessage(String.format("AXE_MASTERY LVL UP: %s", skill.getLvl()));
				
			} else if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
				e.setDamage(e.getDamage() + ps.getBonus(Skill.SkillType.HAND_MASTERY));
				Skill skill = ps.getSkill(Skill.SkillType.HAND_MASTERY);
				if (skill.addExpNaturally(exp)) p.sendMessage(String.format("HAND_MASTERY LVL UP: %s", skill.getLvl()));
			}
		}
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		
	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e) {
		//This event will fire when a player is finishing consuming an item (food, potion, milk bucket).
		e.getPlayer().sendMessage("on item consume");
	}
	
	@EventHandler
	public void ItemMend(PlayerItemMendEvent e) {
		//Represents when a player has an item repaired via the Mending enchantment.
		//Обозначает, когда игрок ремонтирует предмет с помощью чар Mending.
		
		e.getPlayer().sendMessage("on item mend");
	}
	
	@EventHandler
	public void onRiptide(PlayerRiptideEvent e) {
		//Holds information for player movement events.
		
		e.getPlayer().sendMessage("on riptide");
	}
	
	@EventHandler
	public void onShear(PlayerShearEntityEvent e) {
		//farming
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Date date = new Date();
		Player p = e.getPlayer();
		if (e.getPlayer().isSneaking()) {
			long end = date.getTime();
			PlayerSkills ps = Dustren.getInstance().getPlayerWithName(p.getName());
			int exp = (int) (end - ps.getSneakStart()) / 1000;
			Skill skill = ps.getSkill(Skill.SkillType.STEALTH);
			
			//((CraftPlayer) p).getHandle().playerConnection.sendPacket(Packet.getExpMessage(exp));
			
			skill.addExpNaturally(exp);
		} else {
			long start = date.getTime();
			PlayerSkills ps = Dustren.getInstance().getPlayerWithName(p.getName());
			ps.setSneakStart(start);
		}
	}
	
	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e) {
		Date date = new Date();
		Player p = e.getPlayer();
		if (e.getPlayer().isSprinting()) {
			long end = date.getTime();
			PlayerSkills ps = Dustren.getInstance().getPlayerWithName(p.getName());
			int exp = (int) (end - ps.getSprintStart()) / 1000;
			Skill skill = ps.getSkill(Skill.SkillType.ATHLETICS);
			
			//((CraftPlayer) p).getHandle().playerConnection.sendPacket(Packet.getExpMessage(exp));
			
			skill.addExpNaturally(exp);
		} else {
			long start = date.getTime();
			PlayerSkills ps = Dustren.getInstance().getPlayerWithName(p.getName());
			ps.setSprintStart(start);
		}
	}
	
}
