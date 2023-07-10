package com.example.recyclerviewpractice

interface BaseListItem {
    val id: Int

    override fun equals(other: Any?): Boolean
}