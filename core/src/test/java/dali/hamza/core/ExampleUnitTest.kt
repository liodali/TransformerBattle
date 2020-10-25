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
    private val listTransformers = arrayListOf<Transformer>(
        Transformer(
            name = "optimus prime",
            strength = 10,
            intelligence = 10,
            speed = 7,
            endurance = 6,
            rank = 10,
            courage = 10,
            firePower = 10,
            skill = 6,
            team = TeamTransformer.AUTOBOTS
        ), Transformer(
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


        ///prepation team
        /// separate and sort autobot and decepticon  by rank

        val decepticons = listTransformers.filter {
            it.team == TeamTransformer.DECEPTICON
        }.toMutableList()
        decepticons.sortByDescending {
            it.rank
        }

        val autobots = listTransformers.filter {
            it.team == TeamTransformer.AUTOBOTS
        }.toMutableList()
        autobots.sortByDescending {
            it.rank
        }
        val lenBattle = if (decepticons.size > autobots.size) autobots.size else decepticons.size

        var indexBattle = 0
        // init battle game
        val gameBattle = GameResult(
            currentBattle = Battle(
                autobot = autobots[indexBattle],
                deception = decepticons[indexBattle],
            ),
        )

        do {
            ///check if figthers have name optimusprime and predaking to finish game
            if ((decepticons[indexBattle].name.toLowerCase()
                    .trim() == "optimusprime" || decepticons[indexBattle].name.toLowerCase()
                    .trim() == "predaking") && (autobots[indexBattle].name.toLowerCase()
                    .trim() == "optimusprime" || autobots[indexBattle].name.toLowerCase()
                    .trim() == "predaking")
            ) {
                gameBattle.isFinished
                break
            }
            /// apply special rules 1:  check if the transformers have the name of optimus prime or predaking
            /// to win automatically
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
                ///apply simple rules
                val diffCourage = decepticons[indexBattle].courage - autobots[indexBattle].courage
                val diffStrength =
                    decepticons[indexBattle].strength - autobots[indexBattle].strength
                val diffSkill = decepticons[indexBattle].skill - autobots[indexBattle].skill
                /// check difference of courage and strength,skill between fighters
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
                        ///check rating rank between fighters
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
            ///add current game to previous
            gameBattle.previousBattles.add(gameBattle.currentBattle)
            /// increment to next battle
            indexBattle++
            if (indexBattle < lenBattle) {
                ///assign current battle to the new battle
                gameBattle.currentBattle = Battle(
                    autobot = autobots[indexBattle],
                    deception = decepticons[indexBattle],
                )
                if(indexBattle>lenBattle/2){
                    ///get decepticons wins
                    val countDecepticonWins = gameBattle.previousBattles.filter {
                        it.winnerBattle == TeamTransformer.DECEPTICON
                    }.count()
                    ///get autobots wins
                    val countAutobotsWins = gameBattle.previousBattles.filter {
                        it.winnerBattle == TeamTransformer.AUTOBOTS
                    }.count()
                    ///check number wins if passed the half of battles
                    if (countAutobotsWins > lenBattle / 2) {
                        gameBattle.isFinished = true
                        gameBattle.winner = TeamTransformer.AUTOBOTS

                    } else if (countDecepticonWins > lenBattle / 2) {
                        gameBattle.isFinished = true
                        gameBattle.winner = TeamTransformer.DECEPTICON

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
                }

            } else {
                gameBattle.isFinished = true
                val countDecepticonWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.DECEPTICON
                }.count()

                val countAutobotsWins = gameBattle.previousBattles.filter {
                    it.winnerBattle == TeamTransformer.AUTOBOTS
                }.count()
                when {
                    countAutobotsWins > countDecepticonWins -> {
                        gameBattle.winner = TeamTransformer.AUTOBOTS
                    }
                    countDecepticonWins > countAutobotsWins -> {
                        gameBattle.winner = TeamTransformer.DECEPTICON
                    }
                    else -> {
                        gameBattle.isDraw = true
                    }
                }
                if (autobots.size > lenBattle) {
                    gameBattle.survivors.addAll(autobots.subList(lenBattle, autobots.size))

                } else if (decepticons.size > lenBattle) {
                    gameBattle.survivors.addAll(decepticons.subList(lenBattle, decepticons.size))

                }

            }
        } while (indexBattle < lenBattle)
        Assert.assertEquals(TeamTransformer.AUTOBOTS, gameBattle.winner)
        Assert.assertEquals(autobots[1], gameBattle.survivors.first())
        Assert.assertEquals(1, gameBattle.survivors.size)
    }
}