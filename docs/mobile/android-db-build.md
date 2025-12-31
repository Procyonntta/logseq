# Android DB app build guide (0.11.0+)

This guide focuses on the Capacitor-based Android build for the Logseq DB app (0.11.0+). It assumes you are working from the repository root.

## Prerequisites
- Android Studio Hedgehog+ with Android SDK Platform 34 (and matching build-tools).
- JDK 17 (used by the Gradle wrapper in `android/`).
- Node.js 18+ with Yarn installed.
- At least 15 GB of free disk space for Gradle caches and the generated APK.

## One-time setup
1. Install Java/Android tooling above.
2. From the repo root, install JavaScript dependencies and build mobile assets:
   ```bash
   yarn install
   yarn release-mobile
   ```
   `release-mobile` creates the production web assets under `public/` that are later copied into the Android project.
3. Sync Capacitor plugins/assets into the Android project:
   ```bash
   npx cap sync android
   ```
   This writes `android/app/src/main/assets/public` and refreshes Capacitor plugin wrappers.

## Building a debug APK
Run the Gradle assemble task from the repo root:
```bash
cd android
./gradlew :app:assembleDebug
```

Artifacts:
- Debug APK: `android/app/build/outputs/apk/debug/app-debug.apk`
- If you need to install directly on a device or emulator:
  ```bash
  adb install -r app/build/outputs/apk/debug/app-debug.apk
  ```

## Iterating in Android Studio
- Open `android/` in Android Studio. The project uses the Gradle wrapper and Material 3 Compose dependencies already declared in `android/app/build.gradle`.
- If Studio cannot locate the web assets, re-run `yarn release-mobile` followed by `npx cap sync android`.
- To point the app at a local dev server instead of packaged assets, temporarily enable the `server` block in `capacitor.config.ts` with your LAN IP and re-run `npx cap sync android`.

## Common pitfalls
- **Stale assets**: If UI changes do not appear, rebuild the web bundle (`yarn release-mobile`) and re-run Capacitor sync.
- **SDK mismatch**: Ensure Android SDK 34 is installed; lower SDKs can fail during Compose compilation.
- **Gradle daemon memory**: If builds fail with OOM, export `ORG_GRADLE_JAVA_OPTS="-Xmx4g"` before invoking Gradle.
- **node_modules missing**: Mobile build scripts expect `node_modules/` to exist; always run `yarn install` first.
