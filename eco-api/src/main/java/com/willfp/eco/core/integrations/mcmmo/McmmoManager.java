package com.willfp.eco.core.integrations.mcmmo;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to handle mcmmo integrations.
 */
public final class McmmoManager {
    /**
     * A set of all registered integrations.
     */
    private static final Set<McmmoWrapper> REGISTERED = new HashSet<>();

    /**
     * Register a new integration.
     *
     * @param integration The integration to register.
     */
    public static void register(@NotNull final McmmoWrapper integration) {
        REGISTERED.add(integration);
    }

    /**
     * Get bonus drop count from block.
     *
     * @param block The block.
     * @return The bonus drop count.
     */
    public static int getBonusDropCount(@NotNull final Block block) {
        for (McmmoWrapper mcmmoWrapper : REGISTERED) {
            return mcmmoWrapper.getBonusDropCount(block);
        }
        return 0;
    }

    /**
     * Get if event is fake.
     *
     * @param event The event to check.
     * @return If the event is fake.
     */
    public static boolean isFake(@NotNull final Event event) {
        for (McmmoWrapper mcmmoWrapper : REGISTERED) {
            return mcmmoWrapper.isFake(event);
        }
        return false;
    }

    private McmmoManager() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
