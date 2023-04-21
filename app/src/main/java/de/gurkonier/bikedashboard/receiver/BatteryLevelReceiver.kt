package de.gurkonier.bikedashboard.receiver

import android.content.*
import android.os.*

class BatteryLevelReceiver(
    private val listener: BatteryLevelListener
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        listener.onLevelUpdated(intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)?: 0)
    }

    interface BatteryLevelListener {
        fun onLevelUpdated(newLevel: Int)
    }
}