package com.txpdesenvolvimento.feelflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.txpdesenvolvimento.feelflow.databinding.FragmentSignUpBinding
import com.txpdesenvolvimento.feelflow.models.api.requests.CreateUserRequest
import com.txpdesenvolvimento.feelflow.services.api.UsersApi
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        val fUserName = binding.edtTxtUsername
        val fPassword = binding.edtTxtPassword
        val fPasswordConf = binding.edtTxtConfirmPassword
        val fEmail = binding.edtTxtEmail

        binding.btnSignIn.setOnClickListener {
            NavControllerSingleton.getInstance().navigate(SignUpDirections.actionNavSignUpToNavSignIn())
        }

        binding.btnSignUp.setOnClickListener {

            if (!fPassword.text.toString().equals(fPasswordConf.text.toString())) {
                Toast.makeText(context, "Senhas não coindicem", LENGTH_SHORT).show()
                fPassword.backgroundTintList = context?.getColorStateList(R.color.button_red)
                fPasswordConf.backgroundTintList = context?.getColorStateList(R.color.button_red)
                return@setOnClickListener
            }
            val body =
                CreateUserRequest(fUserName.text.toString(), fPassword.text.toString(), fEmail.text.toString())

            UsersApi.services().createUser(body).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    var msg = ""
                    when(response.code()){
                        201 -> {
                            msg = "Usuário criado com sucesso!"
                            NavControllerSingleton.getInstance().navigate(SignUpDirections.actionNavSignUpToNavSignIn())
                        }
                        400 -> {
                            msg = "Os dados fornecidos são inválidos."
                        }
                        409 -> {
                            msg = "Este E-mail já está em uso."
                            fEmail.backgroundTintList = context?.getColorStateList(R.color.button_red)
                        }
                        500 -> {
                            msg = "Erro interno da API, tente novamente mais tarde."
                        }
                        else -> {
                            msg = response.code().toString()
                        }
                    }
                    Toast.makeText(context, msg, LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, "Erro ao acessar a API", LENGTH_SHORT).show()
                }
            })
        }
        return view
    }
}