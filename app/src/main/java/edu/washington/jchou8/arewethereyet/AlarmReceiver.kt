package edu.washington.jchou8.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val phone = intent.getStringExtra("phone")

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.alarm_toast, null)
        layout.findViewById<TextView>(R.id.txt_caption).text = "Texting %s".format(phone)
        layout.findViewById<TextView>(R.id.txt_body).text = message
        with (Toast(context)) {
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}
