package br.com.anderltda.funapp.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.Contact
import br.com.anderltda.funapp.util.Constants
import br.com.anderltda.funapp.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_address.*
import kotlinx.android.synthetic.main.fragment_address.*
import java.text.SimpleDateFormat
import java.util.*


class AddressFragment : Fragment() {

    private lateinit var root: ViewGroup

    private lateinit var auth: FirebaseAuth

    private lateinit var searchViewModel: SearchViewModel

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refLocationStates by lazy {
        firestore.collection(Constants.CONTACTS_LOCATION_APP_FIREBASE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_address, container, false)

        root = view.findViewById(R.id.root)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        val title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = resources.getString(R.string.title_address)

        val back = toolbar.findViewById(R.id.tv_back) as TextView
        back.visibility = View.VISIBLE

        back.setOnClickListener{
            val fragmentManager = activity!!.supportFragmentManager
            if(fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            }
        }

        val zipcode: EditText = view.findViewById(R.id.et_zip_code) as EditText

        zipcode.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                //searchAddress()
            }
        }


        zipcode.setOnClickListener{
            searchAddress()
        }

        val button = view.findViewById(R.id.bt_continue) as Button

        button.setOnClickListener {
            saveFirestoneDatabase()
        }

        return view
    }

    fun searchAddress() {

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searchViewModel.buscar(et_zip_code.text.toString())

        searchViewModel.address.observe(this, Observer {
            et_address.setText(it?.toString())
        })

        searchViewModel.mensagemErro.observe(this, Observer {
            if (!it.equals("")) {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })

        searchViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }

        })
    }

    private fun saveFirestoneDatabase() {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val sdf = SimpleDateFormat("h:mm a")
        val hora = Calendar.getInstance().getTime()
        val dataFormatada = sdf.format(hora)

        val contact = Contact()
        contact.update = dataFormatada
        contact.zipcode = et_zip_code.text.toString()
        contact.address = et_address.text.toString()
        contact.number = et_number.text.toString()
        contact.lat = "-23.4834015"
        contact.long = "-46.661051"
        refLocationStates.document(uid).set(contact);
    }

    companion object {
        fun newInstance(name: String): AddressFragment {
            val fragment = AddressFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            fragment.setArguments(bundle)
            return fragment
        }
    }

}
