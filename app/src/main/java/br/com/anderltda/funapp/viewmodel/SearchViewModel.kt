package br.com.anderltda.funapp.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.anderltda.funapp.data.entity.Address
import br.com.anderltda.funapp.data.repository.AddressRepository

class SearchViewModel: ViewModel() {

    val addressRepository = AddressRepository()

    val address = MutableLiveData<Address>()
    val mensagemErro = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

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