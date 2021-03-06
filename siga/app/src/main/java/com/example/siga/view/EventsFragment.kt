package com.example.siga.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siga.R
import com.example.siga.viewmodel.AppViewModel
import com.example.siga.viewmodel.Event
import com.example.siga.viewmodel.InjectorUtils
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var recyclerView: RecyclerView
private lateinit var eventsArrayList: ArrayList<Event>
private lateinit var eventsAdapter: EventsAdapter
private lateinit var db: FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        eventsArrayList = arrayListOf()
        eventsAdapter = EventsAdapter(eventsArrayList, activity as HomeActivity)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment


        val view =  inflater.inflate(R.layout.fragment_events, container, false)

        recyclerView = view.findViewById(R.id.rvEvents)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        // recyclerView.setHasFixedSize(true)
        recyclerView.adapter = eventsAdapter

        var ab = (activity as AppCompatActivity).supportActionBar

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setHomeButtonEnabled(false);
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = InjectorUtils.provideViewModelFactory(context)
        val viewModel: AppViewModel by viewModels {factory}

        EventChangeListener(viewModel)
    }

    private fun EventChangeListener(viewModel :AppViewModel ) {

        viewModel.fetchRemoteEvents()

        viewModel.remoteEvents().observe(this, EventObserver() )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
class EventObserver : Observer<ArrayList<Event>> {
    override fun onChanged(it: ArrayList<Event>) {
        Log.d("Observer events", "Events")
        eventsArrayList.removeAll(eventsArrayList)
        eventsArrayList.addAll(it)
        eventsAdapter.notifyDataSetChanged()
    }
}