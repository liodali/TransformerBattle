package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.BattleScoreBinding

class ScoreBattle (context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    )

    constructor(context: Context) : this(context, null, 0)

    private val binding:BattleScoreBinding
    init {
        val inflater:LayoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
         binding=BattleScoreBinding.inflate(inflater,this,true)
        binding.idScoreAutobotTextview.text="0"
        binding.idScoreAutobotTextview.text="0"

    }

    fun setAutobotsScore(score:Int){
        binding.idScoreAutobotTextview.text="$score"
    }

    fun setDecepticonsScore(score:Int){
        binding.idScoreDecepticonTextview.text="$score"
    }



}