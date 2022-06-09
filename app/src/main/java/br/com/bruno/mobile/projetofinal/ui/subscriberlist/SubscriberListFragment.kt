package br.com.bruno.mobile.projetofinal.ui.subscriberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import br.com.bruno.mobile.projetofinal.R
import br.com.bruno.mobile.projetofinal.data.db.AppDatabase
import br.com.bruno.mobile.projetofinal.data.db.dao.SubscriberDAO
import br.com.bruno.mobile.projetofinal.data.db.entity.SubscriberEntity
import br.com.bruno.mobile.projetofinal.extension.navigateWithAnimations
import br.com.bruno.mobile.projetofinal.repository.DatabaseDataSource
import br.com.bruno.mobile.projetofinal.repository.SubscriberRepository
import br.com.bruno.mobile.projetofinal.ui.subscriber.SubscriberViewModel
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    private val viewModel: SubscriberListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDatabase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DatabaseDataSource(subscriberDAO)
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()

    }

    private fun observeViewModelEvents() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            val subscriberListAdapter = SubscriberListAdapter(allSubscribers)

            with(recycler_subscribers) {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscibers()
    }

    private fun configureViewListeners(){
        fabAddSubscriber.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.subscriberFragment)
        }
    }
}