package dali.hamza.core.repository

import dali.hamza.core.networking.TransformerApi
import dali.hamza.core.utilities.SessionManager
import dali.hamza.domain.model.*
import dali.hamza.domain.repository.IAppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: TransformerApi,
    private val sessionManager: SessionManager
) : IAppRepository {
    override suspend fun retrieveToken(): Result<String> {
        if (sessionManager.getValue(SessionManager.tokenKey) == null) {
            val s = api.getToken().data()
            if (s is Success<String>) {
                sessionManager.setValue(SessionManager.tokenKey, s.data)
            } else {
                return Failure(AppError(Throwable("No token")))
            }

        }
        return Success(sessionManager.getValue(SessionManager.tokenKey) as String)
    }

    override suspend fun playMatch(listTransformers: List<Transformer>): Flow<Result<GameResult>> {

        return flow {
            try {
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
                emit(Success(gameBattle))
                delay(200)
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
                                when {
                                    decepticons[indexBattle].ratingRank() > autobots[indexBattle].ratingRank() -> {
                                        gameBattle.currentBattle.winnerBattle = TeamTransformer.DECEPTICON
                                    }
                                    decepticons[indexBattle].ratingRank() < autobots[indexBattle].ratingRank() -> {
                                        gameBattle.currentBattle.winnerBattle = TeamTransformer.AUTOBOTS
                                    }
                                    else -> {
                                        gameBattle.currentBattle.isDestroyed = true
                                    }
                                }
                            }
                        }

                    }
                    ///add current game to previous
                    gameBattle.previousBattles.add(gameBattle.currentBattle)
                    emit(Success(gameBattle))
                    delay(500)
                    /// increment to next battle
                    indexBattle++
                    if (indexBattle < lenBattle) {
                        ///assign current battle to the new battle
                        gameBattle.currentBattle = Battle(
                            autobot = autobots[indexBattle],
                            deception = decepticons[indexBattle],
                        )
                        emit(Success(gameBattle))
                        delay(200)
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

                emit(Success(gameBattle))
            }catch (e:Exception){
             emit(
                 Failure(AppError(Throwable(), 404)))
            }
        }
    }
}