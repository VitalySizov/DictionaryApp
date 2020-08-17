package ru.vitalysizov.dictionaryapp.presentation.search.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import ru.vitalysizov.dictionaryapp.R
import ru.vitalysizov.dictionaryapp.databinding.FragmentSearchBinding
import ru.vitalysizov.dictionaryapp.domain.params.translate.Language
import ru.vitalysizov.dictionaryapp.presentation.base.BaseFragment
import ru.vitalysizov.dictionaryapp.presentation.search.adapter.SearchResultAdapter
import ru.vitalysizov.dictionaryapp.presentation.search.viewmodel.SearchViewModel
import ru.vitalysizov.dictionaryapp.utils.InternetConnectionLiveData
import ru.vitalysizov.dictionaryapp.utils.dismissKeyboard
import ru.vitalysizov.dictionaryapp.utils.isNetworkAvailable
import ru.vitalysizov.dictionaryapp.utils.showToast
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentSearchBinding

    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory }
    )

    private lateinit var internetConnectionLiveData: InternetConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = searchViewModel

        initTranslateButton()
        initSpinnersAdapter()
        initSpinnersObservers()
        initSearchViewTextListener()
        initSearchResultAdapter()
        initShowToastsMessages()
        initCheckInternetConnectionObserver()

        return binding.root
    }

    private fun initSpinnersAdapter() {
        val spinnerArrayAdapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            resources.getStringArray(R.array.languages)
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int, convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding.spinnerOriginalLanguage.adapter = spinnerArrayAdapter
        binding.spinnerResultLanguage.adapter = spinnerArrayAdapter
    }

    private fun initTranslateButton() {
        binding.btnTranslate.setOnClickListener {
            dismissKeyboard(binding.root)
            searchViewModel.getTranslate()
        }
    }

    private fun initSearchResultAdapter() {
        val adapter = SearchResultAdapter()
        binding.rvResults.adapter = adapter

        searchViewModel.translateResult.observe(viewLifecycleOwner) { results ->
            adapter.items = results
        }
    }

    private fun initSpinnersObservers() {
        searchViewModel.choiceOriginalLanguage.observe(viewLifecycleOwner) { originalLanguage ->
            checkCorrectLanguages(binding.spinnerResultLanguage, originalLanguage)
        }

        searchViewModel.choiceResultLanguage.observe(viewLifecycleOwner) { resultLanguage ->
            checkCorrectLanguages(binding.spinnerOriginalLanguage, resultLanguage)
        }
    }

    private fun checkCorrectLanguages(spinner: Spinner, language: Int) {
        when (language) {
            Language.ENG.ordinal, Language.GER.ordinal, Language.FRE.ordinal -> {
                spinner.setSelection(Language.RUS.ordinal)
            }
            Language.RUS.ordinal -> {
                if (searchViewModel.choiceResultLanguage.value == Language.RUS.ordinal) {
                    spinner.setSelection(Language.ENG.ordinal)
                }
            }
        }
    }

    private fun initSearchViewTextListener() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismissKeyboard(binding.root)
                    searchViewModel.getTranslate()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchViewModel.setSearchQuery(newText)
                    return true
                }
            })
        }
    }

    private fun initShowToastsMessages() {
        searchViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                requireContext().showToast(message)
            }
        }
    }

    private fun initCheckInternetConnectionObserver() {
        internetConnectionLiveData = InternetConnectionLiveData(requireContext())
        internetConnectionLiveData.observe(viewLifecycleOwner) {
            searchViewModel.isInternetAvailableMutable.value = it
        }
        searchViewModel.isInternetAvailableMutable.value = isNetworkAvailable(requireContext())
    }

}