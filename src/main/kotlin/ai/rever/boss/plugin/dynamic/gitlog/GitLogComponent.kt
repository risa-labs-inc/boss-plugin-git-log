package ai.rever.boss.plugin.dynamic.gitlog

import ai.rever.boss.plugin.api.PanelComponentWithUI
import ai.rever.boss.plugin.api.PanelInfo
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

/**
 * Git Log panel component (Dynamic Plugin)
 *
 * This is a stub implementation. Full functionality requires
 * host services not yet exposed through PluginContext.
 */
class GitLogComponent(
    ctx: ComponentContext,
    override val panelInfo: PanelInfo
) : PanelComponentWithUI, ComponentContext by ctx {

    @Composable
    override fun Content() {
        GitLogContent()
    }
}
