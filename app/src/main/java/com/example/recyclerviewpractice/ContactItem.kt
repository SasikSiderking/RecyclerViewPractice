package com.example.recyclerviewpractice

data class ContactItem(
    override val id: Int,
    var isChecked: Boolean,
    val name: String,
    val phoneNumber: String
) : BaseListItem