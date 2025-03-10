package com.willfp.eco.core.integrations.customitems;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to handle custom item integrations.
 */
public final class CustomItemsManager {
    /**
     * A set of all registered integrations.
     */
    private static final Set<CustomItemsWrapper> REGISTERED = new HashSet<>();

    /**
     * Register a new integration.
     *
     * @param integration The integration to register.
     */
    public static void register(@NotNull final CustomItemsWrapper integration) {
        REGISTERED.add(integration);
    }

    /**
     * Register all the custom items for a specific plugin into eco.
     *
     * @see com.willfp.eco.core.items.Items
     */
    public static void registerAllItems() {
        for (CustomItemsWrapper customItemsWrapper : REGISTERED) {
            customItemsWrapper.registerAllItems();
        }
    }

    private CustomItemsManager() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
