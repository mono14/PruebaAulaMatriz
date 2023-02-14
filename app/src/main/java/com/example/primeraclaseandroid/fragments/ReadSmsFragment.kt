package com.example.primeraclaseandroid.fragments

import android.Manifest
import android.Manifest.permission_group.SMS

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.primeraclaseandroid.data.adapter.SmsAdapter
import com.example.primeraclaseandroid.data.adapter.SmsColumns
import com.example.primeraclaseandroid.databinding.FragmentReadSmsBinding

class ReadSmsFragment : Fragment() {

    private var binding: FragmentReadSmsBinding? = null
    private val binding_ get() = binding!!
    private val SMS = Uri.parse("content://sms")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReadSmsBinding.inflate(inflater, container, false)
        return binding_.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
    }

    private fun requestPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            readSMS()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_SMS),
                PERMISSIONS_REQUEST_READ_SMS
            )
        }
    }

    private fun readSMS() {
        val cursor = requireActivity().contentResolver.query(
            SMS,
            arrayOf(
                SmsColumns.ID,
                SmsColumns.ADDRESS,
                SmsColumns.DATE,
                SmsColumns.BODY
            ),
            null,
            null,
            SmsColumns.DATE + " DESC"
        )
        val adapter = cursor?.let { c ->
            SmsAdapter(requireContext(), c, true)
        }
        binding!!.lstSMS.adapter = adapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_SMS -> {
                // If request is cancelled, the result arrays are empty.
                if (
                    grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted
                    readSMS()
                } else {
                    // permission denied
                }
                return
            }
        }
    }


    companion object {
        private const val PERMISSIONS_REQUEST_READ_SMS = 2
        fun newInstance() =
            ReadSmsFragment()
    }
}
