package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.WidgetTransformerCountBinding

class TransformerCount(context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    ) {
        initAttrs(context,attrs)
    }

    constructor(context: Context, count: Int, team: String) : this(context, null, 0) {
        this.count = count
        this.team = team

    }

    private var textViewCount: TextView
    private var icon: ImageView
    private var count: Int = 0

    private var team: String = "0"

    fun setCount(v:Int){
        count=v
        textViewCount.text="$count"
    }
    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TransformerCount)

        initTyped(attributes)

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = WidgetTransformerCountBinding.inflate(inflater, this, true)
        textViewCount = binding.idTxtTransformerCount
        icon = binding.idIconTransformerCount

        textViewCount.text="$count"

        var drawable = context.resources.getDrawable(
            R.drawable.ic_decepticon
        )
        var colorDrawable=R.color.purple_700
        if (team == "0") {
            drawable = context.resources.getDrawable(
                R.drawable.ic_autobot
            )
            colorDrawable=android.R.color.holo_red_light
        }
        icon.setImageDrawable(
            drawable
        )
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(context, colorDrawable))
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typed = context.obtainStyledAttributes(attrs, R.styleable.TransformerCount)
        initTyped(typed)
    }
    private fun initTyped(typed:TypedArray){
        try {
            count = typed.getInt(R.styleable.TransformerCount_count, 0)
            team = typed.getString(R.styleable.TransformerCount_team) ?: "A"

        } catch (e: Exception) {
            count = 0
            team = "A"
        }
    }

}