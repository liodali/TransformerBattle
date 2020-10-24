package dali.hamza.transformerwar.ui.activities

import android.content.Intent
import android.os.Bundle
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
    private lateinit var createTransformerButton: MaterialButton
    private lateinit var adapter: TransformerAdapter
    private var listTransformers: MutableList<Transformer> =
        emptyList<Transformer>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initList()


        createTransformerButton.setOnClickListener {
            val intent = Intent(this, CreateTransformerActivity::class.java)
            intent.putExtra(Utilities.TEAM_TRANSFORMER, TeamTransformer.AUTOBOTS.v)
            startActivity(intent)
        }
    }

    private fun initUI() {
        recyclerView = binding.idRecyclerViewTransformers
        startBattle = binding.floatingActionButton
        createTransformerButton = binding.idCreateTransformerBt
        adapter = TransformerAdapter(listTransformers)
        recyclerView.adapter = adapter

    }

    private fun initList() {
        listTransformers.add(
            Transformer(
                "optimus prime", TeamTransformer.AUTOBOTS, 10, 10, 10, 10, 10, 10, 10,
                10,
            )
        )
        listTransformers.add(
            Transformer(
                "megatron", TeamTransformer.DECEPTICON, 9, 10, 10, 10, 10, 9, 10,
                10,
            )
        )
        adapter.notifyDataSetChanged()
    }


}