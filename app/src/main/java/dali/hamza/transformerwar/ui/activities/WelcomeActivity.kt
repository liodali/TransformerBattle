package dali.hamza.transformerwar.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.domain.model.Success
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.viewmodels.WelcomeViewModel

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        welcomeViewModel.stateView().observe(this, Observer { state->
            if(state!=null){
                if(state.state==State.DATA){
                    if(state.data is Success<*>){
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        welcomeViewModel.checkToken()
    }
}