package dali.hamza.transformerwar.ui.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputLayout
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ActivityCreateTransformerBinding
import dali.hamza.transformerwar.ui.widgets.TransformerInputFeature
import dali.hamza.transformerwar.utilities.Utilities

class CreateTransformerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTransformerBinding
    private lateinit var appBarLayout: AppBarLayout
    private var listTransformerInput: MutableList<TransformerInputFeature> =
        emptyList<TransformerInputFeature>().toMutableList()
    private lateinit var editInputLayout: TextInputLayout
    private lateinit var createOrModifyBt: Button

    private var idTransformer: String? = null
    private var teamTransformer: TeamTransformer = TeamTransformer.AUTOBOTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTransformerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.idToolbarCreateTransformer)

        idTransformer = intent.extras!!.getString(Utilities.ID_TRANSFORMER)

        teamTransformer =
            if (intent.extras!!.getString(Utilities.TEAM_TRANSFORMER) == TeamTransformer.AUTOBOTS.v)
                TeamTransformer.AUTOBOTS
            else {
                TeamTransformer.DECEPTICON
            }

        appBarLayout = binding.appBarLayout
        editInputLayout = binding.idNameTransformerTextInputEditText
        createOrModifyBt = binding.idCreateOrModifyTransformer

        title = if (teamTransformer == TeamTransformer.AUTOBOTS) {
            "Create new Autobot"
        } else {
            "Create new Decepticon"
        }

        val colorID = if (teamTransformer == TeamTransformer.AUTOBOTS) {
            android.R.color.holo_red_light
        } else {
            R.color.purple_500
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(colorID)
        }
        appBarLayout.apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                setStatusBarForegroundColor(getColor(colorID))
                setBackgroundColor(getColor(colorID))
            } else {
                setBackgroundColor(resources.getColor(colorID))
                setStatusBarForegroundColor(resources.getColor(colorID))

            }
        }
        editInputLayout.editText!!.doOnTextChanged { text, _, _, _ ->
            createOrModifyBt.isEnabled = !(text!!.isEmpty() || text!!.isBlank())
        }
        createOrModifyBt.background = if (teamTransformer == TeamTransformer.AUTOBOTS) {
            resources.getDrawable(R.drawable.custom_autobot_button)
        } else {
            resources.getDrawable(R.drawable.custom_button_decepticon)

        }
    }

    fun createOrModify(view: View) {

    }

    fun cancel(view: View) {
        finish()
    }
}