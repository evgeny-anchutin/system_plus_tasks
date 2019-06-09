package com.systemplus.helpers

import android.view.GestureDetector
import android.view.MotionEvent

class DoubleTapListener(private val listener: () -> Unit) : GestureDetector.SimpleOnGestureListener() {
    override fun onDoubleTap(e: MotionEvent): Boolean {
        listener.invoke()
        return true
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return true
    }
}