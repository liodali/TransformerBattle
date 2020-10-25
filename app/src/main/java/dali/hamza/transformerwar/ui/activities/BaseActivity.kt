package dali.hamza.transformerwar.ui.activities

import android.app.Dialog
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun snackBar(
    @StringRes message: Int,
    rootView: View,
    duration: Int = Snackbar.LENGTH_SHORT,
) =
    Snackbar.make(rootView, message, duration).show()

fun snackBar(
    message: String,
    rootView: View,
    duration: Int = Snackbar.LENGTH_SHORT,
) =
    Snackbar.make(rootView, message, duration).show()

fun View.visible() {
    visibility = View.VISIBLE
}
fun View.enabled(){
    isEnabled=true
}

fun View.disabled(){
    isEnabled=false
}

fun View.gone() {
    visibility = View.GONE
}

abstract class BaseActivity : AppCompatActivity() {
    fun showSnackBar(@StringRes message: Int, rootView: View) = snackBar(message, rootView)

    fun showSnackBar(message: String?, rootView: View) = snackBar(message ?: "", rootView)

    open fun showLoading(loadingView: View) = loadingView.visible()

    open fun hideLoading(loadingView: View) = loadingView.gone()


    fun showDialog(dialog: Dialog) = dialog.show()

    fun dismissDialog(dialog: Dialog?) = dialog?.cancel()
}