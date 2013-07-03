package com.hcsmp.common.factories;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.powermock.api.mockito.PowerMockito;

public class PluginManagerFactory
{
    PluginManager pluginManager = PowerMockito.mock(PluginManager.class);
    ArrayList<JavaPlugin> plugins = new ArrayList<JavaPlugin>();
    
    public PluginManagerFactory withPlugin(String pluginName, JavaPlugin plugin)
    {
        plugin = PowerMockito.spy(plugin);
        plugins.add(plugin);
        
        when(pluginManager.getPlugin(pluginName)).thenReturn(plugin);
        
        return this;
    }
    
    public PluginManagerFactory withPlugin(String pluginName, JavaPlugin plugin, FileConfiguration fileConfiguration) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        Field newConfigField = JavaPlugin.class.getDeclaredField("newConfig");
        newConfigField.setAccessible(true);
        newConfigField.set(plugin, fileConfiguration);
        
        return withPlugin(pluginName, plugin);
    }
    
    public PluginManager build()
    {
        when(pluginManager.getPlugins()).thenReturn( plugins.toArray(new JavaPlugin[plugins.size()]) );
        when(pluginManager.getPermission(anyString())).thenReturn(null);
        
        return pluginManager;
    }
}
