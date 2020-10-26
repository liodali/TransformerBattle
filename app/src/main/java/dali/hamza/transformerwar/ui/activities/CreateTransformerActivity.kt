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
    private var transformer: Transformer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTransformerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.idToolbarCreateTransformer)

        transformer = intent.extras!!.getParcelable(Utilities.TRANSFORMER)

        teamTransformer =
            if (intent.extras!!.getString(Utilities.TEAM_TRANSFORMER) == TeamTransformer.AUTOBOTS.v)
                TeamTransformer.AUTOBOTS
            else {
                TeamTransformer.DECEPTICON
            }
        //initialze ui element
        appBarLayout = binding.appBarLayout
        editInputLayout = binding.idNameTransformerTextInputEditText
        createOrModifyBt = binding.idCreateOrModifyTransformer

        //change color of appbar and statusBar
        title = if (transformer == null) {
            if (teamTransformer == TeamTransformer.AUTOBOTS) {
                resources.getString(R.string.create_autobot_title)
            } else {
                resources.getString(R.string.create_decepticon_title)
            }
        } else {
            resources.getString(R.string.modifyTransformerTitle, transformer!!.name)
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
            ResourcesCompat.getDrawable(resources, R.drawable.custom_autobot_button, null)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.custom_button_decepticon, null)
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
        // check we are in modification or create new transformer
        // if transformer is not null,we are in modification of transformer
        // we enable button to change team of transformer
        // assign values of transformer information in each element
        if (transformer != null) {
            createOrModifyBt.text = resources.getText(R.string.modifyLabel)
            updateTransformerObject()
            binding.idChangeTeam.visible()
            val messageHelperBt = if (transformer!!.team == TeamTransformer.AUTOBOTS) {
                resources.getString(R.string.decepticon_label)
            } else {
                resources.getString(R.string.autobots_label)
            }

            binding.idChangeTeam.text =
                resources.getString(R.string.change_team_bt_label, messageHelperBt)
            binding.idChangeTeam.background = if (teamTransformer == TeamTransformer.AUTOBOTS) {
                ResourcesCompat.getDrawable(resources, R.drawable.custom_button_decepticon, null)

            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.custom_autobot_button, null)

            }
        }



        viewModelCreate.getResult().observe(this, { result ->
            if (result != null) {
                if (dialog != null) {
                    dismissDialog(dialog)
                    dialog = null
                }
                if (result is Success<Transformer>) {

                    val intentResult =
                        Intent().putExtra(Utilities.TRANSFORMER, result.data)
                    setResult(RESULT_OK, intentResult)
                    finish()

                } else {
                    if (transformer==null || transformer!!.id.isEmpty()) {
                        showSnackBar(getString(R.string.errorCreateTransformer), binding.root)
                    } else {
                        showSnackBar(getString(R.string.errorModificationTransformer), binding.root)
                    }
                }
            }
        })

    }

    private fun createTransformerObject() {
        transformer = Transformer(
            id = transformer?.id ?: "",
            name = editInputLayout.editText!!.text.toString(),
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

    private fun updateTransformerObject() {
        editInputLayout.editText!!.setText(transformer!!.name)
        listTransformerInput[0].setProgressValue(transformer!!.strength)
        listTransformerInput[1].setProgressValue(transformer!!.intelligence)
        listTransformerInput[2].setProgressValue(transformer!!.speed)
        listTransformerInput[3].setProgressValue(transformer!!.endurance)
        listTransformerInput[4].setProgressValue(transformer!!.rank)
        listTransformerInput[5].setProgressValue(transformer!!.courage)
        listTransformerInput[6].setProgressValue(transformer!!.firePower)
        listTransformerInput[7].setProgressValue(transformer!!.skill)
    }

    fun createOrModify(view: View) {
        createTransformerObject()
        val messageDialog = if (transformer == null) {
            resources.getString(R.string.loading_create_transformer, transformer!!.name)
        } else {
            resources.getString(R.string.loading_modify_transformer, transformer!!.name)

        }
        dialog = ProgressDialog.progressDialog(
            this,
            messageDialog
        )
        showDialog(dialog!!)
        if (transformer!!.id.isEmpty())
            viewModelCreate.create(transformer!!)
        else {
            viewModelCreate.modify(transformer!!)
        }
    }

    private fun manageThemeBarAndButtons(team: TeamTransformer) {
        val colorID = if (team == TeamTransformer.AUTOBOTS) {
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


        createOrModifyBt.background = if (team == TeamTransformer.AUTOBOTS) {
            ResourcesCompat.getDrawable(resources, R.drawable.custom_autobot_button, null)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.custom_button_decepticon, null)
        }
        binding.idChangeTeam.background = if (team == TeamTransformer.AUTOBOTS) {
            ResourcesCompat.getDrawable(resources, R.drawable.custom_button_decepticon, null)

        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.custom_autobot_button, null)

        }
    }

    fun changeTeamOfCurrentTransformer(view: View) {
        if (transformer != null) {
            var helperLabel = resources.getString(R.string.decepticon_label)
            if (teamTransformer == TeamTransformer.DECEPTICON) {
                teamTransformer = TeamTransformer.AUTOBOTS
            } else {
                teamTransformer = TeamTransformer.DECEPTICON
                helperLabel = resources.getString(R.string.autobots_label)

            }
            binding.idChangeTeam.text =
                resources.getString(R.string.change_team_bt_label, helperLabel)
            manageThemeBarAndButtons(teamTransformer)
        }
    }

    fun cancel(view: View) {
        finish()
    }
}