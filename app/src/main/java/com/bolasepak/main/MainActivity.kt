package com.bolasepak.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.bolasepak.event.EventFragment
import com.bolasepak.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var content: FrameLayout? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_previous -> {
                val fragment = EventFragment.newInstance("eventspastleague")
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                val fragment = EventFragment.newInstance("eventsnextleague")
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private fun addFragment(fragment: EventFragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = EventFragment.newInstance("eventspastleague")

        addFragment(fragment)
    }
}
