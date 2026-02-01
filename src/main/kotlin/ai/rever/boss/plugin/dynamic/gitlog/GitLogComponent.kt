package ai.rever.boss.plugin.dynamic.gitlog

import ai.rever.boss.plugin.api.GitDataProvider
import ai.rever.boss.plugin.api.PanelComponentWithUI
import ai.rever.boss.plugin.api.PanelInfo
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

/**
 * Git Log panel component (Dynamic Plugin)
 */
class GitLogComponent(
    ctx: ComponentContext,
    override val panelInfo: PanelInfo,
    private val gitDataProvider: GitDataProvider?
) : PanelComponentWithUI, ComponentContext by ctx {

    private val viewModel: GitLogViewModel? = gitDataProvider?.let { GitLogViewModel(it) }

    @Composable
    override fun Content() {
        GitLogContent(viewModel)
    }

    fun dispose() {
        viewModel?.dispose()
    }
}
