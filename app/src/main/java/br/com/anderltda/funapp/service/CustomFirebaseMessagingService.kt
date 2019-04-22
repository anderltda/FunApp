package br.com.anderltda.funapp.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.text.Html
import android.util.Log
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.domain.NotificationEvent
import br.com.anderltda.funapp.util.NotificationManagerUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus


class CustomFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //super.onMessageReceived(remoteMessage);
        Log.i("log", "From ---> " + remoteMessage!!.from!!)
        Log.i("log", "MessageId ---> " + remoteMessage.messageId!!)

       // Log.i("log", "To ---> " + remoteMessage.to != null ? remoteMessage.to : "")
        Log.i("log", "SentTime ---> " + remoteMessage.sentTime)
        Log.i("log", "Ttl ---> " + remoteMessage.ttl)
        Log.i("log", "CollapseKey ---> " + remoteMessage.collapseKey!!)
//        Log.i("log", "MessageType ---> " + remoteMessage.messageType!!)
        Log.i("log", "Data ---> " + remoteMessage.data)
        Log.i("log", "Notification : Body ---> " + remoteMessage.notification!!.body!!)

        val intent = getIntentBody(remoteMessage)
        val notificationEvent = NotificationEvent(intent)
        EventBus.getDefault().post(notificationEvent)
        showNotification(remoteMessage, getLastScreen())
    }

    private fun getIntentBody(remoteMessage: RemoteMessage): Intent {
        val intent = Intent()
        val extras = Bundle()

        for (key in remoteMessage.data.keys) {
            extras.putString(key, remoteMessage.data[key])
        }
        intent.putExtras(extras)
        return intent
    }

    private fun getLastScreen(): PendingIntent {
        val nIntent = packageManager.getLaunchIntentForPackage(packageName)
        return PendingIntent.getActivity(this, 0, nIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun showNotification(remoteMessage: RemoteMessage, pendingIntent: PendingIntent?) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val title = remoteMessage.notification?.title ?: "Nova mensagem"
        val message = remoteMessage.notification?.body ?: ""

        val mBuilder =
            createNotificationCompatBuilder(applicationContext)
                .setContentTitle(Html.fromHtml(title))
                .setSound(defaultSoundUri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(Html.fromHtml(message))

        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(notificationID, mBuilder.build())
    }

    private fun createNotificationCompatBuilder(context: Context): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, NotificationManagerUtil(context).getMainNotificationId())
        } else {
            NotificationCompat.Builder(context)
        }
    }

    companion object {
        private const val notificationID = 1000
    }
}
