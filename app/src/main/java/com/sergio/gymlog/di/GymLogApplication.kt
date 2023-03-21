package com.sergio.gymlog.di

import android.app.Application
import com.sergio.gymlog.data.authentication.FirebaseAuthenticationRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GymLogApplication() : Application()