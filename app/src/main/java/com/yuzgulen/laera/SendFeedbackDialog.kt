package com.yuzgulen.laera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yuzgulen.laera.domain.usecases.SendFeedback
import kotlinx.android.synthetic.main.fragment_send_feedback.*
import java.io.ByteArrayOutputStream

class SendFeedbackDialog : DialogFragment() {

    private val pickImage = 100
    private var imageUri: Uri? = null
    private var loaded: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_send_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load_image.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        send_feedback.setOnClickListener {
            if (loaded) {
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val image = stream.toByteArray()
                SendFeedback().execute(feedback_text.text.toString(), isAnonymous.isChecked, image)
            } else {
                SendFeedback().execute(feedback_text.text.toString(), isAnonymous.isChecked, null)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            loaded = true
        }
    }
}