package com.hcsmp.common.factories;

import org.bukkit.configuration.file.FileConfiguration;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.when;

public class PluginConfigFactory
{
    private FileConfiguration fileConfiguration = PowerMockito.mock(FileConfiguration.class);
    
    
    public PluginConfigFactory withConfigProperty(String key, Object value)
    {
        when(fileConfiguration.get(key)).thenReturn(value);
        
        return this;
    }
    
    public FileConfiguration build()
    {
        return fileConfiguration; 
    }
}
