package com.abner.trabalho.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.abner.trabalho.pokedex.adapter.PokeAdapter;
import com.abner.trabalho.pokedex.connection.PokeAPI;
import com.abner.trabalho.pokedex.model.Pokemon;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PokeListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPokeList;
    private TextInputEditText searchEditText;
    private ImageButton searchButton;
    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private PokeAdapter listAdapter;
    private int PAGINATION_SIZE;
    private int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PAGINATION_SIZE = 12;
        offset = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_list);

        PokeAPI pokeApi = new PokeAPI();

        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.search_button);

        recyclerViewPokeList = findViewById(R.id.recycler_poke_list);
        recyclerViewPokeList.setLayoutManager(new GridLayoutManager(this, 2));

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    pokeApi.getPokemons(searchEditText.getText().toString(), new PokeAPI.OnPokeAPIListener() {
                        @Override
                        public void onFinish(ArrayList<Pokemon> pokemons) {
                            updateSearch(pokemons);
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        listAdapter = new PokeAdapter(pokemons, new PokeAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(Pokemon pokemon) {
                pokeApi.getPokemonDetails(pokemon.getId(), new PokeAPI.OnPokeDetailsAPIListener() {
                    @Override
                    public void onFinish(Pokemon pokemon) {
                        Intent intent = new Intent(PokeListActivity.this, PokeDetailsActivity.class);
                        intent.putExtra("pokemon", pokemon);
                        Log.v("APP_POKEDEX =====>", pokemon.getName() + " selected");
                        startActivity(intent);
                    }
                });
            }
        });

        recyclerViewPokeList.setAdapter(listAdapter);

        pokeApi.getPokemons("", new PokeAPI.OnPokeAPIListener() {
            @Override
            public void onFinish(ArrayList<Pokemon> pokemons) {
                if (pokemons != null) {
                    PokeListActivity.this.pokemons.addAll(pokemons);
                    recyclerViewPokeList.getAdapter().notifyDataSetChanged();
                }
            }
        });

//        recyclerViewPokeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
////                    offset += PAGINATION_SIZE;
//                    // asyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit="+PAGINATION_SIZE+"&offset="+offset, "GET");
//                    // recyclerViewPokeList.getAdapter().notifyDataSetChanged();
//                    Log.v("APP_POKEDEX", String.valueOf(offset));
//                }
//            }
//        });
    }

    private void updateSearch(ArrayList<Pokemon> pokemons) {
        if (pokemons != null) {
            View view = PokeListActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            PokeListActivity.this.pokemons.clear();
            PokeListActivity.this.pokemons.addAll(pokemons);
            recyclerViewPokeList.getAdapter().notifyDataSetChanged();
        } else {
            Toast notFound = Toast.makeText(getBaseContext(), "Oops! No Pokemons named " + searchEditText.getText().toString() + " were found.", Toast.LENGTH_SHORT);
            notFound.show();
        }
    }
}