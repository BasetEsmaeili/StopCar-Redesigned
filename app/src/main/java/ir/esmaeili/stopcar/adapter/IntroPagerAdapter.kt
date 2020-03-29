package ir.esmaeili.stopcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.databinding.ItemviewIntroBinding

import ir.esmaeili.stopcar.models.Slide
import ir.esmaeili.stopcar.ui.base.BaseViewHolder
import ir.esmaeili.stopcar.ui.fragments.intro.IntroPagerViewModel

class IntroPagerAdapter(private val itemsList: List<Slide>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = ItemviewIntroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


    inner class IntroViewHolder(
        private val itemViewIntroBinding: ItemviewIntroBinding
    ) : BaseViewHolder(itemViewIntroBinding.root) {


        private val viewModel: IntroPagerViewModel = IntroPagerViewModel()
        override fun onBind(position: Int) {
            itemViewIntroBinding.viewModel = viewModel
            itemViewIntroBinding.animIntroLottie.setAnimation(itemsList[position].anim)
            viewModel.setTitle(itemsList[position].title)
            viewModel.setDescription(itemsList[position].description)
            itemViewIntroBinding.executePendingBindings()

        }
    }
}