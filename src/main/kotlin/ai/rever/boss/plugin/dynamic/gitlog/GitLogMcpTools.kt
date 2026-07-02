package ai.rever.boss.plugin.dynamic.gitlog

import ai.rever.boss.plugin.api.GitDataProvider
import ai.rever.boss.plugin.api.GitOperationResultData
import ai.rever.boss.plugin.api.McpToolDefinition
import ai.rever.boss.plugin.api.McpToolHandler
import ai.rever.boss.plugin.api.McpToolProvider
import ai.rever.boss.plugin.api.McpToolResult

/**
 * MCP tools contributed by the Git Log plugin: read the recent commit log and
 * cherry-pick / revert commits in the current BOSS project. Registered in
 * [GitLogDynamicPlugin.register]; removed automatically on disable/unload.
 */
internal class GitLogMcpToolProvider(
    override val providerId: String,
    private val gitDataProvider: GitDataProvider?,
) : McpToolProvider {

    override fun tools(): List<McpToolDefinition> = listOf(
        McpToolDefinition(
            name = "git_log",
            description = "List recent commits in the current BOSS project (short hash, subject, author).",
            inputSchema = LIMIT_SCHEMA,
            handler = McpToolHandler { args ->
                val gp = gitDataProvider ?: return@McpToolHandler unavailable()
                val limit = (args.int("limit") ?: 30).coerceIn(1, 500)
                gp.refreshLog(limit)
                val commits = gp.commitLog.value.take(limit)
                if (commits.isEmpty()) return@McpToolHandler McpToolResult("No commits (or not a git repository).")
                McpToolResult(commits.joinToString("\n") { c ->
                    val refs = if (c.refs.isNotEmpty()) " (${c.refs.joinToString(", ")})" else ""
                    "${c.shortHash} ${c.subject}$refs — ${c.author}"
                })
            },
        ),
        McpToolDefinition(
            name = "git_cherry_pick",
            description = "Cherry-pick a commit onto the current branch of the current project.",
            inputSchema = hashSchema("Hash of the commit to cherry-pick."),
            readOnly = false,
            handler = McpToolHandler { args -> hashOp(args) { gp, h -> gp.cherryPick(h) } },
        ),
        McpToolDefinition(
            name = "git_revert",
            description = "Revert a commit in the current project (creates a new revert commit).",
            inputSchema = hashSchema("Hash of the commit to revert."),
            readOnly = false,
            handler = McpToolHandler { args -> hashOp(args) { gp, h -> gp.revert(h) } },
        ),
    )

    private suspend fun hashOp(
        args: ai.rever.boss.plugin.api.McpToolArgs,
        op: suspend (GitDataProvider, String) -> GitOperationResultData,
    ): McpToolResult {
        val gp = gitDataProvider ?: return unavailable()
        val hash = args.string("hash")
            ?: return McpToolResult("Missing required argument: hash", isError = true)
        return when (val r = op(gp, hash)) {
            is GitOperationResultData.Success -> McpToolResult(r.message ?: "OK")
            is GitOperationResultData.Error -> McpToolResult(r.message, isError = true)
        }
    }

    private fun unavailable(): McpToolResult =
        McpToolResult("Git data provider unavailable in this context.", isError = true)

    private fun hashSchema(desc: String): String =
        """{"type":"object","properties":{"hash":{"type":"string","description":"$desc"}},"required":["hash"]}"""

    private companion object {
        const val LIMIT_SCHEMA =
            """{"type":"object","properties":{"limit":{"type":"integer","description":"Max commits to return (default 30)."}}}"""
    }
}
