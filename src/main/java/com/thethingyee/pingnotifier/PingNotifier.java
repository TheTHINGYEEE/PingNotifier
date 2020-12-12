package com.thethingyee.pingnotifier;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PingNotifier extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getCommand("pingnotifier").setExecutor(new PingCommand(this));
        this.getCommand("pingnotifier").setTabCompleter(new PingCommand(this));
        this.getServer().getPluginManager().registerEvents(this, this);
        new PingUtil(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(PingUtil.getWarn().get(e.getPlayer().getUniqueId()) == null) return;
        boolean bul = PingUtil.getWarn().get(e.getPlayer().getUniqueId());
        if(PingUtil.getHigh().get(e.getPlayer().getUniqueId()) != null) {
            if(bul) {
                e.getPlayer().sendMessage(ChatColor.GREEN + "Your ping notifier is still on!");
                PingUtil.start(e.getPlayer());
            }
        }

    }
}
