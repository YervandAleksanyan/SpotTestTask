package com.yervand.spottesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yervand.feature.spots.requests.shared.SpotsRequestsFeatureCommunicator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var spotsRequestsFeatureCommunicator: SpotsRequestsFeatureCommunicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(spotsRequestsFeatureCommunicator.launchFeature(this))
    }

}