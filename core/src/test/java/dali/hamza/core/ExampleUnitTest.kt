package dali.hamza.core

import dali.hamza.domain.model.*
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val listTransformers = arrayListOf<Transformer>(
        Transformer(
            name = "dumblebee",
            strength = 8,
            intelligence = 4,
            speed = 7,
            endurance = 6,
            rank = 5,
            courage = 7,
            firePower = 6,
            skill = 6,
            team = TeamTransformer.AUTOBOTS
        ),
        Transformer(
            name = "Starscream",
            strength = 5,
            intelligence = 4,
            speed = 7,
            endurance = 6,
            rank = 5,
            courage = 3,
            firePower = 6,
            skill = 6,
            team = TeamTransformer.DECEPTICON
        )
    ).toMutableList()

    @Test
    fun gameSimpleBattleTransformers() {


        val decepticons = listTransformers.filter {
            it.team == TeamTransformer.DECEPTICON
        }.toMutableList()
        decepticons.sortBy {
            it.rank
        }

        val autobots = listTransformers.filter {
            it.team == TeamTransformer.AUTOBOTS
        }.toMutableList()
        autobots.sortBy {
            it.rank
        }
        val lenBattle = if (decepticons.size > autobots.size) autobots.size else decepticons.size

        var indexBattle = 0
        val gameBattle = GameResult(
            currentBattle = Battle(
                autobot = autobots[indexBattle],
                deception = decepticons[indexBattle],
            ),
        )

        do {
            if (decepticons[indexBattle].name.toLowerCase()
                    .trim() == "optimusprime" || decepticons[indexBattle].name.toLowerCase()
                    .trim() == "predaking"
            ) {
                gameBattle.currentBattle.winnerBattle = TeamTransformer.DECEPTICON
            } else if (autobots[indexBattle].name.toLowerCase()
                    .trim() == "optimusprime" || decepticons[indexBattle].name.toLowerCase()
                    .trim() == "predaking"
            ) {
                gameBattle.currentBattle.winnerBattle = TeamTransformer.AUTOBOTS
            } else {
                val diffCourage = decepticons[indexBattle].courage - autobots[indexBattle].courage
                val diffStrength =
                    decepticons[indexBattle].strength - autobots[indexBattle].strength
                val diffSkill = decepticons[indexBattle].skill - autobots[indexBattle].skill
                if (diffCourage >= 4 && diffStrength >= 3) {
                    gameBattle.currentBattle.winnerBattle = TeamTransformer.DECEPTICON

                } else if (diffCourage <= -4 && diffStrength <= -3) {
                    gameBattle.currentBattle.winnerBattle = TeamTransformer.AUTOBOTS
                } else {
                    if (diffSkill >= 3) {
                        gameBattle.currentBattle.winnerBattle = TeamTransformer.DECEPTICON

                    } else if (diffSkill <= -3) {
                        gameBattle.currentBattle.winnerBattle = TeamTransformer.AUTOBOTS
                    } else {
                        if (decepticons[indexBattle].ratingRank() > autobots[indexBattle].ratingRank()) {
                            gameBattle.currentBattle.winnerBattle = TeamTransformer.DECEPTICON
                        } else if (decepticons[indexBattle].ratingRank() < autobots[indexBattle].ratingRank()) {
                            gameBattle.currentBattle.winnerBattle = TeamTransformer.AUTOBOTS
                        } else {
                            gameBattle.currentBattle.isDestroyed = true
                        }
                    }
                }

            }
            gameBattle.previousBattles.add(gameBattle.currentBattle)
            indexBattle++
            if (indexBattle < lenBattle) {
                gameBattle.previousBattles.add(gameBattle.currentBattle)
                gameBattle.currentBattle = Battle(
                    autobot = autobots[indexBattle],
                    deception = decepticons[indexBattle],
                )
                val countDecepticonWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.DECEPTICON
                }.count()

                val countAutobotsWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.AUTOBOTS
                }.count()
                if (countAutobotsWins > lenBattle / 2) {
                    gameBattle.isFinished = true
                    gameBattle.winner = TeamTransformer.AUTOBOTS

                } else {
                    if (countDecepticonWins > lenBattle / 2) {
                        gameBattle.isFinished = true
                        gameBattle.winner = TeamTransformer.DECEPTICON

                    }
                }
                if (gameBattle.isFinished) {
                    if (gameBattle.isFinished) {
                        for (x in indexBattle..lenBattle) {
                            gameBattle.survivors.add(decepticons[x])
                            gameBattle.survivors.add(autobots[x])
                        }
                    }
                    break
                }

            } else {
                gameBattle.isFinished = true
                val countDecepticonWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.DECEPTICON
                }.count()

                val countAutobotsWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.AUTOBOTS
                }.count()
                if (countAutobotsWins > countDecepticonWins) {
                    gameBattle.winner = TeamTransformer.AUTOBOTS
                } else if (countDecepticonWins > countAutobotsWins) {
                    gameBattle.winner = TeamTransformer.DECEPTICON

                }

            }
        } while (indexBattle < lenBattle)
        Assert.assertEquals(TeamTransformer.AUTOBOTS, gameBattle.winner)
    }
}