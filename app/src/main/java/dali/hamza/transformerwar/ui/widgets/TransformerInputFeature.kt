package dali.hamza.transformerwar.ui.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.*
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ItemInputFeatureTransformerBinding

class TransformerInputFeature(context: Context, attrs: AttributeSet?, defStyle: Int?) :
    LinearLayout(context, attrs, defStyle!!) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        0
    ) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TransformerInputFeature)

        initTyped(attributes)
    }

    constructor(context: Context) : this(context, null, 0)

    private var value: Int = 1

    private var name: String = ""
    private var drawable: Drawable? = null
    private val minusValueBt: ImageButton
    private val addValueBt: ImageButton
    private val textValue: TextView
    private val progressBarValue: ProgressBar
    private val icon: ImageView
    private val nameFeature: TextView

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TransformerInputFeature)

        initTyped(attributes)


        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemInputFeatureTransformerBinding.inflate(inflater, this, true)
        minusValueBt = binding.idMinusValueFeature
        addValueBt = binding.idAddValueFeature
        icon = binding.idIconInputTransformerFeature
        nameFeature = binding.idNameInputTransformerFeature
        textValue = binding.idTxtValueFeatureInput
        progressBarValue = binding.idProgressBarValueInputTransformerFeature
        progressBarValue.progress = value

        nameFeature.text = name
        textValue.text = "${value * 10}%"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon.setImageDrawable(drawable!!)
        } else {
            icon.setImageDrawable(drawable!!)

        }
        Log.e("add click",""+addValueBt.isClickable)
        addValueBt.setOnClickListener {
            addValue()
            textValue.text = "${value * 10}%"
        }
        minusValueBt.setOnClickListener {
            minus()

            textValue.text = "${value * 10}%"
        }
    }


    fun getValue() :Int{
        return progressBarValue.progress
    }
    fun setProgressValue(value: Int) {
        this.value = value
        progressBarValue.progress = this.value
        textValue.text = "${value * 10}%"
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typed = context.obtainStyledAttributes(attrs, R.styleable.TransformerCount)
        initTyped(typed)
    }

    private fun initTyped(typed: TypedArray) {
        try {
            value = typed.getInt(R.styleable.TransformerInputFeature_value, 0)
            name = typed.getString(R.styleable.TransformerInputFeature_name) ?: ""
            drawable =
                typed.getDrawable(R.styleable.TransformerInputFeature_iconDrawable)

        } catch (e: Exception) {
            value = 1
            name = ""
            drawable = context.resources.getDrawable(R.drawable.ic_axe)
        }

    }

    private fun addValue() {
        if (value < 10) {
            value += 1
            progressBarValue.progress = value
        }
        invalidate()
        requestLayout()
    }

    private fun minus() {
        if (value > 1) {
            value -= 1
            progressBarValue.progress = value
        }
        invalidate()
        requestLayout()
    }
}