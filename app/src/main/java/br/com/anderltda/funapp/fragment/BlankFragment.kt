package br.com.anderltda.funapp.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R

class BlankFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_blank, container, false);
        val tvMessage = view.findViewById<TextView>(R.id.tvMensagem)

        tvMessage.text = arguments?.getString("texto")

        return view
    }

    companion object {
        fun newInstance(texto: String): BlankFragment {
            val fragment = BlankFragment()
            val bundle = Bundle()
            bundle.putString("texto", texto)
            fragment.arguments = bundle
            return fragment
        }
    }

}
