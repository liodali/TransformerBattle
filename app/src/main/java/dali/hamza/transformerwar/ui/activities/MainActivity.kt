package dali.hamza.transformerwar.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.domain.model.Success
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.model.TransformerList
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ActivityMainBinding
import dali.hamza.transformerwar.databinding.ChargementBinding
import dali.hamza.transformerwar.databinding.EmptyDataBinding
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.ui.adapter.TransformerAdapter
import dali.hamza.transformerwar.utilities.Utilities
import dali.hamza.transformerwar.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyBinding: EmptyDataBinding
    private lateinit var loadingBinding: ChargementBinding
    private lateinit var startBattle: ExtendedFloatingActionButton
    private lateinit var adapter: TransformerAdapter
    private var listTransformers: MutableList<Transformer> =
        emptyList<Transformer>().toMutableList()

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

        mainViewModel.getViewState().observe(this, Observer { state ->
            if (state != null) {
                if (state.state == State.LOADING) {
                    showLoading()
                } else {
                    if (state.state == State.DATA) {
                        hideLoading(false)
                        if (state.data is Success<Any>) {
                            listDataChanged(((state.data as Success<*>).data as TransformerList).transformers)
                        } else {
                            hideLoading(true)
                        }
                    } else {
                        hideLoading(true)
                    }
                }
            }
        })

        mainViewModel.retrieveListTransformers()
    }

    fun listDataChanged(list: List<Transformer>) {
        if (list.isNotEmpty()) {
            listTransformers.clear()
            listTransformers.addAll(list)
            adapter.notifyDataSetChanged()
        } else {
            hideLoading(true)

        }
    }

    private fun initUI() {
        recyclerView = binding.idRecyclerViewTransformers
        startBattle = binding.floatingActionButton
        emptyBinding = binding.idMainEmptyData
        loadingBinding = binding.idMainLoadingLayout
        adapter = TransformerAdapter(listTransformers)
        recyclerView.adapter = adapter

        showLoading()
        emptyBinding.idTextEmpty.text = resources.getString(R.string.emptyTransformerList)
    }

    private fun showLoading() {
        super.showLoading(loadingBinding.root)
        recyclerView.gone()
        emptyBinding.root.gone()
        startBattle.gone()
        binding.idCreateTransformerBt.disabled()
    }

    private fun hideLoading(
        error: Boolean,
    ) {
        super.hideLoading(loadingBinding.root)
        binding.idCreateTransformerBt.enabled()
        if (error) {
            recyclerView.gone()
            emptyBinding.root.visible()
            startBattle.gone()
        } else {
            recyclerView.visible()
            emptyBinding.root.gone()
            startBattle.visible()
        }

    }

    fun createTransformer(view: View) {
        val intent = Intent(this, CreateTransformerActivity::class.java)
        intent.putExtra(Utilities.TEAM_TRANSFORMER, TeamTransformer.AUTOBOTS.v)
        startActivityForResult(intent, Utilities.CreateTransformerResquestCode)
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