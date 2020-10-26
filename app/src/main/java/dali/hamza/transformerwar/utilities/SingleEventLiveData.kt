package dali.hamza.transformerwar.utilities

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleEventLiveData<T> : LiveData<T>() {

    private val mPending: AtomicBoolean = AtomicBoolean(false)


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false))
                observer.onChanged(it)
        })
    }

     fun setEvent(v: T) {
        mPending.set(true)
        super.setValue(v)
    }



}