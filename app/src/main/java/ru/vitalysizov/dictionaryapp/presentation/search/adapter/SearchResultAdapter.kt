package ru.vitalysizov.dictionaryapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.vitalysizov.dictionaryapp.databinding.ItemWordTranslateResultBinding
import ru.vitalysizov.dictionaryapp.model.domain.WordTranslateItem
import ru.vitalysizov.dictionaryapp.utils.executeAfter

class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private val differ = AsyncListDiffer<Any>(this, DiffCallback)

    var items: List<Any> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultViewHolder(
            ItemWordTranslateResultBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.binding.executeAfter {
            val currentItem = differ.currentList[position] as WordTranslateItem
            item = currentItem
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is WordTranslateItem && newItem is WordTranslateItem -> oldItem == newItem
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is WordTranslateItem && newItem is WordTranslateItem -> oldItem.word == oldItem.word
                else -> true
            }
        }
    }
}

class SearchResultViewHolder(val binding: ItemWordTranslateResultBinding) :
    RecyclerView.ViewHolder(binding.root) {}
