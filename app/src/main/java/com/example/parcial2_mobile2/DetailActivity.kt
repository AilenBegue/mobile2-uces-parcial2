package com.example.parcial2_mobile2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var detailImageView: ImageView
    private lateinit var detailTvName: TextView
    private lateinit var detailBtnBack: Button

    private lateinit var tvStatusContent: TextView
    private lateinit var tvSpeciesContent: TextView
    private lateinit var tvGenderContent: TextView
    private lateinit var tvLocationContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailImageView = findViewById(R.id.detailImageView)
        detailTvName = findViewById(R.id.detailTvName)
        detailBtnBack = findViewById(R.id.detailBtnBack)

        tvStatusContent = findViewById(R.id.tvStatusContent)
        tvSpeciesContent = findViewById(R.id.tvSpeciesContent)
        tvGenderContent = findViewById(R.id.tvGenderContent)
        tvLocationContent = findViewById(R.id.tvLocationContent)

        detailBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val characterId = intent.getIntExtra("CHARACTER_ID", -1)
        if (characterId != -1) {
            getCharacterDetails(characterId)
        } else {
            Toast.makeText(this, "Error: No se encontró el ID del personaje.", Toast.LENGTH_SHORT).show()
            finish()
    }
    }

    private fun getCharacterDetails(characterId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getCharacterById(characterId)

            runOnUiThread {
                if (call.isSuccessful) {
                    val character = call.body()

                    character?.let {
                        detailTvName.text = it.name
                        tvStatusContent.text = it.status
                        tvSpeciesContent.text = it.species
                        tvGenderContent.text = it.gender
                        tvLocationContent.text = it.location.name // Accede al nombre de la ubicación

                        Picasso.get().load(it.image).into(detailImageView)
                    }
                }
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

}