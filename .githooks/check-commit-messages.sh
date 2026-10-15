#!/bin/sh
# Checks every commit in a range against Conventional Commits.
# Used by the pre-push hook and by CI.
#
#   .githooks/check-commit-messages.sh origin/main..HEAD
#   .githooks/check-commit-messages.sh <sha> --not --remotes
set -eu

. "$(dirname "$0")/lib/conventional.sh"

failed=0
for sha in $(git rev-list --no-merges "$@"); do
    subject=$(git log -1 --format=%s "$sha")
    if ! is_conventional "$subject"; then
        echo "✗ $(git log -1 --format='%h %s' "$sha")" >&2
        failed=1
    fi
done

if [ "$failed" -ne 0 ]; then
    echo >&2
    echo "Some commit messages do not follow the rules. Fix them with: git rebase -i" >&2
    print_commit_rules
fi
exit $failed
