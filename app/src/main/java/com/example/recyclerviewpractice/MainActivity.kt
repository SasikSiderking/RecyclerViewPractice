package com.example.recyclerviewpractice

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.Collections

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding
        get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    private var adapter: ContactsAdapter? = null

    private val viewModel: MainViewModel by viewModels()

    private val chosenContactIds: MutableList<Int> = mutableListOf()

    private var items: List<ContactItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = ContactsAdapter(onContactClickListener)

        val itemTouchHelper = ItemTouchHelper(dragAndDrop)
        itemTouchHelper.attachToRecyclerView(binding.contactsRecyclerView)

        with(binding) {
            contactsRecyclerView.adapter = adapter

            addButton.setOnClickListener {
                openAddDialog()
            }
            editButton.setOnClickListener {
                openEditDialog()
            }
            deleteButton.setOnClickListener {
                viewModel.deleteContacts(chosenContactIds)
            }
        }

        collectContacts()
    }

    private fun collectContacts() {
        lifecycleScope.launch {
            viewModel.contactsStateFlow.collect { contacts ->
                adapter?.items = contacts
                updateButtons()

                items = contacts
            }
        }
    }

    private val onContactClickListener = OnContactClickListener { contactId, isChecked ->
        if (isChecked) {
            chosenContactIds.add(contactId)
        } else {
            chosenContactIds.remove(contactId)
        }
        viewModel.checkUncheckContact(contactId)

        updateButtons()
    }

    private fun updateButtons() {
        when (chosenContactIds.size) {
            0 -> {
                with(binding) {
                    editButton.isEnabled = false
                    deleteButton.isEnabled = false
                }
            }

            1 -> {
                with(binding) {
                    editButton.isEnabled = true
                    deleteButton.isEnabled = true
                }
            }

            else -> {
                with(binding) {
                    editButton.isEnabled = false
                    deleteButton.isEnabled = true
                }
            }
        }
    }

    private fun openAddDialog() {
        val dialog = createDialog()

        with(dialog) {
            findViewById<Button>(R.id.okButton).setOnClickListener {
                val contactName = findViewById<EditText>(R.id.contactName).text.toString()
                val contactNumber = findViewById<EditText>(R.id.contactNumber).text.toString()
                viewModel.addContact(contactName, contactNumber)
                dismiss()
            }

            findViewById<Button>(R.id.cancelButton).setOnClickListener {
                dialog.cancel()
            }

            show()
        }
    }

    private fun openEditDialog() {
        val contactItem = chosenContactIds.firstOrNull()?.let { viewModel.getContact(it) }

        if (contactItem != null) {
            val dialog = createDialog()

            with(dialog) {
                val nameEditText = findViewById<EditText>(R.id.contactName)
                val numberEditText = findViewById<EditText>(R.id.contactNumber)

                nameEditText.setText(contactItem.name)
                numberEditText.setText(contactItem.phoneNumber)

                findViewById<Button>(R.id.okButton).setOnClickListener {
                    viewModel.editContact(
                        contactItem,
                        nameEditText.text.toString(),
                        numberEditText.text.toString()
                    )
                    dismiss()
                }

                findViewById<Button>(R.id.cancelButton).setOnClickListener {
                    dialog.cancel()
                }

                show()
            }
        }
    }

    private fun createDialog(): Dialog {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.contact_dialog)
        return dialog
    }

    private val dragAndDrop = object : DragAndDrop() {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.absoluteAdapterPosition
            val to = target.absoluteAdapterPosition
            Collections.swap(items, from, to)
            adapter?.items = items
            adapter?.notifyItemMoved(from, to)
            viewModel.swapContacts(from, to)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    }

}