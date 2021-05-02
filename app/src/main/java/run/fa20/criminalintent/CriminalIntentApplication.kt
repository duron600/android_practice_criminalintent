package run.fa20.criminalintent

import android.app.Application
import android.util.Log

class CriminalIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
        Log.i(APP_TAG, "APPLICATION on Create")
    }
}