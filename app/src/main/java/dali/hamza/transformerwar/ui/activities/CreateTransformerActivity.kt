package dali.hamza.transformerwar.ui.activities

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.domain.model.Success
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ActivityCreateTransformerBinding
import dali.hamza.transformerwar.ui.dialog.ProgressDialog
import dali.hamza.transformerwar.ui.widgets.TransformerInputFeature
import dali.hamza.transformerwar.utilities.Utilities
import dali.hamza.transformerwar.viewmodels.CreateOrModifyViewModel

@AndroidEntryPoint
class CreateTransformerActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateTransformerBinding
    private lateinit var appBarLayout: AppBarLayout
    private var listTransformerInput: MutableList<TransformerInputFeature> =
        emptyList<TransformerInputFeature>().toMutableList()
    private lateinit var editInputLayout: TextInputLayout
    private lateinit var createOrModifyBt: Button

    private var teamTransformer: TeamTransformer = TeamTransformer.AUTOBOTS

    private val viewModelCreate: CreateOrModifyViewModel by viewModels()
    private var dialog: Dialog? = null
    private var transformer:Transformer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTransformerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.idToolbarCreateTransformer)

        transformer = intent.extras!!.getParcelable<Transformer>(Utilities.TRANSFORMER)

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
            resources.getString(R.string.create_autobot_title)
        } else {
            resources.getString(R.string.create_decepticon_title)
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

        editInputLayout.boxStrokeColor = resources.getColor(colorID)
        editInputLayout.hintTextColor = ColorStateList.valueOf(resources.getColor(colorID))
        editInputLayout.editText!!.doOnTextChanged { text, _, _, _ ->
            createOrModifyBt.isEnabled = !(text!!.isEmpty() || text.isBlank())
        }
        createOrModifyBt.background = if (teamTransformer == TeamTransformer.AUTOBOTS) {
            ResourcesCompat.getDrawable(resources,R.drawable.custom_autobot_button,null)
        } else {
            ResourcesCompat.getDrawable(resources,R.drawable.custom_button_decepticon,null)
        }
        listTransformerInput.addAll(
            arrayListOf(
                binding.idStrengthInformationInput,
                binding.idIntelligenceInformationInput,
                binding.idSpeedInformationInput,
                binding.idEnduranceInformationInput,
                binding.idRankInformationInput,
                binding.idCourageInformationInput,
                binding.idFirePowerInformationInput,
                binding.idSkillInformationInput,
            )
        )
        viewModelCreate.getResult().observe(this, Observer { result ->
            if (result != null) {
                if (dialog != null) {
                    dismissDialog(dialog)
                    dialog = null
                }
                if (result is Success<String>) {
                    if(transformer!=null){
                        transformer!!.id=result.data
                    }else{
                        createTransformerObject()
                        transformer!!.id=result.data
                    }
                    val intentResult =
                        Intent().putExtra(Utilities.TRANSFORMER, transformer)
                    setResult(RESULT_OK, intentResult)
                    finish()//Utilities.CreateTransformerResquestCode
                } else {

                    showSnackBar(getString(R.string.errorCreateTransformer), binding.root)
                }
            }
        })
    }
    private fun createTransformerObject(){
        transformer = Transformer(
            id = "",
            editInputLayout.editText!!.text.toString(),
            team = teamTransformer,
            strength = listTransformerInput[0].getValue(),
            intelligence = listTransformerInput[1].getValue(),
            speed = listTransformerInput[2].getValue(),
            endurance = listTransformerInput[3].getValue(),
            rank = listTransformerInput[4].getValue(),
            courage = listTransformerInput[5].getValue(),
            firePower = listTransformerInput[6].getValue(),
            skill = listTransformerInput[7].getValue(),
        )
    }
    fun createOrModify(view: View) {
        createTransformerObject()
        dialog = ProgressDialog.progressDialog(
            this,
            resources.getString(R.string.loading_create_transformer, transformer!!.name)
        )
        showDialog(dialog!!)
        viewModelCreate.create(transformer!!)
    }

    fun cancel(view: View) {
        finish()
    }
}