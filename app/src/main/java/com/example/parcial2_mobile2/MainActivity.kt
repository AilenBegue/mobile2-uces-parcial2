package com.example.parcial2_mobile2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var tvTitleMain: TextView
    private lateinit var tvSubtitleMain: TextView
    private lateinit var adapter: Adapter
    private var charactersList = mutableListOf<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMain = findViewById(R.id.recyclerViewMain)
        tvTitleMain = findViewById(R.id.tvTitleMain)
        tvSubtitleMain = findViewById(R.id.tvSubtitleMain)

        recyclerViewMain.layoutManager = GridLayoutManager(this, 2)
        adapter = Adapter(charactersList)
        recyclerViewMain.adapter = adapter

        getCharacters()
    }

    private fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(ApiService::class.java).getAllCharacters()

                runOnUiThread {
                    if (call.isSuccessful) {
                        val response = call.body()
                        val characters = response?.results

                        charactersList.clear()

                        characters?.forEach { character ->
                            charactersList.add(character)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
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