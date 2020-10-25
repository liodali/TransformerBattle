package dali.hamza.domain.model

import com.google.gson.annotations.SerializedName

enum class TeamTransformer(val v: String) {
    @SerializedName("A")

    AUTOBOTS("A"),

    @SerializedName("D")
    DECEPTICON("D"),
}

data class TransformerList(
    @SerializedName("transformers")
    val transformers: List<Transformer>
)

fun Transformer.ratingRank(): Int {
    return this.strength + this.intelligence + this.speed + this.endurance + this.rank + this.courage + this.firePower + this.skill
}

data class Transformer(
    val id: String = "",
    val name: String,
    val team: TeamTransformer,
    val strength: Int,
    val intelligence: Int,
    val speed: Int,
    val endurance: Int,
    val rank: Int,
    val courage: Int,
    @SerializedName("firepower")
    val firePower: Int,
    val skill: Int,
)

data class GameResult(
    var winner: TeamTransformer?=null,
    var isFinished: Boolean = false,
    var currentBattle: Battle,
    var previousBattles: MutableList<Battle> = emptyList<Battle>().toMutableList(),
    var survivors: MutableList<Transformer> = emptyList<Transformer>().toMutableList(),
)

data class Battle(
    val autobot: Transformer,
    val deception: Transformer,
    var winnerBattle: TeamTransformer?=null,
    var isDestroyed:Boolean=false,
)