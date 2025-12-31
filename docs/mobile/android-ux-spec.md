# Android DB UX polish (0.11.0+)

This document summarizes the intended native-first UX for the Android DB app while keeping the existing Capacitor/WebView editor.

## Shell & navigation
- **Material 3 chrome**: Use Compose-based `TopAppBar` with the current page title and contextual actions. Bottom navigation hosts Journal, Pages, Search, and Graph/Settings tabs. Each tab preserves its own back stack.
- **Back behavior**: System back gesture and button map to the active tab’s stack. When the stack is at root, back exits the app (after handing control to JS for in-editor pop if needed).
- **Safe areas**: Surfaces should pad against status/navigation bars; WebView padding is managed by the Compose host to avoid overlapping toolbars.

## Editor UI
- **Editor toolbar**: Lives above the keyboard, shows default actions (indent/outdent, undo/redo, link, bold, italic, code, image, mic, slash). Toolbar order is user-customizable and stored in native preferences.
- **Selection bar**: When blocks are multi-selected, show a native action bar with bulk actions (indent/outdent, move, link, delete, export).
- **Gestures**: Provide both gestures and explicit buttons for indent/outdent. Bullet drag shows an insertion indicator and auto-scrolls while dragging.
- **Slash palette**: Opens instantly with debounce + incremental search; results load progressively to avoid blocking typing.

## Theming
- Default to system theme (light/dark). Expose overrides in Settings (System / Light / Dark) with optional accent tint.
- Native Material color scheme is mirrored into the WebView via existing theme bridge.

## Import/Export
- Dedicated Settings subsection with Import and Export cards:
  - Export zips the current graph with progress and error states.
  - Import accepts zip or folder, confirms overwrite, and shows a determinate progress bar.
- Operations run off the UI thread and surface completion toasts.

## Onboarding & tips
- **First-launch flow**: 3–5 pages that highlight navigation tabs, editing gestures, and toolbar customization. Skippable at any step.
- **Contextual tips**: One-time coach marks for indent/outdent gestures, bullet long-press (select), and drag-to-reorder.

## Performance guardrails
- Avoid heavy work on the UI thread; expensive DB/indexing work is async with progress indicators.
- Journal rendering is incremental/virtualized; avoid rendering unbounded blocks.
- Add lightweight debug tracing hooks around navigation, toolbar rendering, and search to spot regressions without sending analytics.
