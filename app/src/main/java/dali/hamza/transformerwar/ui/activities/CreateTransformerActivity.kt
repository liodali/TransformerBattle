package dali.hamza.transformerwar.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ActivityCreateTransformerBinding

class CreateTransformerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCreateTransformerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateTransformerBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
    fun cnacel(view: View){
        finish()
    }
}