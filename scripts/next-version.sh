# next-version.sh
#   Guess the next version of the sbt-guardrail plugin based on the current library dependency
#   Ideally, this'll let actions automatically tag and release.
#
#   Strategy is to pull the dev.guardrail %% guardrail % <VERSION>,
#   ... check to see if a tag with that version exists,
#   ... if so, exit 1
#   ... if not, emit that version to stdout

# Extract library version
version="$(grep -ho 'dev.guardrail:guardrail_2.12:[^"]*"' build.gradle | grep -ho '\<\([0-9]\+\)\(\.[0-9]\+\)\{1,\}')"

exists=0
# Check to see if we've already released for this library version
if git rev-parse "v${version}" >/dev/null 2>&1; then
  exists=1
fi
