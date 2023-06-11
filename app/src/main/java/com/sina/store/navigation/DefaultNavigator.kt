package com.sina.store.navigation

import com.sina.common.Activities
import com.sina.common.navigator.Navigator
import com.sina.feature_main.GoToMainActivity

class DefaultNavigator : Navigator.Provider {
    override fun getActivities(activities: Activities): Navigator {
        return when (activities) {
            Activities.MainActivity -> GoToMainActivity
        }
    }
}