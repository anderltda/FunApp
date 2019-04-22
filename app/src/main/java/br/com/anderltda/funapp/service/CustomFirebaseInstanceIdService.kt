package br.com.anderltda.funapp.service

import android.util.Log
import br.com.anderltda.funapp.domain.TokenEvent
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.greenrobot.eventbus.EventBus

class CustomFirebaseInstanceIdService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token

        val tokenEvent = token?.let { TokenEvent(it) }
        EventBus.getDefault().post(tokenEvent)
        Log.i("Token", token)
    }
}