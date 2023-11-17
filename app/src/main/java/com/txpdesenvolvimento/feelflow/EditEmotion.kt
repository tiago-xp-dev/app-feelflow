package com.txpdesenvolvimento.feelflow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.txpdesenvolvimento.feelflow.databinding.FragmentCreateEmotionBinding
import com.txpdesenvolvimento.feelflow.models.api.requests.CreateEmotionRequest
import com.txpdesenvolvimento.feelflow.services.api.EmotionsApi
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditEmotion : Fragment() {
    private var _binding: FragmentCreateEmotionBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateEmotionBinding.inflate(inflater, container, false)
        val view = binding.root

        val primaryType = requireArguments().getInt("primary_type")
        val secondaryType = requireArguments().getInt("secondary_type")

        val fDescription = binding.edtTxtDescription
        val tVPrimary = binding.txtFWeightPrimary
        val tVSecondary = binding.txtFSecondaryWeight
        val sRPrimary = binding.rSPrimaryWeight
        val sRSecundary = binding.rSSecondaryWeight

        when(primaryType){
            1 -> tVPrimary.text = tVPrimary.text.toString() + " - " + resources.getString(R.string.field_hint_calm)
            2 -> tVPrimary.text = tVPrimary.text.toString() + " - " + resources.getString(R.string.field_hint_energized)
        }

        when(secondaryType){
            3 -> tVSecondary.text = tVSecondary.text.toString() + " - " + resources.getString(R.string.field_hint_comfortable)
            4 -> tVSecondary.text = tVSecondary.text.toString() + " - " + resources.getString(R.string.field_hint_uncomfortable)
        }

        binding.btnConfirm.setOnClickListener {
            if(fDescription.text.toString().isBlank()){
                Toast.makeText(context, "Forneça uma descrição.", Toast.LENGTH_SHORT).show()
                fDescription.backgroundTintList = context?.getColorStateList(R.color.button_red)
                return@setOnClickListener
            }

            val body = CreateEmotionRequest(
                description = fDescription.text.toString(),
                primaryIdType = primaryType,
                secondaryIdType = secondaryType,
                primaryWeight = sRPrimary.values[0],
                secondaryWeight = sRSecundary.values[0],
            )

            EmotionsApi.services()
                .createEmotion(body, JWTTokenUtils(requireContext()).retrieveToken()!!)
                .enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        var msg = ""
                        when(response.code()){
                            200 -> {
                                msg = "Emoção \""+ fDescription.text.toString()+ "\" criada com sucesso!"
                                //NavControllerSingleton.getInstance().navigate(SignUpDirections.actionNavSignUpToNavSignIn())
                            }
                            400 -> msg = "Os dados fornecidos são inválidos."
                            401 -> msg = "Acesso negado a API, realize o Login novamente."
                            500 -> msg = "Erro interno da API, tente novamente mais tarde."
                            else -> msg = response.code().toString()
                        }
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(context, "Erro ao acessar a API", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        return view
    }
}