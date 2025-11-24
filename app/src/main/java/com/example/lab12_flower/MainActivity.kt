package com.example.lab12_flower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageToLabel)
        val btnTest = findViewById<Button>(R.id.btnTest)
        val txtOutput = findViewById<TextView>(R.id.txtOutput)

        val inputStream = assets.open("flower1.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)

        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        btnTest.setOnClickListener {
            val image = InputImage.fromBitmap(bitmap, 0)

            labeler.process(image)
                .addOnSuccessListener { labels ->
                    val sb = StringBuilder()
                    for (label in labels) {
                        sb.append("${label.text} : ${label.confidence}\n")
                    }
                    txtOutput.text = sb.toString()
                }
                .addOnFailureListener { e ->
                    txtOutput.text = "Error: ${e.localizedMessage}"
                }
        }
    }
}

