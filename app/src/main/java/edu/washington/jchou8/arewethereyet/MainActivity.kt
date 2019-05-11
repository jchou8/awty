package edu.washington.jchou8.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var started = false
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        btn_toggle.setOnClickListener{
            if (started) {
                started = false
                btn_toggle.text = getString(R.string.start)
                alarmManager.cancel(pendingIntent)
            } else {
                if (edt_message.text.isBlank()) {
                    Toast.makeText(applicationContext, "No message provided!", Toast.LENGTH_SHORT).show()
                } else if (edt_phone.text.isBlank()) {
                    Toast.makeText(applicationContext, "No phone number provided!", Toast.LENGTH_SHORT).show()
                } else {
                    val freq = edt_freq.text.toString().toIntOrNull()
                    if (freq === null || freq <= 0) {
                        Toast.makeText(applicationContext, "Invalid frequency provided!", Toast.LENGTH_SHORT).show()
                    } else {
                        val msFreq = (freq * 1000 * 60).toLong()
                        started = true
                        btn_toggle.text = getString(R.string.stop)

                        val intent = Intent(applicationContext, AlarmReceiver::class.java)
                        intent.putExtra("message", edt_message.text.toString())
                        intent.putExtra("phone", edt_phone.text.toString())
                        pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                        alarmManager.setRepeating(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(),
                            msFreq,
                            pendingIntent)
                    }
                }
            }
        }
    }
}
