package br.com.anderltda.funapp.data.repository

import br.com.anderltda.funapp.api.getAddressService
import br.com.anderltda.funapp.data.entity.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRepository {

    fun buscar(cep: String, onComplete: (Address?) -> Unit, onError: (Throwable?) -> Unit) {

        getAddressService().buscar(cep).enqueue(object : Callback<Address> {

            override fun onFailure(call: Call<Address>?, t: Throwable?) {
                onError(t)
            }

            override fun onResponse(call: Call<Address>?, response: Response<Address>?) {

                if(response?.isSuccessful == true) {
                    onComplete(response?.body())
                } else {
                    onError(Throwable(response?.errorBody()?.string()))
                }

            }

        })
    }
}