package com.example.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val CHANNEL_NAME = "channel_name"
    private val CHANNELL_ID = "channel_id"
    private val NOTIFICATON_ID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChanel()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.winter)
        val notification = NotificationCompat.Builder(this, CHANNELL_ID)
            .setContentTitle("Notification Title")
            .setContentText("Notification Text")
            .setSmallIcon(R.drawable.ic_notifications)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setLargeIcon(iconBitmap)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(iconBitmap)
                    .bigLargeIcon(null)
            )
            .setAutoCancel(true)

        btnShowNotification.setOnClickListener {
            NotificationManagerCompat.from(this).notify(NOTIFICATON_ID, notification.build())
        }
    }

    fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNELL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
                description = "descriptionText"
            }

            val notificationManger: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(channel)
        }
    }
}