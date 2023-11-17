package com.txpdesenvolvimento.feelflow.adapters

import AsyncCell
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.ActivitySelectorEmotionBinding
import com.txpdesenvolvimento.feelflow.models.BaseEmotion
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton

class AsyncEntryAddEmotionAdapter internal constructor(private val dataSet: Array<BaseEmotion>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
         EmotionItemViewHolder(EmotionItemCell(parent).apply{ inflate() })
    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EmotionItemViewHolder -> setupEmotionViewHolder(holder, position)
        }
    }

    private fun setupEmotionViewHolder(holder: EmotionItemViewHolder, position: Int) {
        (holder.itemView as AsyncEntryAddEmotionAdapter.EmotionItemCell).bindWhenInflated {
            dataSet[position].let {item ->
                val binding = holder.itemView.binding!!
                binding.txtVDay.text = item.description

                binding.dayContainer.setOnClickListener {
                    val args = Bundle()
                    args.putInt("emotion_id", item.id)
                    args.putInt("user_id", item.userId)
                    NavControllerSingleton.getInstance().navigate(R.id.nav_edit_emotion, args)
                }
            }
        }
    }

    private inner class EmotionItemViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    private inner class EmotionItemCell(viewGroup: ViewGroup) : AsyncCell(viewGroup) {
        var binding: ActivitySelectorEmotionBinding? = null
        override val layoutId = R.layout.activity_selector_emotion
        override fun createDataBindingView(view: View): View? {
            binding = ActivitySelectorEmotionBinding.bind(view)
            return view.rootView
        }
    }
}