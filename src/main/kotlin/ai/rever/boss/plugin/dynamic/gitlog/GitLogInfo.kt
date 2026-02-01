package ai.rever.boss.plugin.dynamic.gitlog

import ai.rever.boss.plugin.api.Panel.Companion.left
import ai.rever.boss.plugin.api.Panel.Companion.bottom
import ai.rever.boss.plugin.api.PanelId
import ai.rever.boss.plugin.api.PanelInfo
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree

object GitLogInfo : PanelInfo {
    override val id = PanelId("git-log", 15)
    override val displayName = "Git Log"
    override val icon = Icons.Outlined.AccountTree
    override val defaultSlotPosition = left.bottom
}
