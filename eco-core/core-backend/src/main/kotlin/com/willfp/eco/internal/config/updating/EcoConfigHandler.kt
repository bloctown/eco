package com.willfp.eco.internal.config.updating

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.config.interfaces.LoadableConfig
import com.willfp.eco.core.config.updating.ConfigHandler
import com.willfp.eco.core.config.updating.ConfigUpdater
import com.willfp.eco.internal.config.json.EcoLoadableJSONConfig
import com.willfp.eco.internal.config.json.EcoUpdatableJSONConfig
import com.willfp.eco.internal.config.updating.exceptions.InvalidUpdateMethodException
import com.willfp.eco.internal.config.yaml.EcoLoadableYamlConfig
import com.willfp.eco.internal.config.yaml.EcoUpdatableYamlConfig
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner

class EcoConfigHandler(
    private val plugin: EcoPlugin
) : ConfigHandler {
    private val reflections: Reflections = Reflections(
        this.plugin::class.java.classLoader,
        MethodAnnotationsScanner()
    )

    private val configs = mutableListOf<LoadableConfig>()

    override fun callUpdate() {
        for (method in reflections.getMethodsAnnotatedWith(ConfigUpdater::class.java)) {
            kotlin.runCatching {
                when (method.parameterCount) {
                    0 -> method.invoke(null)
                    1 -> method.invoke(null, this.plugin)
                    else -> throw InvalidUpdateMethodException("Update method must have 0 parameters or a plugin parameter.")
                }
            }.onFailure {
                it.printStackTrace()
                plugin.logger.severe("Update method ${method.toGenericString()} generated an exception")
            }
        }
    }

    override fun saveAllConfigs() {
        for (config in configs) {
            config.save()
        }
    }

    override fun addConfig(config: LoadableConfig) {
        configs.add(config)
    }

    override fun updateConfigs() {
        for (config in configs) {
            when (config) {
                is EcoUpdatableYamlConfig -> config.update()
                is EcoUpdatableJSONConfig -> config.update()
                is EcoLoadableYamlConfig -> config.reloadFromFile()
                is EcoLoadableJSONConfig -> config.reloadFromFile()
            }
        }
    }
}