package dali.hamza.transformerwar.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.Picasso
import dali.hamza.domain.model.TeamTransformer
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.model.ratingRank
import dali.hamza.transformerwar.R
import dali.hamza.transformerwar.databinding.ItemTransformerBinding

class TransformerAdapter(
    transformers: MutableList<Transformer>,
    private val actionCallback: TransformerAction,
) : BaseAdapter<Transformer, TransformerAdapter.TransformerViewHolder>(transformers) {
    class TransformerViewHolder(
        var bindingView: ItemTransformerBinding,
        var action: TransformerAction
    ) :
        BaseAdapter.BaseViewHolder<Transformer>(bindingView) {
        override fun bind(data: Transformer) {
            var drawable = ResourcesCompat.getDrawable(
                bindingView.root.context.resources,
                R.drawable.ic_decepticon, null
            )
            if (data.team == TeamTransformer.AUTOBOTS) {
                drawable = ResourcesCompat.getDrawable(
                    bindingView.root.context.resources,
                    R.drawable.ic_autobot, null
                )
            }
            Picasso.get().load(data.icon).placeholder(drawable!!).into(bindingView.imageView)
            bindingView.idRankRatingItemTransformer.text =
                bindingView.root.context.resources.getString(
                    R.string.rating_rank,
                    data.ratingRank().toString()
                )
            bindingView.idNameItemTransformer.text = data.name
            bindingView.idInformationItemTransformer.setTransformer(data)
            bindingView.idDeleteTransformer.setOnClickListener {
                action.deleteTransformer(data.id)
            }
            bindingView.idEditTransformer.setOnClickListener {
                action.editTransformer(data)
            }
        }

        companion object TransformerHolder {
            fun create(parent: ViewGroup, action: TransformerAction): TransformerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTransformerBinding.inflate(inflater, parent, false)
                return TransformerViewHolder(binding, action = action)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TransformerViewHolder {
        return TransformerViewHolder.create(parent, action = actionCallback)
    }

    interface TransformerAction {
        fun editTransformer(transformer: Transformer)
        fun deleteTransformer(id: String)
    }
}