package dali.hamza.transformerwar.utilities

import dali.hamza.transformerwar.R

class Utilities {
    companion object {
        const val CreateTransformerRequestCode: Int=101
        const val ModifyTransformerRequestCode: Int=102
        const val TEAM_TRANSFORMER: String = "TEAM_TRANSFORMER"
        const val TRANSFORMER: String = "TRANSFORMER"
        const val BASE_URL = "https://transformers-api.firebaseapp.com"
        val drawableIcons = listOf<Int>(
            R.drawable.ic_axe,
            R.drawable.ic_intelligence,
            R.drawable.ic_speed,
            R.drawable.ic_endurance,
            R.drawable.ic_rank,
            R.drawable.ic_courage,
            R.drawable.ic_power_fire,
            R.drawable.ic_skill,
        )
    }
}