package com.example.parcial2_mobile2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

    private lateinit var btnFilterAll: Button
    private lateinit var btnFilterAlive: Button
    private lateinit var btnFilterDead: Button
    private lateinit var btnFilterUnknown: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMain = findViewById(R.id.recyclerViewMain)
        tvTitleMain = findViewById(R.id.tvTitleMain)
        tvSubtitleMain = findViewById(R.id.tvSubtitleMain)

        btnFilterAll = findViewById(R.id.btnFilterAll)
        btnFilterAlive = findViewById(R.id.btnFilterAlive)
        btnFilterDead = findViewById(R.id.btnFilterDead)
        btnFilterUnknown = findViewById(R.id.btnFilterUnknown)

        recyclerViewMain.layoutManager = GridLayoutManager(this, 2)
        adapter = Adapter(charactersList)

        adapter.onClickListener = { character ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("CHARACTER_ID", character.id)
            }
            startActivity(intent)
        }

        recyclerViewMain.adapter = adapter

        getAllCharacters()

        btnFilterAll.setOnClickListener {
            getAllCharacters()
            tvSubtitleMain.text = "All Characters"
        }

        btnFilterAlive.setOnClickListener {
            getAliveCharacters()
            tvSubtitleMain.text = "Alive Characters"
        }

        btnFilterDead.setOnClickListener {
            getDeadCharacters()
            tvSubtitleMain.text = "Dead Characters"
        }

        btnFilterUnknown.setOnClickListener {
            getUnknownCharacters()
            tvSubtitleMain.text = "Unknown Status Characters"
        }
    }

    private fun getAllCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
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
        }
    }

    private fun getAliveCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAliveCharacters()

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
        }
    }

    private fun getDeadCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getDeadCharacters()

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
        }
    }

    private fun getUnknownCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getUnknownCharacters()

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