package com.example.parcial2_mobile2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2_mobile2.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerMain: RecyclerView
    private lateinit var adapter: Adapter
    private var charactersList = mutableListOf<Characters>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerMain = findViewById(R.id.recyclerMain)

        recyclerMain.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(charactersList)
        recyclerMain.adapter = adapter
    }

    private fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCharacters(BASE_URL)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val characters = response?.results
                    characters?.forEach {
                        charactersList.add(it)
                    }

                    adapter.notifyDataSetChanged()
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
        const val BASE_URL = "https://rickandmortyapi.com/api/character"
    }
}