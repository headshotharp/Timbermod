package de.headshotharp.timbermod;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Timbermod extends JavaPlugin implements Listener {
	private PluginDescriptionFile pdf;
	private ArrayList<User> users = new ArrayList<User>();

	public boolean contains(String name) {
		for (User u : users) {
			if (u.getName().equals(name))
				return true;
		}
		return false;
	}

	public void addUser(String name) {
		if (!contains(name)) {
			users.add(new User(name));
		}
	}

	public User getUser(String name) {
		for (User u : users) {
			if (u.getName().equals(name))
				return u;
		}
		User u = new User(name);
		users.add(u);
		return u;
	}

	public boolean hasTimber(String name) {
		for (User u : users) {
			if (u.getName().equals(name))
				return u.hasTimber();
		}
		return true;
	}

	public void setTimber(String name, boolean timber) {
		User u = getUser(name);
		u.setTimber(timber);
	}

	@Override
	public void onEnable() {
		pdf = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		System.out.println(pdf.getName() + " Plugin v" + pdf.getVersion() + " enabled");
	}

	@Override
	public void onDisable() {
		System.out.println(pdf.getName() + " Plugin v" + pdf.getVersion() + " disabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("timber")) {
			if (args.length > 0) {
				boolean timber;
				if (args[0].equals("0") || args[0].equalsIgnoreCase("off")) {
					timber = false;
				} else if (args[0].equals("1") || args[0].equalsIgnoreCase("on")) {
					timber = true;
				} else {
					return false;
				}
				setTimber(player.getName(), timber);
				player.sendMessage(
						ChatColor.GRAY + "Timbermod v" + pdf.getVersion() + " " + (timber ? "enabled" : "disabled"));
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		addUser(event.getPlayer().getName());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		if ((event.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_AXE)
				|| (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STONE_AXE)
				|| (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOOD_AXE)
				|| (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE)
				|| (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_AXE)) {
			if (hasTimber(event.getPlayer().getName())) {
				if (isWood(event.getBlock().getType())) {
					timberWoodAt(event.getBlock().getLocation());
				}
				if (isLeave(event.getBlock().getType())) {
					timberLeavesAt(event.getBlock().getLocation());
				}
			}
		}
	}

	public void timberWoodAt(Location loc) {
		while (isWood(loc.getBlock().getType())) {
			breakWood(loc);
			loc.setY(loc.getBlockY() + 1);
		}
		for (int i = 0; i < 4; i++) {
			breakWood(loc);
			loc.setY(loc.getBlockY() + 1);
		}
	}

	public void breakWood(Location loca) {
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() + 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() - 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() - 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isWood(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		loca.setZ(loca.getBlockZ() + 1);
	}

	public boolean isWood(Material mat) {
		if (mat == Material.LOG || mat == Material.LOG_2)
			return true;
		return false;
	}

	public boolean isLeave(Material mat) {
		if (mat == Material.LEAVES || mat == Material.LEAVES_2)
			return true;
		return false;
	}

	public void timberLeavesAt(Location loc) {
		while (isLeave(loc.getBlock().getType())) {
			breakLeaves(loc);
			loc.setY(loc.getBlockY() + 1);
		}
		for (int i = 0; i < 4; i++) {
			breakLeaves(loc);
			loc.setY(loc.getBlockY() + 1);
		}
	}

	public void breakLeaves(Location loca) {
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() + 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() - 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setZ(loca.getBlockZ() - 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() + 1);
		if (isLeave(loca.getBlock().getType()))
			loca.getBlock().breakNaturally();
		loca.setX(loca.getBlockX() - 1);
		loca.setZ(loca.getBlockZ() + 1);
	}
}
