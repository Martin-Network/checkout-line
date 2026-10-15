# Shared rules for commit messages: Conventional Commits 1.0.0
# https://www.conventionalcommits.org/en/v1.0.0/

COMMIT_TYPES='feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert'
MAX_SUBJECT_LENGTH=72

# Succeeds if the first line of a commit message follows the rules.
is_conventional() {
    subject=$1

    # Messages that git writes itself
    case $subject in
        "Merge "* | "Revert \""*) return 0 ;;
    esac

    [ "${#subject}" -le "$MAX_SUBJECT_LENGTH" ] || return 1

    printf '%s\n' "$subject" |
        grep -Eq "^($COMMIT_TYPES)(\([a-z0-9][a-z0-9._-]*\))?!?: [^ ].*[^. ]$"
}

print_commit_rules() {
    cat >&2 <<EOF

Commit messages follow Conventional Commits:

    <type>(<optional scope>): <description>

    types:  ${COMMIT_TYPES}
    rules:  imperative mood, no full stop at the end, at most ${MAX_SUBJECT_LENGTH} characters

    examples:
        feat(sim): let customers walk to the end of the line
        fix(render): keep the progress bar below 100 percent
        docs: explain how to record frames

EOF
}
