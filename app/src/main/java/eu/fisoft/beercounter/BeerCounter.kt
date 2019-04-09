package eu.fisoft.beercounter

import android.content.Context
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.support.wearable.complications.ProviderUpdateRequester
import android.content.ComponentName



class BeerCounter : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_counter)

        // Enables Always-on
        setAmbientEnabled()
        setupListeners()
        this.counterValue += 1
    }

    private var counterValue: Int
        get() {
            val pref = getSharedPreferences("BEER_COUNTER_DATA", Context.MODE_PRIVATE)
            return pref.getInt("beers", 0)
        }
        set(value) {
            val newValue = Math.max(0, value)
            val pref = getSharedPreferences("BEER_COUNTER_DATA", Context.MODE_PRIVATE)
            pref.edit().putInt("beers", newValue).apply()
            render()
            updateComplications()
        }

    private fun updateComplications() {
        val componentName = ComponentName(applicationContext, BeerCountProvider::class.java!!)
        val providerUpdateRequester = ProviderUpdateRequester(applicationContext, componentName)
        providerUpdateRequester.requestUpdateAll()
    }

    private fun render() {
        val counterView = findViewById<TextView>(R.id.counter)
        counterView.text = "${counterValue}\uD83C\uDF7A"
    }

    private fun setupListeners() {
        val display = this.findViewById<View>(R.id.display)
        display.setOnClickListener { _ -> this.counterValue += 1 }

        val minusButton = this.findViewById<View>(R.id.minusButton)
        minusButton.setOnClickListener { _ -> this.counterValue -= 1 }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.repeatCount == 0 && keyCode == KeyEvent.KEYCODE_STEM_1) {
            this.counterValue += 1
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }


}
