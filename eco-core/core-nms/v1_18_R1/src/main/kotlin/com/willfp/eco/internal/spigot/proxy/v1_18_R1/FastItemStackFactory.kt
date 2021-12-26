package com.willfp.eco.internal.spigot.proxy.v1_18_R1

import com.willfp.eco.core.fast.FastItemStack
import com.willfp.eco.internal.spigot.proxy.v1_18_R1.fast.NMSFastItemStack
import com.willfp.eco.internal.spigot.proxy.FastItemStackFactoryProxy
import org.bukkit.inventory.ItemStack

class FastItemStackFactory : FastItemStackFactoryProxy {
    override fun create(itemStack: ItemStack): FastItemStack {
        return NMSFastItemStack(itemStack)
    }
}