package dali.hamza.domain.model
enum class TeamTransformer {
    AUTOBOTS,DECEPTICON
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