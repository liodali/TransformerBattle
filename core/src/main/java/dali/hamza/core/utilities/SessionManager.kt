package dali.hamza.core.utilities

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext application: Application) {

    var preference:SharedPreferences=application.getSharedPreferences(namePreference,MODE_PRIVATE)


    fun getValue(key: String):Any?{
        return preference.all[key]
    }

    fun setValue(key:String,value:Any){
        preference.edit().run {
            when(value){
                is String->this.putString(key,value)
                is Int -> this.putInt(key,value)
            }
            this.commit()
        }
    }


    fun clearData(){
        preference.edit().run {
            this.clear()
            this.commit()
        }
    }

    fun registerSession(listener:SharedPreferences.OnSharedPreferenceChangeListener){
        preference.registerOnSharedPreferenceChangeListener(listener)
    }
    fun unregisterSession(listener:SharedPreferences.OnSharedPreferenceChangeListener){
        preference.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object keys{
        const val namePreference="TransformerPref"
        const val tokenKey="token"
        const val bearer="Bearer "
    }
}