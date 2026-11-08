#!/usr/bin/env sh
# Small Gradle bootstrapper for this starter project.
# It downloads the pinned Gradle distribution on first use, then delegates to it.
set -eu

GRADLE_VERSION="8.14.5"
GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
DIST_DIR="$GRADLE_USER_HOME/wrapper/dists/gradle-$GRADLE_VERSION-bin/mybudget"
GRADLE_HOME="$DIST_DIR/gradle-$GRADLE_VERSION"
GRADLE_BIN="$GRADLE_HOME/bin/gradle"

if [ ! -x "$GRADLE_BIN" ]; then
  mkdir -p "$DIST_DIR"
  ZIP_FILE="$DIST_DIR/gradle-$GRADLE_VERSION-bin.zip"
  URL="https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"
  echo "Downloading Gradle $GRADLE_VERSION..."
  if command -v curl >/dev/null 2>&1; then
    curl --fail --location --retry 3 "$URL" --output "$ZIP_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP_FILE" "$URL"
  else
    echo "Please install curl or wget to download Gradle." >&2
    exit 1
  fi
  if ! command -v unzip >/dev/null 2>&1; then
    echo "Please install unzip to extract Gradle." >&2
    exit 1
  fi
  unzip -q -o "$ZIP_FILE" -d "$DIST_DIR"
  rm -f "$ZIP_FILE"
fi

exec "$GRADLE_BIN" "$@"
