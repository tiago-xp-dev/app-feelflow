package com.txpdesenvolvimento.feelflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.txpdesenvolvimento.feelflow.databinding.FragmentEntryNoteBinding
import com.txpdesenvolvimento.feelflow.models.api.requests.CreateNoteRequest
import com.txpdesenvolvimento.feelflow.models.dto.NoteDto
import com.txpdesenvolvimento.feelflow.services.api.NotesApi
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EntryNote : Fragment() {

    var _binding: FragmentEntryNoteBinding? = null
    val binding get() = _binding!!
    var entryId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        val token = JWTTokenUtils(requireContext()).retrieveToken()!!

        entryId = requireArguments().getInt("entryId")

        NotesApi.services().getNote(entryId, token)
            .enqueue(object: Callback<NoteDto>{
                override fun onResponse(call: Call<NoteDto>, response: Response<NoteDto>) {
                    var msg = ""
                    when(response.code()){
                        200 -> {
                            if(!response.body()?.content.equals(null)){
                                binding.editTextTextMultiLine.setText(response.body()?.content)
                            }
                        }
                        400 -> msg = "Os dados fornecidos são inválidos."
                        401 -> msg = "Acesso negado a API, realize o Login novamente."
                        500 -> msg = "Erro interno da API, tente novamente mais tarde."
                        else -> msg = response.code().toString()
                    }
                    if(msg.isNotBlank())
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<NoteDto>, t: Throwable) {
                    Toast.makeText(context, "Erro ao acessar a API", Toast.LENGTH_SHORT).show()
                }

            })

        binding.editTextTextMultiLine.requestFocus()

        return view
    }

    override fun onStop() {
        val token = JWTTokenUtils(requireContext()).retrieveToken()!!
        val noteContent = binding.editTextTextMultiLine.text.toString()

        NotesApi.services().getNote(entryId, token)
            .enqueue(object: Callback<NoteDto>{
                override fun onResponse(call: Call<NoteDto>, response: Response<NoteDto>) {
                    var msg = ""
                    when(response.code()){
                        200 -> {
                            if(response.body()?.content.equals(null)){
                                val body = CreateNoteRequest(noteContent)
                                NotesApi.services().createNote(entryId, body, token).enqueue(object : Callback<Void>{
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {}
                                    override fun onFailure(call: Call<Void>, t: Throwable) {}
                                })
                            }else{
                                val body = CreateNoteRequest(noteContent)
                                NotesApi.services().editNote(entryId, body, token).enqueue(object : Callback<Void>{
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {}
                                    override fun onFailure(call: Call<Void>, t: Throwable) {}
                                })
                            }
                        }
                        400 -> msg = "Os dados fornecidos são inválidos."
                        401 -> msg = "Acesso negado a API, realize o Login novamente."
                        500 -> msg = "Erro interno da API, tente novamente mais tarde."
                        else -> msg = response.code().toString()
                    }
                    if(msg.isNotBlank())
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<NoteDto>, t: Throwable) {
                    Toast.makeText(context, "Erro ao acessar a API", Toast.LENGTH_SHORT).show()
                }

            })

        super.onStop()
    }
}