package com.txpdesenvolvimento.feelflow.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.adapters.AsyncEmotionAdapter
import com.txpdesenvolvimento.feelflow.databinding.FragmentEmotionsBinding
import com.txpdesenvolvimento.feelflow.models.dto.EmotionDto
import com.txpdesenvolvimento.feelflow.services.api.EmotionsApi
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton
import retrofit2.Call
import retrofit2.Response

class EntryEmotions : Fragment() {
    private var _binding : FragmentEmotionsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmotionsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnCreateCalmUncomfortable.setOnClickListener {
            val args = Bundle()
            args.putInt("primary_type", 1)
            args.putInt("secondary_type", 4)

            NavControllerSingleton.getInstance().navigate(R.id.nav_create_emotion, args)
        }

        binding.btnCreateCalmComfortable.setOnClickListener {
            val args = Bundle()
            args.putInt("primary_type", 1)
            args.putInt("secondary_type", 3)

            NavControllerSingleton.getInstance().navigate(R.id.nav_create_emotion, args)
        }

        binding.btnCreateEnergizedComfortable.setOnClickListener {
            val args = Bundle()
            args.putInt("primary_type", 2)
            args.putInt("secondary_type", 3)

            NavControllerSingleton.getInstance().navigate(R.id.nav_create_emotion, args)
        }

        binding.btnCreateEnergizedUncomfortable.setOnClickListener {
            val args = Bundle()
            args.putInt("primary_type", 2)
            args.putInt("secondary_type", 4)

            NavControllerSingleton.getInstance().navigate(R.id.nav_create_emotion, args)
        }


        EmotionsApi.services()
            .getAllEmotions(JWTTokenUtils(requireContext()).retrieveToken()!!)
            .enqueue(object: retrofit2.Callback<Array<EmotionDto>>{
                override fun onResponse(
                    call: Call<Array<EmotionDto>>,
                    response: Response<Array<EmotionDto>>
                ) {
                    var msg = ""
                    when (response.code()) {
                        200 -> {
                            binding.recyclerCalmUncomfortable.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
                            binding.recyclerCalmComfortable.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
                            binding.recyclerEnergizedUncomfortable.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
                            binding.recyclerEnergizedComfortable.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)

                            val emotions = EmotionDto.toModelList(response.body()!!.toList())

                            binding.recyclerCalmUncomfortable.adapter = AsyncEmotionAdapter(emotions.filter { it.primaryType.id == 1 && it.secondaryType.id == 4 }.toTypedArray())
                            binding.recyclerCalmComfortable.adapter = AsyncEmotionAdapter(emotions.filter { it.primaryType.id == 1 && it.secondaryType.id == 3 }.toTypedArray())
                            binding.recyclerEnergizedUncomfortable.adapter = AsyncEmotionAdapter(emotions.filter { it.primaryType.id == 2 && it.secondaryType.id == 4 }.toTypedArray())
                            binding.recyclerEnergizedComfortable.adapter = AsyncEmotionAdapter(emotions.filter { it.primaryType.id == 2 && it.secondaryType.id == 3 }.toTypedArray())
                        }
                        204 -> msg = "A Emoção alvo não pôde ser encontrada."
                        400 -> msg = "Os dados fornecidos são inválidos."
                        401 -> msg = "Acesso negado a API, realize o Login novamente."
                        403 -> msg = "Operação não permitida."
                        500 -> msg = "Erro interno da API, tente novamente mais tarde."

                        else -> msg = response.code().toString()
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Array<EmotionDto>>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Erro ao acessar a API",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        return view
    }
}