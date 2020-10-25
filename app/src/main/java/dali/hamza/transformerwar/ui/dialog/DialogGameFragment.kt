package dali.hamza.transformerwar.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.domain.model.Success
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.databinding.GameFragmentBinding
import dali.hamza.transformerwar.ui.activities.gone
import dali.hamza.transformerwar.ui.activities.visible
import dali.hamza.transformerwar.ui.widgets.ScoreBattle
import dali.hamza.transformerwar.ui.widgets.WinnerWidget
import dali.hamza.transformerwar.viewmodels.GameViewModel

@AndroidEntryPoint
class DialogGameFragment : DialogFragment() {

    private var KEY_PARAM: String = "listTransformers"
    private lateinit var binding: GameFragmentBinding


    private val gameViewModel by viewModels<GameViewModel>()

    private lateinit var score: ScoreBattle
    private lateinit var winnerWidget: WinnerWidget
    private lateinit var autobotPlayer: TextView
    private lateinit var decepticonPlayer: TextView
    private var transformers: List<Transformer> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            transformers = it.getParcelableArrayList<Transformer>(KEY_PARAM)!!.toList()
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(requireActivity())


        binding = GameFragmentBinding.inflate(requireActivity().layoutInflater)
        builder.setView(binding.root)
        score = binding.idScoreBattle
        autobotPlayer = binding.idPlayerAutobot
        decepticonPlayer = binding.idPlayerDecepticon
        winnerWidget = binding.idWinnerLayout
        // Create the AlertDialog object and return it
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        gameViewModel.getGame().observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                if (result is Success) {
                    val game = result.data
                    if (game.isFinished) {
                        isCancelable = true
                        winnerWidget.visible()
                        binding.idPlayerNameZone.gone()
                        winnerWidget.setResult(game.winner, game.isDraw)
                    } else {
                        autobotPlayer.text = game.currentBattle.autobot.name
                        decepticonPlayer.text = game.currentBattle.deception.name
                        if (game.previousBattles.isNotEmpty()) {
                            score.setAutobotsScore(game.previousBattles.filter {
                                it.winnerBattle == TeamTransformer.AUTOBOTS
                            }.size)
                            score.setDecepticonsScore(game.previousBattles.filter {
                                it.winnerBattle == TeamTransformer.DECEPTICON
                            }.size)
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.setListTransformers(transformers)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param idWallet Parameter 1.
         * @return A new instance of fragment OutlaysFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(transformers: List<Transformer>) =
            DialogGameFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_PARAM, ArrayList(transformers))
                }
            }
    }

}