package com.logseq.app

import android.os.SystemClock
import android.view.MotionEvent

/**
 * Detects a quick two-finger double tap to trigger the command palette.
 * This avoids interfering with single-finger editing/scrolling.
 */
class CommandPaletteGesture(private val onTrigger: () -> Unit) {
    private var lastTwoFingerUpTime = 0L

    fun onTouch(event: MotionEvent) {
        if (event.pointerCount < 2) return
        if (event.actionMasked == MotionEvent.ACTION_POINTER_UP) {
            val now = SystemClock.elapsedRealtime()
            val delta = now - lastTwoFingerUpTime
            if (delta in 40..280) {
                onTrigger()
                lastTwoFingerUpTime = 0L
            } else {
                lastTwoFingerUpTime = now
            }
        }
    }
}
