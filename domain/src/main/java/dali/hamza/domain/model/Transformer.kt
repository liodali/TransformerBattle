package dali.hamza.domain.model

import com.google.gson.annotations.SerializedName

enum class TeamTransformer(val v:String) {
    @SerializedName("A")

    AUTOBOTS("A"),
    @SerializedName("D")
    DECEPTICON("D"),
}
data class Transformer(
    val name: String,
    val team: TeamTransformer,
    val strength: Int,
    val intelligence: Int,
    val endurance: Int,
    val rank: Int,
    val courage: Int,
    val firePower: Int,
    val skill: Int,
)