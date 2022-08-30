package com.example.randoo.model

import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var status: Boolean,
    val task_text: String
): Parcelable


