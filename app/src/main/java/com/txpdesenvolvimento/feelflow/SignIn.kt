package com.txpdesenvolvimento.feelflow

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.txpdesenvolvimento.feelflow.databinding.FragmentSignInBinding
import com.txpdesenvolvimento.feelflow.models.api.requests.ValidateUserRequest
import com.txpdesenvolvimento.feelflow.models.api.responses.ValidateUserResponse
import com.txpdesenvolvimento.feelflow.services.api.ApiServices
import com.txpdesenvolvimento.feelflow.services.api.UsersApi
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : Fragment() {

    private var _binding : FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        val fEmail = binding.edtTxtEmailLogin
        val fPassword = binding.edtTxtPasswordLogin

        binding.btnSignIn.setOnClickListener {
            val body = ValidateUserRequest(fPassword.text.toString(), fEmail.text.toString())

            UsersApi.services().validateUser(body)
                .enqueue(object : Callback<ValidateUserResponse> {
                    override fun onResponse(
                        call: Call<ValidateUserResponse>,
                        response: Response<ValidateUserResponse>
                    ) {
                        var msg = ""
                        when (response.code()) {
                            202 -> {
                                msg = "Logado com sucesso!\nBem-vindo!"
                                JWTTokenUtils(context!!).storeToken(response.body()?.token!!)
                                NavControllerSingleton.getInstance().navigate(SignInDirections.actionNavSignInToNavCalendar())
                            }

                            204 -> {
                                msg = "Usuário ou senha incorretos."
                            }

                            500 -> {
                                msg = "Erro interno da API, tente novamente mais tarde."
                            }

                            else -> {
                                msg = response.code().toString()
                            }
                        }
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ValidateUserResponse>, t: Throwable) {
                        Toast.makeText(context, "Erro ao acessar a API", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }

        binding.btnSignUp.setOnClickListener {
            NavControllerSingleton.getInstance().navigate(SignInDirections.actionNavSignInToNavSignUp())
        }

        return view
    }
}