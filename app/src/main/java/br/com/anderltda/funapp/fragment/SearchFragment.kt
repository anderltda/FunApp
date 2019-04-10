package br.com.anderltda.funapp.fragment


import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.Address
import br.com.anderltda.funapp.repository.AddressRepository


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    val addressRepository = AddressRepository()

    val address = MutableLiveData<Address>()
    val mensagemErro = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    fun buscar(cep: String) {

        isLoading.value = true

        addressRepository.buscar(
            cep,
            onComplete = {
                address.value = it
                mensagemErro.value = ""
                isLoading.value = false
            },
            onError = {
                address.value = null
                mensagemErro.value = it?.message
                isLoading.value = false
            }

        )

    }

}
