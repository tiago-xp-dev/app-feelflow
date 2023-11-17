package com.txpdesenvolvimento.feelflow.adapters

import AsyncCell
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.ActivitySelectorEmotionBinding
import com.txpdesenvolvimento.feelflow.models.BaseEmotion
import com.txpdesenvolvimento.feelflow.services.api.CompositionApi
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsyncEntryRemoveEmotionAdapter internal constructor(private val entryId: Int, private val dataSet: Array<BaseEmotion>) :
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
        (holder.itemView as AsyncEntryRemoveEmotionAdapter.EmotionItemCell).bindWhenInflated {
            dataSet[position].let {item ->
                val token = JWTTokenUtils(context).retrieveToken()!!
                val binding = holder.itemView.binding!!
                binding.txtVDay.text = item.description

                binding.dayContainer.setOnClickListener {
                    val args = Bundle()

                    CompositionApi.services().addEmotion(entryId, item.id, token)
                        .enqueue(object: Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                var msg = ""
                                when(response.code()){
                                    200 -> {
                                        msg = "Emoção adicionada a entrada com sucesso!!"
                                        NavControllerSingleton.getInstance().popBackStack()
                                    }
                                    400 -> msg = "Os dados fornecidos são inválidos."
                                    401 -> msg = "Acesso negado a API, realize o Login novamente."
                                    500 -> msg = "Erro interno da API, tente novamente mais tarde."
                                    else -> msg = response.code().toString()
                                }
                                if(msg.isNotBlank())
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(context, "Erro ao acessar a API", Toast.LENGTH_SHORT).show()
                            }

                        })

                    NavControllerSingleton.getInstance().popBackStack()
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