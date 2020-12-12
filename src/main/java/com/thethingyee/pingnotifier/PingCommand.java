package com.thethingyee.pingnotifier;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class PingCommand implements CommandExecutor, TabCompleter {

    public PingNotifier plugin;

    public PingCommand(PingNotifier plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player)sender);
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("set")) {
                    try {
                        int num = Integer.parseInt(args[1]);

                        PingUtil.getHigh().put(player.getUniqueId(), num);
                        PingUtil.getWarn().put(player.getUniqueId(), true);

                        player.sendMessage(ChatColor.GREEN + "Maximum ping has been set to " + num + " ms!");
                    } catch(Exception e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " just made an error!");
                    }
                }

            }
            else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("stop")) {
                    boolean bul = PingUtil.getWarn().get(player.getUniqueId());
                    if(PingUtil.getHigh().get(player.getUniqueId()) != null) {
                        if(bul) {
                            PingUtil.getWarn().put(player.getUniqueId(), false);
                            player.sendMessage(ChatColor.GREEN + "Warn Notification is disabled!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Warn Notification is already disabled!");
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("start")) {
                    PingUtil.start(player);
                }
                if(args[0].equalsIgnoreCase("getping")) {
                    try {
                        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);

                        player.sendMessage(ChatColor.GREEN + "You are running at " + ping + " ms!");
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                if(args[0].equalsIgnoreCase("getmaxping")) {
                    if(PingUtil.getWarn().get(player.getUniqueId()) == null) {
                        player.sendMessage(ChatColor.RED + "You haven't set the maximum time!");
                    } else {
                        int num = PingUtil.getHigh().get(player.getUniqueId());
                        player.sendMessage(ChatColor.RED + "Maximum ping: " + num + " ms");
                    }
                }
                if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.GOLD + "--------------------------");
                    player.sendMessage(ChatColor.YELLOW + "Commands for PingNotifier:");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier help " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Displays all commands available for PingNotifier.");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier set <maxnumber> " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Sets the maximum ping. When exceeded, Player will be notified.");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier getmaxping " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Tells the maximum ping that the player has set.");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier getping " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Tells the player's ping.");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier start " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Starts the notifier.");
                    player.sendMessage(ChatColor.BLUE + "/pingnotifier stop " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Stops the notifier.");
                    player.sendMessage(ChatColor.GOLD + "--------------------------");
                }
                if(args[0].equalsIgnoreCase("set")) {
                    player.sendMessage(ChatColor.RED + "Missing arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier set <maxnumber>");
                }
            }
            if(args.length >= 2) {
                if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.RED + "Too many arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier help");
                }
                if(args[0].equalsIgnoreCase("start")) {
                    player.sendMessage(ChatColor.RED + "Too many arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier start");
                }
                if(args[0].equalsIgnoreCase("stop")) {
                    player.sendMessage(ChatColor.RED + "Too many arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier stop");
                }
                if(args[0].equalsIgnoreCase("getping")) {
                    player.sendMessage(ChatColor.RED + "Too many arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier getping");
                }
                if(args[0].equalsIgnoreCase("getmaxping")) {
                    player.sendMessage(ChatColor.RED + "Too many arguments!");
                    player.sendMessage(ChatColor.RED + "Usage: /pingnotifier getmaxping");
                }
            } else if(args.length == 0) {
                player.sendMessage(ChatColor.GOLD + "--------------------------");
                player.sendMessage(ChatColor.YELLOW + "Commands for PingNotifier:");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier help " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Displays all commands available for PingNotifier.");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier set <maxnumber> " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Sets the maximum ping. When exceeded, Player will be notified.");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier getmaxping " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Tells the maximum ping that the player has set.");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier getping " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Tells the player's ping.");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier start " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Starts the notifier.");
                player.sendMessage(ChatColor.BLUE + "/pingnotifier stop " + ChatColor.DARK_GRAY + "- " + ChatColor.AQUA + "Stops the notifier.");
                player.sendMessage(ChatColor.GOLD + "--------------------------");
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmds = Arrays.asList("help", "set", "getmaxping", "getping", "start", "stop");
        List<String> finalcmds = Lists.newArrayList();

        if(args.length == 1) {
            for (String arg : cmds) {
                if(arg.toLowerCase().startsWith(args[0].toLowerCase())) finalcmds.add(arg);
            }
            return finalcmds;
        }
        return null;
    }
}
