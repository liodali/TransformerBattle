package dali.hamza.transformerwar.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ItemTransformerBinding

class TransformerAdapter(
     transformers: MutableList<Transformer>
) : BaseAdapter<Transformer, TransformerAdapter.TransformerViewHolder>(transformers) {
    class TransformerViewHolder(
        var bindingView: ItemTransformerBinding
    ) :
        BaseAdapter.BaseViewHolder<Transformer>(bindingView) {
        override fun bind(data: Transformer) {
            var drawable = bindingView.root.context.resources.getDrawable(
                R.drawable.ic_decepticon
            )
            var colorDrawable=R.color.purple_700
            if (data.team == TeamTransformer.AUTOBOTS) {
                drawable = bindingView.root.context.resources.getDrawable(
                    R.drawable.ic_autobot
                )
                colorDrawable=android.R.color.holo_red_light
            }
            bindingView.imageView.setImageDrawable(
                drawable
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                bindingView.imageView.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(bindingView.root.context, colorDrawable))
            }
            bindingView.idNameItemTransformer.text = data.name
            bindingView.idInformationItemTransformer.setTransformer(data)
        }

        companion object TransformerHolder {
            fun create(parent: ViewGroup): TransformerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTransformerBinding.inflate(inflater, parent, false)
                return TransformerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TransformerViewHolder {
        return TransformerViewHolder.create(parent)
    }
}