package com.ruslangrigoriev.homework6

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult


class DialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireActivity())
            .setTitle("Delete contact?")
            .setPositiveButton("Ok") { _, _ ->
                setFragmentResult(DIALOG_REQUEST_KEY, bundleOf())
            }
            .setNegativeButton("Cancel") { _, _ ->
                dismiss()
            }
            .create()

    companion object {
        const val DIALOG_TAG = "LIST_TAG"
        const val DIALOG_REQUEST_KEY = "rateDialog"
    }

}