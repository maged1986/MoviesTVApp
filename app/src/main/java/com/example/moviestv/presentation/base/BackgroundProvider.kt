package com.example.moviestv.presentation.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.leanback.app.BackgroundManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.example.moviestv.utils.Constants.BACKGROUND_UPDATE_DELAY
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject


class BackgroundProvider @Inject constructor(val activity: Activity){

   /* private var mActivityWeakReference: WeakReference<Activity>? = null
    private var mBackgroundManager: BackgroundManager? = null
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private var mBackgroundURI: String? = null
    private var mBackgroundTimer: Timer? = null

    var instance: BackgroundProvider? = null


    //  The activity to which this WindowManager is attached
   init {
        mActivityWeakReference = WeakReference(activity)
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager?.attach(activity.window)
    }

    internal val mGlideDrawableSimpleTarget: SimpleTarget<GlideDrawable?> =
        object : SimpleTarget<GlideDrawable?>() {
            override fun onResourceReady(
                resource: GlideDrawable?,
                glideAnimation: GlideAnimation<in GlideDrawable?>?
            ) {
                setBackground(resource)
            }
        }

    fun loadImage(imageUrl: String?) {
        mBackgroundURI = imageUrl
        startBackgroundTimer()
    }

    fun setBackground(drawable: Drawable?) {
        if (mBackgroundManager != null) {
            if (!mBackgroundManager!!.isAttached) {
                mBackgroundManager!!.attach(mActivityWeakReference?.get()?.getWindow())
            }
            mBackgroundManager!!.drawable = drawable
        }
    }

    inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post {
                if (mBackgroundURI != null) {
                    updateBackground()
                }
            }
        }
    }


    // Cancels an ongoing background change
    fun cancelBackgroundChange() {
        mBackgroundURI = null
        cancelTimer()
    }


    // Stops the timer
    private fun cancelTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer?.cancel()
        }
    }


    // Starts the background change timer
    internal fun startBackgroundTimer() {
        cancelTimer()
        mBackgroundTimer = Timer()
        *//* set delay time to reduce too much background image loading process *//*
        mBackgroundTimer?.schedule(  UpdateBackgroundTask(),
            BACKGROUND_UPDATE_DELAY      )
    }


    // Updates the background with the last known URI
    fun updateBackground() {
        if (mActivityWeakReference?.get() != null) {
            Glide.with(mActivityWeakReference?.get())
                .load(mBackgroundURI)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mGlideDrawableSimpleTarget)
        }
    }*/
}