package dali.hamza.domain.interactor

interface UseCase<T : Any, R: Any>{
    suspend operator fun invoke(parameter:T):R
}