package com.bolasepak.main

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
import com.bolasepak.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var content: FrameLayout? = null
    var sensorManager: SensorManager? = null
    var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private fun addFragment(fragment: EventFragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = EventFragment.newInstance("eventspastleague")
        addFragment(fragment)
    }

    override fun onResume() {
        super.onResume()

        if (stepsSensor == null) {
            Toast.makeText(this, "Step Counter Not Supported", Toast.LENGTH_SHORT).show()
            stepCounter.visibility = View.INVISIBLE
            stepComment.visibility = View.INVISIBLE
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (stepsSensor != null) {
            stepCounter.text = "" + event.values[0]
        }
    }
}
