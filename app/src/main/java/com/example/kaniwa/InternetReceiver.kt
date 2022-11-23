/*package com.example.kaniwa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class InternetReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val status = CheckInternet.getNetworkInfo(context)
        if (status.equals("Conectado"))
            Toast.makeText(context, "Estás conectado a internet", Toast.LENGTH_SHORT).show()
        else if(status.equals("Desconectado"))
            Toast.makeText(context, "Estas Desconectado :(", Toast.LENGTH_SHORT).show()
    }
}*/