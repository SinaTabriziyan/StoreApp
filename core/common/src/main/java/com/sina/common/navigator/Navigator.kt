package com.sina.common.navigator

import android.app.Activity
import com.sina.common.Activities

interface Navigator {
    fun navigate(activities: Activity)

    interface Provider {
        fun getActivities(activities: Activities): Navigator
    }
}