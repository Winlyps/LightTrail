package winlyps.lightTrail

import org.bukkit.plugin.java.JavaPlugin

class LightTrail : JavaPlugin() {

    override fun onEnable() {
        // Register the event listener
        server.pluginManager.registerEvents(LightTrailListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}