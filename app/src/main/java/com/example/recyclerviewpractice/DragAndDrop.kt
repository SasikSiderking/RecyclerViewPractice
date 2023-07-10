package com.example.recyclerviewpractice

import androidx.recyclerview.widget.ItemTouchHelper

abstract class DragAndDrop : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    0
)