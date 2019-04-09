package eu.fisoft.beercounter

import android.content.Context
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationManager
import android.support.wearable.complications.ComplicationProviderService
import android.support.wearable.complications.ComplicationText



class BeerCountProvider : ComplicationProviderService() {
    override fun onComplicationUpdate(complicationId: Int, type: Int, manager: ComplicationManager?) {
        if (type != ComplicationData.TYPE_SHORT_TEXT) {
            manager?.noUpdateRequired(complicationId)
            return
        }
        val data = ComplicationData.Builder(type)
                .setShortText(ComplicationText.plainText("${counterValue}\uD83C\uDF7A"))
                .build()

        manager?.updateComplicationData(complicationId, data);
    }


    private val counterValue: Int
        get() {
            val pref = getSharedPreferences("BEER_COUNTER_DATA", Context.MODE_PRIVATE)
            return pref.getInt("beers", 0)
        }

}