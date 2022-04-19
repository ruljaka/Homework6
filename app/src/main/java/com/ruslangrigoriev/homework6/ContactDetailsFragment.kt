package com.ruslangrigoriev.homework6

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ruslangrigoriev.homework6.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {

    private var _binding: FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!
    private var contract: FragmentContract? = null
    private val contact: Contact
        get() = requireArguments().getSerializable(DETAILS_ARG) as Contact

    companion object {
        const val FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG"
        private const val DETAILS_ARG = "DETAILS_ARG"

        @JvmStatic
        fun newInstance(contact: Contact) =
            ContactDetailsFragment().apply {
                arguments = bundleOf(DETAILS_ARG to contact)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentContract) {
            contract = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContactDetailsBinding.bind(view)
        with(binding) {
            firstNameEt.setText(contact.firstName)
            lastNameEt.setText(contact.lastName)
            numberEt.setText(contact.number)
            detailsImageView.load(contact.imageUrl) {
                crossfade(200)
                transformations(RoundedCornersTransformation(15F))
            }
            saveBtn.setOnClickListener {
                contact.firstName = firstNameEt.text.toString()
                contact.lastName = lastNameEt.text.toString()
                contact.number = numberEt.text.toString()
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}