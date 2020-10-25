package dali.hamza.transformerwar.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.LoadingDialogBinding

class ProgressDialog {
    companion object {
        fun progressDialog(context: Context, text: String): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.loading_dialog)
            dialog.findViewById<TextView>(R.id.id_loading_text).text = text
            return dialog.apply {
                setCancelable(false)
                window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                )
            }
        }
    }
}