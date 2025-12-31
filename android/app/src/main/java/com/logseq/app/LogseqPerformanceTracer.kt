package com.logseq.app

import android.os.Build
import android.os.SystemClock
import android.os.Trace
import android.util.Log

object LogseqPerformanceTracer {
    private const val TAG = "LogseqPerf"
    private const val MAX_SECTION_LENGTH = 120

    fun <T> trace(name: String, block: () -> T): T {
        val active = start(name)
        return try {
            block()
        } finally {
            active.close()
        }
    }

    fun start(name: String): ActiveTrace = ActiveTrace(name)

    class ActiveTrace(private val rawName: String) : AutoCloseable {
        private val safeName: String = rawName.take(MAX_SECTION_LENGTH)
        private val start = SystemClock.elapsedRealtime()
        private var closed = false
        private val enabled = BuildConfig.DEBUG

        init {
            if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Trace.beginSection(safeName)
            }
        }

        override fun close() {
            if (closed) return
            closed = true
            val durationMs = SystemClock.elapsedRealtime() - start
            if (enabled) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    Trace.endSection()
                }
                Log.d(TAG, "$safeName finished in ${durationMs}ms")
            }
        }
    }
}
