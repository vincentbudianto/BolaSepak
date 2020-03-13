package com.bolasepak.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bolasepak.event.EventFragment
import com.bolasepak.listener.StepListener
import com.bolasepak.util.StepDetector
import com.bolasepak.R
import com.bolasepak.subscribed.SubscribedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener, StepListener {
    private var content: FrameLayout? = null
    private var stepDetector: StepDetector? = null
    private var steps: Int = 0
    var sensorManager: SensorManager? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_previous -> {
                val fragment = EventFragment.newInstance("eventspastleague", applicationContext)
                addEventFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                val fragment = SubscribedFragment.newInstance("eventsnextleague")
                addSubscribedFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private fun addEventFragment(fragment: EventFragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    private fun addSubscribedFragment(fragment: SubscribedFragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetector = StepDetector()
        stepDetector!!.registerListener(this)

        val fragment = EventFragment.newInstance("eventspastleague", applicationContext)
        addEventFragment(fragment)
    }

    override fun onResume() {
        super.onResume()

        var accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            Toast.makeText(this, "Step Counter Not Supported", Toast.LENGTH_SHORT).show()
            stepCounter.visibility = View.INVISIBLE
            stepComment.visibility = View.INVISIBLE
        } else {
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            stepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }

    override fun step(timeNs: Long) {
        steps++
        stepCounter.text = steps.toString()
    }
}
