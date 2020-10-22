package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.utilities.Utilities

class TransformerInformation(context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    )

    constructor(context: Context, transformer: Transformer) : this(context, null, 0) {
        this.transformer = transformer
    }

    private var transformer: Transformer? = null
    private var listNames: List<String> =
        context.resources.getStringArray(R.array.feature_name_transformer).toMutableList()
    private val listViewFeature = arrayListOf<TransformerFeature>().toMutableList()

    init {
        if (transformer == null) {
            transformer = Transformer("", TeamTransformer.AUTOBOTS, 0, 0, 0, 0, 0, 0, 0, 0)
        }
        orientation = VERTICAL
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        generateItems()

    }

    fun generateItems() {
        val values = listOf<Int>(
            this.transformer!!.strength,
            this.transformer!!.intelligence,
            this.transformer!!.speed,
            this.transformer!!.endurance,
            this.transformer!!.rank,
            this.transformer!!.courage,
            this.transformer!!.firePower,
            this.transformer!!.skill,
        )
        for (x in 0..6) {
            val drawable =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    context.getDrawable(Utilities.drawableIcons[x])
                } else {
                    context.resources.getDrawable(Utilities.drawableIcons[x])
                }
            val view = TransformerFeature(context, values[x], listNames[x], drawable!!)
            listViewFeature.add(
                view
            )
            addView(view)
        }
    }

    fun setTransformer(transformer: Transformer) {
        this.transformer = transformer
        val values = listOf<Int>(
            this.transformer!!.strength,
            this.transformer!!.intelligence,
            this.transformer!!.speed,
            this.transformer!!.endurance,
            this.transformer!!.rank,
            this.transformer!!.courage,
            this.transformer!!.firePower,
            this.transformer!!.skill,
        )
        for (x in 0..6) {
            listViewFeature[x].setValueFeature(value = values[x])
        }


    }
}