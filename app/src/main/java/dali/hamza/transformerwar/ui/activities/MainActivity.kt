package dali.hamza.transformerwar.ui.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
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
import dali.hamza.transformerwar.ui.dialog.DialogGameFragment
import dali.hamza.transformerwar.ui.dialog.ProgressDialog
import dali.hamza.transformerwar.ui.widgets.TransformerCount
import dali.hamza.transformerwar.utilities.Utilities
import dali.hamza.transformerwar.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity(), TransformerAdapter.TransformerAction {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyBinding: EmptyDataBinding
    private lateinit var loadingBinding: ChargementBinding
    private lateinit var autobotsCount: TransformerCount
    private lateinit var decepticonCount: TransformerCount
    private lateinit var selectorTeam: MaterialCardView
    private lateinit var startBattle: ExtendedFloatingActionButton
    private lateinit var adapter: TransformerAdapter
    private var battleFragment: DialogGameFragment? = null
    private var listTransformers: MutableList<Transformer> =
        emptyList<Transformer>().toMutableList()

    private val mainViewModel: MainViewModel by viewModels()

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setSupportActionBar(binding.idMainToolbar)
        title = resources.getString(R.string.transformers)

        //listener to select which type of transformer you want to create
        binding.idSelectorTransformerTeam.idSelectorAutobot.setOnClickListener {
            openCreateNewTranformer(TeamTransformer.AUTOBOTS.v)
        }

        binding.idSelectorTransformerTeam.idSelectorDecepticon.setOnClickListener {
            openCreateNewTranformer(TeamTransformer.DECEPTICON.v)
        }
        // observe retrieve list of transformers
        mainViewModel.getViewState().observe(this, { state ->
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
        // observe delete event
        mainViewModel.getEventDelete().observe(this, { event ->
            if (event != null) {
                dismissDialog(dialog!!)
                if (event is Success) {
                    val id = event.data
                    val index = listTransformers.indexOfFirst {
                        it.id == id
                    }
                    listTransformers.removeAt(index)
                    adapter.notifyDataSetChanged()
                    updateCountOfTransformers()
                    showSnackBar(
                        resources.getString(R.string.SuccessDeleteTransformer),
                        binding.root,
                    )
                    if (listTransformers.isEmpty()) {
                        hideLoading(true)
                    }
                } else {
                    showSnackBar(
                        resources.getString(R.string.impossibleToDeleteTransformer),
                        binding.root,
                    )
                }
            }
        })

        mainViewModel.retrieveListTransformers()
    }

    private fun listDataChanged(list: List<Transformer>) {
        if (list.isNotEmpty()) {
            listTransformers.clear()
            listTransformers.addAll(list)
            listTransformers.sortByDescending {
                it.rank
            }
            updateCountOfTransformers()
            adapter.notifyDataSetChanged()
        } else {
            hideLoading(true)

        }
    }

    private fun updateCountOfTransformers() {
        autobotsCount.setCount(
            listTransformers.filter {
                it.team == TeamTransformer.AUTOBOTS
            }.size
        )

        decepticonCount.setCount(
            listTransformers.filter {
                it.team == TeamTransformer.DECEPTICON
            }.size
        )
    }

    private fun initUI() {
        recyclerView = binding.idRecyclerViewTransformers
        startBattle = binding.floatingActionButton
        emptyBinding = binding.idMainEmptyData
        loadingBinding = binding.idMainLoadingLayout
        selectorTeam = binding.idSelectorTransformerTeam.idTransformerSelector
        autobotsCount = binding.idAutobotCount
        decepticonCount = binding.idDecepticonCount
        adapter = TransformerAdapter(listTransformers, this)
        recyclerView.adapter = adapter
        showLoading()

        loadingBinding.idChargementText.text =
            resources.getString(R.string.loading_list_transformer)
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
        if (selectorTeam.translationY < 0)
            selectorTeam.animate()
                .translationYBy(-150f)
                .translationY(0f).duration = 500//200ms
        else {
            selectorTeam.animate()
                .translationYBy(0f)
                .translationY(-250f).duration = 500
        }

    }

    private fun openCreateNewTranformer(team: String) {

        val intent = Intent(this, CreateTransformerActivity::class.java)
        intent.putExtra(Utilities.TEAM_TRANSFORMER, team)
        startActivityForResult(intent, Utilities.CreateTransformerRequestCode)
    }

    fun startBattle(view: View) {
        battleFragment = DialogGameFragment.newInstance(listTransformers)
        battleFragment!!.show(supportFragmentManager, "game")

    }

    override fun onStop() {
        super.onStop()
        if (battleFragment != null) {
            battleFragment!!.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Utilities.CreateTransformerRequestCode -> {

                if (resultCode == RESULT_OK) {
                    selectorTeam.animate()
                        .translationYBy(0f)
                        .translationY(-250f).duration = 500
                    showSnackBar(
                        resources.getString(R.string.SuccessAddedTransformer),
                        binding.root
                    )
                    val newTransformer =
                        data!!.extras!!.getParcelable<Transformer>(Utilities.TRANSFORMER)
                    if (newTransformer != null) {
                        listTransformers.add(newTransformer)
                        listTransformers.sortByDescending {
                            it.rank
                        }
                        updateCountOfTransformers()
                        adapter.notifyDataSetChanged()
                        if (listTransformers.size == 1) {
                            hideLoading(false)
                        }
                    }
                }
            }
            Utilities.ModifyTransformerRequestCode -> {
                if (resultCode == RESULT_OK) {
                    val transformer =
                        data!!.extras!!.getParcelable<Transformer>(Utilities.TRANSFORMER)
                    showSnackBar(
                        resources.getString(R.string.SuccessModifiedTransformer,transformer!!.name),
                        binding.root
                    )
                    if (transformer != null) {
                        val index = listTransformers.indexOfFirst {
                            it.id == transformer.id
                        }
                        listTransformers[index] = transformer
                        listTransformers.sortByDescending {
                            it.rank
                        }
                        updateCountOfTransformers()
                        adapter.notifyDataSetChanged()
                        if (listTransformers.size == 1) {
                            hideLoading(false)
                        }
                    }
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

    override fun editTransformer(transformer: Transformer) {
        val intent = Intent(this, CreateTransformerActivity::class.java)
        intent.putExtra(Utilities.TEAM_TRANSFORMER, transformer.team.v)
        intent.putExtra(Utilities.TRANSFORMER, transformer)
        startActivityForResult(intent, Utilities.ModifyTransformerRequestCode)
    }

    override fun deleteTransformer(id: String) {
        dialog = ProgressDialog.progressDialog(
            this,
            resources.getString(R.string.deleteTransformerMessage)
        )
        showDialog(dialog!!)
        mainViewModel.deleteTransformer(id)
    }

}