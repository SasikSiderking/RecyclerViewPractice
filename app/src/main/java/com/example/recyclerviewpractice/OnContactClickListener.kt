package com.example.recyclerviewpractice

fun interface OnContactClickListener {
    fun onClick(contactId: Int, isChecked: Boolean)
}