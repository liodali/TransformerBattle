package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import dali.hamza.transformerwar.databinding.GenericFeatureTransformerBinding

class TransformerFeature(context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    )

    constructor(context: Context, featureValue: Int, name: String, iconDrawable: Drawable) : this(
        context,
        null,
        0
    ) {
        this.featureValue = featureValue
        this.iconDrawable = iconDrawable
        this.nameText = name
    }

    private var featureValue: Int = 0
    private var nameText: String = "0"
    private lateinit var iconDrawable: Drawable
    private var iconFeature: ImageView
    private var valueFeature: ProgressBar
    private var nameFeature: TextView
    private var binding: GenericFeatureTransformerBinding

    init {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = GenericFeatureTransformerBinding.inflate(inflater, this, true)
        iconFeature = binding.idGenericIconFeatureTransformer
        valueFeature = binding.idGenericValueFeatureTransformer
        nameFeature = binding.idGenericNameFeatureTransformer
    }


    fun setNameFeature(name: String) {
        this.nameText = name
        nameFeature.text = this.nameText
    }

    fun setValueFeature(value: Int) {
        this.featureValue = value
        valueFeature.progress = this.featureValue
    }

    fun setIconDrawable(iconDrawable: Drawable) {
        this.iconDrawable = iconDrawable
        iconFeature.setImageDrawable(this.iconDrawable)
    }

}