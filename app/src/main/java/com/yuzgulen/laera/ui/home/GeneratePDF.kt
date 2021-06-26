package com.yuzgulen.laera.ui.home

import android.os.Environment
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yuzgulen.laera.utils.App
import java.io.File
import java.io.IOException


class GeneratePDF {
    fun pdfGeneration(tag: String) {
        val targetPdf = Environment.getExternalStorageDirectory().getPath() + "/$tag"
        val filePath = File(targetPdf)
        try {
            val storage = Firebase.storage
            val storageRef = storage.reference
            storageRef.child("lessons/$tag/$tag.pdf").getFile(filePath)
                .addOnSuccessListener {
                    Toast.makeText(App.instance.applicationContext,
                        "File downloaded at: $filePath.pdf", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                        Toast.makeText(App.instance.applicationContext,
                            "Failed to download PDF", Toast.LENGTH_LONG).show()
                }

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                App.instance.applicationContext, "Something wrong: " + e.toString(),
                Toast.LENGTH_LONG
            ).show()
        }

    }
}