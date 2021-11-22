package com.abner.trabalho.pokedex.connection;

import android.util.Log;

import com.abner.trabalho.pokedex.PokeListActivity;
import com.abner.trabalho.pokedex.model.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PokeAPI {

    private ArrayList<Pokemon> pokemons = new ArrayList<>();

    public void getPokemons(String name, int paginationSize, int offset, OnPokeAPIListener listener) {

        PokeConnectionAsyncTask asyncTask = new PokeConnectionAsyncTask(new PokeConnectionAsyncTask.OnRequestListener() {
            @Override
            public void onRequestFinished(JSONObject object) {
                try {
                    pokemons.clear();
                    if (object != null) {
                        if (object.has("results")) {
                            JSONArray results = object.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject pokeObject = results.getJSONObject(i);
                                int id = i+1;
                                String name = pokeObject.getString("name");
                                pokemons.add(new Pokemon(id, name, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png" ));
                            }
                        } else {
                            int id = object.getInt("id");
                            String name = object.getString("name");
                            pokemons.add(new Pokemon(id, name, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png" ));
                        }
                    }
                    listener.onFinish(pokemons);
                } catch (Exception e) {
                    listener.onFinish(null);
                    Log.v("APP_POKEDEX ====>", "Something went wrong getting Pokemons");
                }
            }
        });
//        asyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit="+PAGINATION_SIZE+"&offset="+offset, "GET");
        if (name.isEmpty()) asyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit=" + paginationSize + "&offset=" + offset, "GET");
        else asyncTask.execute("https://pokeapi.co/api/v2/pokemon/" + name, "GET");
    }

    public void getPokemonDetails(int id, OnPokeDetailsAPIListener listener) {

        PokeConnectionAsyncTask asyncTask = new PokeConnectionAsyncTask(new PokeConnectionAsyncTask.OnRequestListener() {

            @Override
            public void onRequestFinished(JSONObject object) {

                try {
                    String name = object.getString("name");
                    JSONArray types = object.getJSONArray("types");
                    JSONArray stats = object.getJSONArray("stats");
                    JSONArray abilities = object.getJSONArray("abilities");
                    JSONArray moves = object.getJSONArray("moves");
                    ArrayList<String> typesArray = new ArrayList<>();
                    ArrayList<String> statsArray = new ArrayList<>();
                    ArrayList<String> abilitiesArray = new ArrayList<>();
                    ArrayList<String> movesArray = new ArrayList<>();

                    String avatar = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png";

                    // Parse types
                    for (int j = 0; j < types.length(); j++) {
                        typesArray.add(types.getJSONObject(j).getJSONObject("type").getString("name"));
                    }

                    // Parse stats
                    for (int j = 0; j < stats.length(); j++) {
                        statsArray.add(stats.getJSONObject(j).getJSONObject("stat").getString("name") + ": " + stats.getJSONObject(j).getString("base_stat"));
                    }

                    // Parse abilities
                    for (int j = 0; j < abilities.length(); j++) {
                        abilitiesArray.add(abilities.getJSONObject(j).getJSONObject("ability").getString("name"));
                    }

                    // Parse moves
                    for (int j = 0; j < moves.length(); j++) {
                        movesArray.add(moves.getJSONObject(j).getJSONObject("move").getString("name"));
                    }

                    Pokemon pokemon = new Pokemon(id, name, avatar);
                    pokemon.setTypes(typesArray);
                    pokemon.setStats(statsArray);
                    pokemon.setAbilities(abilitiesArray);
                    pokemon.setMoves(movesArray);

                    listener.onFinish(pokemon);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        asyncTask.execute("https://pokeapi.co/api/v2/pokemon/"+id+"/", "GET");
    }

    public interface OnPokeDetailsAPIListener {
        void onFinish(Pokemon pokemon);
    }

    public interface OnPokeAPIListener {
        void onFinish(ArrayList<Pokemon> pokemons);
    }
}
