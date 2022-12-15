/**
 * Timbermod
 * Copyright © 2021 gmasil.de
 *
 * This file is part of Timbermod.
 *
 * Timbermod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Timbermod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Timbermod. If not, see <https://www.gnu.org/licenses/>.
 */
package de.headshotharp.timbermod;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    protected static final List<Material> AXE_MATERIALS = Arrays.asList( //
            Material.WOODEN_AXE, //
            Material.STONE_AXE, //
            Material.IRON_AXE, //
            Material.GOLDEN_AXE, //
            Material.DIAMOND_AXE, //
            Material.NETHERITE_AXE //
    );

    protected static final List<Material> LOG_MATERIALS = Arrays.asList( //
            Material.ACACIA_LOG, //
            Material.BIRCH_LOG, //
            Material.DARK_OAK_LOG, //
            Material.JUNGLE_LOG, //
            Material.MANGROVE_LOG, //
            Material.OAK_LOG, //
            Material.SPRUCE_LOG, //
            Material.STRIPPED_ACACIA_LOG, //
            Material.STRIPPED_BIRCH_LOG, //
            Material.STRIPPED_DARK_OAK_LOG, //
            Material.STRIPPED_JUNGLE_LOG, //
            Material.STRIPPED_MANGROVE_LOG, //
            Material.STRIPPED_OAK_LOG, //
            Material.STRIPPED_SPRUCE_LOG //
    );

    protected static final List<Material> LEAVE_MATERIALS = Arrays.asList( //
            Material.ACACIA_LEAVES, //
            Material.BIRCH_LEAVES, //
            Material.DARK_OAK_LEAVES, //
            Material.JUNGLE_LEAVES, //
            Material.MANGROVE_LEAVES, //
            Material.OAK_LEAVES, //
            Material.SPRUCE_LEAVES //
    );

    private PluginDescriptionFile pdf;
    private List<User> users = new LinkedList<>();

    public boolean contains(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return true;
            }
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
            if (u.getName().equals(name)) {
                return u;
            }
        }
        User u = new User(name);
        users.add(u);
        return u;
    }

    public boolean hasTimber(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return u.hasTimber();
            }
        }
        return true;
    }

    public void setTimber(String name, boolean timber) {
        User u = getUser(name);
        u.setTimber(timber);
    }

    @Override
    public void onEnable() {
        pdf = getDescription();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        getLogger().info(pdf.getName() + " Plugin v" + pdf.getVersion() + " enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info(pdf.getName() + " Plugin v" + pdf.getVersion() + " disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
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
        if (hasAxe(event.getPlayer()) && hasTimber(event.getPlayer().getName())) {
            if (isWood(event.getBlock().getType())) {
                timberWoodAt(event.getBlock().getLocation());
            }
            if (isLeave(event.getBlock().getType())) {
                timberLeavesAt(event.getBlock().getLocation());
            }
        }
    }

    public void timberWoodAt(Location loc) {
        while (isWood(loc.getBlock().getType())) {
            breakWood(loc);
            loc.setY(loc.getY() + 1);
        }
        for (int i = 0; i < 4; i++) {
            breakWood(loc);
            loc.setY(loc.getY() + 1);
        }
    }

    public void breakWood(Location loca) {
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() + 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() - 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() - 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isWood(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        loca.setZ(loca.getZ() + 1);
    }

    public boolean hasAxe(Player player) {
        return AXE_MATERIALS.contains(player.getInventory().getItemInMainHand().getType());
    }

    public boolean isWood(Material mat) {
        return LOG_MATERIALS.contains(mat);
    }

    public boolean isLeave(Material mat) {
        return LEAVE_MATERIALS.contains(mat);
    }

    public void timberLeavesAt(Location loc) {
        while (isLeave(loc.getBlock().getType())) {
            breakLeaves(loc);
            loc.setY(loc.getY() + 1);
        }
        for (int i = 0; i < 4; i++) {
            breakLeaves(loc);
            loc.setY(loc.getY() + 1);
        }
    }

    public void breakLeaves(Location loca) {
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() + 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() - 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setZ(loca.getZ() - 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() + 1);
        if (isLeave(loca.getBlock().getType())) {
            loca.getBlock().breakNaturally();
        }
        loca.setX(loca.getX() - 1);
        loca.setZ(loca.getZ() + 1);
    }
}
