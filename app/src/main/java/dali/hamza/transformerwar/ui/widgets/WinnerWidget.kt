package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.WinnerLayoutBinding
import dali.hamza.transformerwar.ui.activities.gone

class WinnerWidget(context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    )

    constructor(context: Context) : this(context, null, 0)

    private val binding: WinnerLayoutBinding

    private val teamWinner: ImageView
    private val trophe: ImageView
    private val messageTextView: TextView


    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WinnerLayoutBinding.inflate(inflater, this, true)
        teamWinner = binding.idWinnerTeam
        trophe = binding.idStatutWinner
        messageTextView = binding.idWinnerMessageTxt
    }

    fun setResult(team: TeamTransformer?, isDraw: Boolean = false) {
        when {
            team != null && team == TeamTransformer.AUTOBOTS -> {

                winner(R.drawable.ic_autobot,android.R.color.holo_red_light,resources.getString(R.string.winnerMessage,"Autobots"))
            }
            team != null && team == TeamTransformer.DECEPTICON -> {
                winner(R.drawable.ic_decepticon,R.color.purple_500,resources.getString(R.string.winnerMessage,"Decepticon"))
            }
            else -> {
                teamWinner.gone()
                trophe.setImageDrawable(resources.getDrawable(R.drawable.ic_destroyed))
                messageTextView.text=resources.getString(R.string.cybertronDestroyed)
            }
        }
    }
    private fun winner(drawable : Int,color :Int,message:String){
        teamWinner.setImageDrawable(resources.getDrawable(drawable))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            teamWinner.imageTintList =
                ColorStateList.valueOf(resources.getColor(color))
        }
        messageTextView.text=message

    }
}