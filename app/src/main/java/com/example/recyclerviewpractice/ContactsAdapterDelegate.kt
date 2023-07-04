package com.example.recyclerviewpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recyclerviewpractice.ContactsAdapterDelegate.ContactsViewHolder
import com.example.recyclerviewpractice.databinding.ItemContactBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ContactsAdapterDelegate(private val onContactClickListener: OnContactClickListener) :
    AbsListItemAdapterDelegate<ContactItem, BaseListItem, ContactsViewHolder>(),
    OnViewHolderClickListener<ContactsViewHolder> {

    override fun isForViewType(
        item: BaseListItem,
        items: MutableList<BaseListItem>,
        position: Int
    ): Boolean {
        return item is ContactItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ContactsViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding, this)
    }

    override fun onBindViewHolder(
        item: ContactItem,
        holder: ContactsViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.itemView.setTag(R.id.base_item_tag_key_id, item)

        with(holder.binding) {
            checkBox.isChecked = item.isChecked
            contactId.text = item.id.toString()
            contactName.text = item.name
            contactNumber.text = item.phoneNumber
        }
    }

    override fun onViewHolderClick(view: View, holder: ContactsViewHolder) {

        with(holder.binding.checkBox) {
            isChecked = !isChecked
        }

        val item = getItemFromItemView(holder.itemView)
        onContactClickListener.onClick(item.id, holder.binding.checkBox.isChecked)
    }

    private fun getItemFromItemView(itemView: View): ContactItem {
        return itemView.getTag(R.id.base_item_tag_key_id) as? ContactItem
            ?: throw IllegalStateException("Item is null or has a wrong type")
    }

    class ContactsViewHolder(
        val binding: ItemContactBinding,
        clickListener: OnViewHolderClickListener<ContactsViewHolder>
    ) : ClickableViewHolder<ContactsViewHolder>(binding.root, clickListener)
}