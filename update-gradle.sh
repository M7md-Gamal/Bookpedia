#!/bin/bash
# Run this script from inside the project folder

# ✅ Versions you want to enforce
GRADLE_VERSION="8.14.3"
KOTLIN_VERSION="2.2.10"

# --- Update gradle-wrapper.properties ---
WRAPPER_FILE="gradle/wrapper/gradle-wrapper.properties"
if [ -f "$WRAPPER_FILE" ]; then
  echo "Updating Gradle wrapper to $GRADLE_VERSION ..."
  sed -i "s|distributionUrl=.*|distributionUrl=https\://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip|" "$WRAPPER_FILE"
else
  echo "⚠️ No gradle-wrapper.properties found"
fi

# --- Update Kotlin version in libs.versions.toml ---
VERSIONS_TOML="gradle/libs.versions.toml"
if [ -f "$VERSIONS_TOML" ]; then
  echo "Updating Kotlin version in libs.versions.toml to $KOTLIN_VERSION ..."
  sed -i "s|\(kotlin\s*=\s*\"\)[0-9.]\+\(\"\)|\1${KOTLIN_VERSION}\2|" "$VERSIONS_TOML"
else
  echo "⚠️ No libs.versions.toml found"
fi

echo "✅ Update complete!"
