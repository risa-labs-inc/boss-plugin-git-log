package ai.rever.boss.plugin.dynamic.gitlog

import ai.rever.boss.plugin.api.DynamicPlugin
import ai.rever.boss.plugin.api.GitDataProvider
import ai.rever.boss.plugin.api.PluginContext

/**
 * Git Log dynamic plugin - Loaded from external JAR.
 *
 * View commit history with graph visualization
 */
class GitLogDynamicPlugin : DynamicPlugin {
    override val pluginId: String = "ai.rever.boss.plugin.dynamic.gitlog"
    override val displayName: String = "Git Log (Dynamic)"
    override val version: String = "1.0.3"
    override val description: String = "View commit history with graph visualization"
    override val author: String = "Risa Labs"
    override val url: String = "https://github.com/risa-labs-inc/boss-plugin-git-log"

    private var gitDataProvider: GitDataProvider? = null

    override fun register(context: PluginContext) {
        gitDataProvider = context.gitDataProvider

        context.panelRegistry.registerPanel(GitLogInfo) { ctx, panelInfo ->
            GitLogComponent(ctx, panelInfo, gitDataProvider)
        }
    }
}
