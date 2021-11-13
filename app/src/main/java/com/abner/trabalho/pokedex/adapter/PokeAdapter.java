package com.abner.trabalho.pokedex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abner.trabalho.pokedex.R;
import com.abner.trabalho.pokedex.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.PokeViewHolder> {

    private ArrayList<Pokemon> pokemons;
    private OnPokemonClickListener listener;

    public PokeAdapter(ArrayList<Pokemon> pokemons, OnPokemonClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View layout = layoutInflater.inflate(R.layout.view_item_pokemon, parent, false);
        return new PokeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        TextView textView = holder.itemView.findViewById(R.id.text_view_pokemon_name);
        textView.setText(pokemon.getName());
        ImageView imageView = holder.itemView.findViewById(R.id.image_view_pokemon);
        Picasso.get().load(pokemon.getImage()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class PokeViewHolder extends RecyclerView.ViewHolder {

        public PokeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Pokemon pokemon = pokemons.get(position);
                    listener.onPokemonClick(pokemon);
                }
            });
        }
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }
}
