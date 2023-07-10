package com.example.recyclerviewpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.javafaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections

class MainViewModel : ViewModel() {

    private val _contactsStateFlow: MutableStateFlow<List<ContactItem>> =
        MutableStateFlow(emptyList())
    val contactsStateFlow: Flow<List<ContactItem>> = _contactsStateFlow.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val faker = Faker.instance()
            val contacts = (1..100).map {
                ContactItem(
                    id = it,
                    isChecked = false,
                    name = faker.name().firstName() + " " + faker.name().lastName(),
                    phoneNumber = faker.phoneNumber().cellPhone()
                )
            }

            _contactsStateFlow.emit(contacts)
        }
    }

    fun addContact(contactName: String, contactNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contactsStateFlow.value.toMutableList()

            val newContact =
                ContactItem(currentList.maxOf { contactItem -> contactItem.id } + 1,
                    false,
                    contactName,
                    contactNumber)
            currentList.add(newContact)

            _contactsStateFlow.emit(currentList.toList())
        }
    }

    fun getContact(contactId: Int): ContactItem? {
        return _contactsStateFlow.value.find { contactItem -> contactItem.id == contactId }
    }

    fun deleteContacts(contactIds: MutableList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contactsStateFlow.value.toMutableList()

            contactIds.forEach { id -> currentList.removeIf { item -> item.id == id } }

            contactIds.clear()
            _contactsStateFlow.emit(currentList.toList())
        }
    }

    fun editContact(oldItem: ContactItem, contactName: String, contactNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contactsStateFlow.value.toMutableList()

            val index = currentList.indexOf(oldItem)
            currentList[index] = oldItem.copy(name = contactName, phoneNumber = contactNumber)

            _contactsStateFlow.emit(currentList.toList())
        }
    }

    fun checkUncheckContact(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contactsStateFlow.value.toMutableList()

            val item = currentList.find { contactItem -> contactItem.id == id }
            item?.let {
                it.isChecked = !it.isChecked
            }

            _contactsStateFlow.emit(currentList.toList())
        }
    }

    fun swapContacts(from: Int, to: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contactsStateFlow.value.toMutableList()

            Collections.swap(currentList, from, to)
        }
    }
}