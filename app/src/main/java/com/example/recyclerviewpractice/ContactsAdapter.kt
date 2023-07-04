package com.example.recyclerviewpractice

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactsAdapter(onCheckedChangeListener: OnContactClickListener) :
    AsyncListDifferDelegationAdapter<BaseListItem>(BaseDiffUtils()) {
    init {
        delegatesManager.addDelegate(ContactsAdapterDelegate(onCheckedChangeListener))
    }
}
