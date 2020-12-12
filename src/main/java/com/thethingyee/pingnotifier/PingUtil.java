package com.thethingyee.pingnotifier;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by TheTHINGYEEEE on 9/16/20.
 */
public class PingUtil {

    public static PingNotifier plugin;

    public PingUtil(PingNotifier plugin) {
        this.plugin = plugin;
    }

    private static Map<UUID, Integer> high = new HashMap<UUID, Integer>();
    private static Map<UUID, Boolean> warn = new HashMap<UUID, Boolean>();

    public static Map<UUID, Boolean> getWarn() {
        return warn;
    }
    public static Map<UUID, Integer> getHigh() {
        return high;
    }
    public static void start(Player player) {
        if(PingUtil.getWarn().get(player.getUniqueId()) == null) {
            player.sendMessage(ChatColor.RED + "You haven't set the maximum time!");
        } else {
            new BukkitRunnable() {

                @Override
                public void run() {
                    int num = PingUtil.getHigh().get(player.getUniqueId());
                    boolean bul = PingUtil.getWarn().get(player.getUniqueId());
                    if(PingUtil.getHigh().get(player.getUniqueId()) != null) {
                        if(!bul) {
                            this.cancel();
                        }
                        try {
                            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                            int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                            if(num <= ping) {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Ping exceeded! At " + ping + " ms!"));
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.runTaskTimer(plugin, 0, 20);
            player.sendMessage(ChatColor.GREEN + "Warn Notification successfully started!");
        }
    }
}
