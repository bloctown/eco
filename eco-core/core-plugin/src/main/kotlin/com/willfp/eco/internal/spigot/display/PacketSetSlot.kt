package com.willfp.eco.internal.spigot.display

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import com.willfp.eco.core.AbstractPacketAdapter
import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.display.Display
import com.willfp.eco.internal.spigot.display.frame.DisplayFrame
import com.willfp.eco.internal.spigot.display.frame.lastDisplayFrame
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PacketSetSlot(plugin: EcoPlugin) : AbstractPacketAdapter(plugin, PacketType.Play.Server.SET_SLOT, false) {
    override fun onSend(
        packet: PacketContainer,
        player: Player,
        event: PacketEvent
    ) {
        packet.itemModifier.modify(0) { item: ItemStack? ->
            Display.display(
                item!!, player
            )
        }

        player.lastDisplayFrame = DisplayFrame.EMPTY
    }
}