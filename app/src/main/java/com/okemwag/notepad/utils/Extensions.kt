package com.okemwag.notepad.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.okemwag.notepad.data.local.Note
import com.okemwag.notepad.data.local.SyncStatus
import com.okemwag.notepad.data.remote.NoteDto
import java.text.SimpleDateFormat
import java.util.*

// Context Extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

// Fragment Extensions
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

// Date Extensions
fun Date.formatToString(pattern: String = "MMM dd, yyyy HH:mm"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun Date.isToday(): Boolean {
    val today = Calendar.getInstance()
    val dateToCheck = Calendar.getInstance().apply { time = this@isToday }

    return today.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == dateToCheck.get(Calendar.DAY_OF_YEAR)
}

fun Date.isYesterday(): Boolean {
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }
    val dateToCheck = Calendar.getInstance().apply { time = this@isYesterday }

    return yesterday.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) &&
            yesterday.get(Calendar.DAY_OF_YEAR) == dateToCheck.get(Calendar.DAY_OF_YEAR)
}

fun Date.getRelativeTimeString(): String {
    return when {
        isToday() -> "Today ${formatToString("HH:mm")}"
        isYesterday() -> "Yesterday ${formatToString("HH:mm")}"
        else -> formatToString("MMM dd, yyyy")
    }
}

// String Extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}

// View Extensions
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}
