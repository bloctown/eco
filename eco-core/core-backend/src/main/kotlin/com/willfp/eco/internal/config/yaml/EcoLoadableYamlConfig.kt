@file:Suppress("DEPRECATION")

package com.willfp.eco.internal.config.yaml

import com.willfp.eco.core.PluginLike
import com.willfp.eco.core.config.interfaces.LoadableConfig
import com.willfp.eco.core.config.interfaces.WrappedYamlConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

open class EcoLoadableYamlConfig(
    configName: String,
    private val plugin: PluginLike,
    private val subDirectoryPath: String,
    val source: Class<*>
) : EcoYamlConfigWrapper<YamlConfiguration>(), WrappedYamlConfiguration, LoadableConfig {

    private val configFile: File
    private val name: String = "$configName.yml"

    fun reloadFromFile() {
        handle.load(getConfigFile())
    }

    final override fun createFile() {
        val inputStream = source.getResourceAsStream(resourcePath)!!
        val outFile = File(this.plugin.dataFolder, resourcePath)
        val lastIndex = resourcePath.lastIndexOf('/')
        val outDir = File(this.plugin.dataFolder, resourcePath.substring(0, lastIndex.coerceAtLeast(0)))
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        if (!outFile.exists()) {
            val out: OutputStream = FileOutputStream(outFile)
            inputStream.copyTo(out)
            out.close()
            inputStream.close()
        }
    }

    override fun getResourcePath(): String {
        val resourcePath: String = if (subDirectoryPath.isEmpty()) {
            name
        } else {
            subDirectoryPath + name
        }
        return "/$resourcePath"
    }

    @Throws(IOException::class)
    override fun save() {
        handle.save(getConfigFile())
    }

    override fun getName(): String {
        return name
    }

    override fun getConfigFile(): File {
        return configFile
    }

    override fun getBukkitHandle(): YamlConfiguration? {
        return handle
    }

    init {
        val directory = File(this.plugin.dataFolder, subDirectoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        if (!File(directory, name).exists()) {
            createFile()
        }
        configFile = File(directory, name)
        this.plugin.configHandler.addConfig(this)
        init(YamlConfiguration.loadConfiguration(configFile))
    }
}