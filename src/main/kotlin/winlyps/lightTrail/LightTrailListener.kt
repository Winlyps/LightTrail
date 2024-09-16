package winlyps.lightTrail

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin

class LightTrailListener(private val plugin: JavaPlugin) : Listener {

    // Map of light-providing items to their corresponding particle colors
    private val lightItemColors = mapOf(
            Material.TORCH to Color.YELLOW,
            Material.WALL_TORCH to Color.YELLOW,
            Material.SOUL_TORCH to Color.BLUE,
            Material.SOUL_WALL_TORCH to Color.BLUE,
            Material.LANTERN to Color.ORANGE,
            Material.SOUL_LANTERN to Color.BLUE,
            Material.GLOWSTONE to Color.YELLOW,
            Material.SEA_LANTERN to Color.AQUA,
            Material.CAMPFIRE to Color.ORANGE,
            Material.SOUL_CAMPFIRE to Color.BLUE,
            Material.SHROOMLIGHT to Color.RED,
            Material.END_ROD to Color.WHITE,
            Material.REDSTONE_TORCH to Color.RED,
            Material.REDSTONE_WALL_TORCH to Color.RED
    )

    // Map to store the previous location of each player
    private val previousLocations = mutableMapOf<Player, org.bukkit.Location>()

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val mainHandItem = player.inventory.itemInMainHand
        val offHandItem = player.inventory.itemInOffHand

        // Check if either hand holds an item that provides light
        val item = if (lightItemColors.containsKey(mainHandItem.type)) mainHandItem.type else offHandItem.type
        if (lightItemColors.containsKey(item)) {
            val previousLocation = previousLocations[player]
            if (previousLocation != null && previousLocation != player.location) {
                val color = lightItemColors[item] ?: Color.WHITE
                val dustOptions = Particle.DustOptions(color, 1.0f)
                previousLocation.world?.spawnParticle(Particle.REDSTONE, previousLocation, 10, 0.1, 0.1, 0.1, 0.0, dustOptions)
            }
            previousLocations[player] = player.location.clone()
        }
    }
}