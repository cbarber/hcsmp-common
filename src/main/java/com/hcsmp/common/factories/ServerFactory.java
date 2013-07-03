package com.hcsmp.common.factories;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;

public class ServerFactory
{
    private Server server = PowerMockito.mock(Server.class);
    private String name = "TestBukkit";
    
    private PluginManager pluginManager = new PluginManagerFactory().build();
    private BukkitScheduler scheduler = new BukkitSchedulerFactory().build();
    private ArrayList<Player> players = new ArrayList<Player>(); 
    
    public ServerFactory withPluginManager(PluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
        return this;
    }
    
    public ServerFactory withPlayer(Player player)
    {
        when(server.getPlayer(player.getName())).thenReturn(player);
        return this;
    }
    
    public Server build() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        when(server.getName()).thenReturn(name);
        when(server.getLogger()).thenReturn(Logger.getLogger("Minecraft"));
        when(server.getPluginManager()).thenReturn(pluginManager);
        when(server.getOnlinePlayers()).thenReturn(players.toArray(new Player[players.size()]));
        when(server.getScheduler()).thenReturn(scheduler);
        
        for (Plugin plugin : pluginManager.getPlugins())
        {
            initializePlugin(plugin);
        }
        
        return server;
    }

    private void initializePlugin(final Plugin plugin)
    {
        when(plugin.getServer()).thenReturn(server);
        
        Answer<PluginCommand> commandAnswer = new Answer<PluginCommand>() {
            public PluginCommand answer(InvocationOnMock invocation) throws Throwable {
                String arg;
                try {
                    arg = (String) invocation.getArguments()[0];
                } catch (Exception e) {
                    return null;
                }
                java.lang.reflect.Constructor<PluginCommand> constructor = PluginCommand.class.getConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);
                return PowerMockito.spy(constructor.newInstance(arg, plugin));
            }
        };

        ((JavaPlugin) doAnswer(commandAnswer).when(plugin)).getCommand(anyString());
    }
}