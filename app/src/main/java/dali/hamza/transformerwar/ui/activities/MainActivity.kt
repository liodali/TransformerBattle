package dali.hamza.transformerwar.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.databinding.ActivityMainBinding
import dali.hamza.transformerwar.ui.adapter.TransformerAdapter
import dali.hamza.transformerwar.utilities.Utilities

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var startBattle: ExtendedFloatingActionButton
    private lateinit var adapter: TransformerAdapter
    private var listTransformers: MutableList<Transformer> =
        emptyList<Transformer>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initList()


    }

    private fun initUI() {
        recyclerView = binding.idRecyclerViewTransformers
        startBattle = binding.floatingActionButton
        adapter = TransformerAdapter(listTransformers)
        recyclerView.adapter = adapter

    }

    private fun initList() {
        listTransformers.add(
            Transformer(
                id = "", "optimus prime", TeamTransformer.AUTOBOTS, 10, 10, 10, 10, 10, 10, 10,
                10,
            )
        )
        listTransformers.add(
            Transformer(
                id = "", "megatron", TeamTransformer.DECEPTICON, 9, 10, 10, 10, 10, 9, 10,
                10,
            )
        )
        adapter.notifyDataSetChanged()
    }
   fun createTransformer(view:View){
       val intent = Intent(this, CreateTransformerActivity::class.java)
       intent.putExtra(Utilities.TEAM_TRANSFORMER, TeamTransformer.AUTOBOTS.v)
       startActivityForResult(intent,Utilities.CreateTransformerResquestCode)
   }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Utilities.CreateTransformerResquestCode -> {
                if (resultCode == RESULT_OK) {

                }
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

    }

}