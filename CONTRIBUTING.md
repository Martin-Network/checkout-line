# Contributing

Thanks for taking a look! Issues and pull requests are welcome.

## Setup

1. JDK 27 (Gradle downloads it automatically if it is missing).
2. Activate the git hooks once after cloning:

   ```
   git config core.hooksPath .githooks
   ```

3. Optional but recommended: install [gitleaks](https://github.com/gitleaks/gitleaks), the pre-commit hook then scans every commit for secrets.

Build and run the tests:

```
./gradlew build
```

## Commit messages

This project follows [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/):

```
<type>(<optional scope>): <description>

<optional body: what and why>

<optional footer, e.g. BREAKING CHANGE: ...>
```

**Types:** `feat` new behaviour · `fix` bug fix · `refactor` restructuring without changing behaviour · `perf` performance · `test` tests · `docs` documentation · `style` formatting · `build` Gradle, dependencies · `ci` GitHub Actions · `chore` everything else · `revert` undo an earlier commit

**Scopes:** `sim`, `render`, `scenes`, `app`, `deps`

**Description:** imperative mood ("add", not "added"), lower case, no full stop at the end, at most 72 characters for the whole first line.

Examples:

```
feat(sim): let customers walk to the end of the line
fix(sim): stop customers from stacking on the same spot
test(sim): cover first-come-first-served order
docs: explain how to record frames
build(deps): update processing core to 4.5.7
```

The `commit-msg` hook checks the format, CI checks it again for every pull request.

## Workflow

- Branch off `main` (e.g. `feat/walking-customers`), open a pull request, merge when CI is green.
- Clean up your local history (`git rebase -i`) **before** pushing. Never rewrite history that is already pushed.
- `fixup!` and `squash!` commits are fine locally, but are rejected on push.

## What never goes into the repository

- Secrets of any kind: tokens, passwords, keys, `.env` files
- Personal data: e-mail addresses (use your GitHub noreply address), local paths such as `/Users/<name>`
- Generated output: `build/`, `frames/`, `screenshots/`, videos
- IDE and OS files

The `pre-commit` hook guards against most of this. It can be skipped with `git commit --no-verify`, but please don't.

## License

By contributing you agree that your contributions are licensed under the [MIT License](LICENSE).
