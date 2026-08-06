# BOSS Git Log

Commit history for the open project, in the left sidebar.

Sits directly below [Git
Changes](https://github.com/risa-labs-inc/boss-plugin-git-status) and shares its approach:
every operation goes through the host's `GitDataProvider`, so the plugin never shells out to
`git` itself.

## What it does

- **Commit list** for the current project, with a live commit count in the toolbar.
- **Per commit**: subject, short hash, author name and email, and any refs or branch tags
  pointing at it, plus a button to copy the full hash.
- **Per-commit actions**: Cherry-pick, Revert and Checkout, each reporting the outcome as a
  toast.
- **Refresh** on demand.
- **Distinct empty states** for "Not a Git repository", "No commits yet", and "Git data
  provider not available".

This is a flat chronological list. There is no commit graph.

## MCP tools

| Tool | Purpose |
|---|---|
| `git_log` | List recent commits. `limit` defaults to 30 and is clamped to 1-500 |
| `git_cherry_pick` | Cherry-pick a commit onto the current branch |
| `git_revert` | Revert a commit, creating a new revert commit |

Neither mutating tool is permission-gated. `git_checkout` is contributed by [Git
Changes](https://github.com/risa-labs-inc/boss-plugin-git-status), not by this plugin, even
though the panel's Checkout button lives here.

## Requirements

- BOSS >= 9.2.20, boss-plugin-api >= 1.0.20
- `gitDataProvider` from the host, which is what needs a working `git`.

## Build

```bash
./gradlew buildPluginJar
cp build/libs/boss-plugin-git-log-*.jar ~/.boss/plugins/
```

See [AGENTS.md](AGENTS.md) for architecture and conventions.

## License

Proprietary - Risa Labs Inc.
