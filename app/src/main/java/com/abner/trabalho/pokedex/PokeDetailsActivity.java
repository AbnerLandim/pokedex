package com.abner.trabalho.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abner.trabalho.pokedex.model.Pokemon;
import com.abner.trabalho.pokedex.utils.PokeHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokeDetailsActivity extends AppCompatActivity {

    private ImageView avatarImage;
    private TextView nameText, typesText, statsText, abilitiesText, movesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_details);

        PokeHelper pokeHelper = new PokeHelper();

        avatarImage = findViewById(R.id.avatarImageView);
        nameText = findViewById(R.id.nameTextView);
        typesText = findViewById(R.id.typesTextView);
        statsText = findViewById(R.id.statsTextView);
        abilitiesText = findViewById(R.id.abilitiesTextView);
        movesText = findViewById(R.id.movesTextView);

        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");

        if (pokemon != null) {
           String types = "";
           String stats = "";
           String abilities = "";
           String moves = "";

            for (int i = 0; i < pokemon.getTypes().size(); i++) {
                types += pokeHelper.formatType(pokemon.getTypes().get(i)) + "\n";
            }

            for (int i = 0; i < pokemon.getStats().size(); i++) {
                stats += "\uD83D\uDC49 " + pokemon.getStats().get(i) + "\n";
            }

            for (int i = 0; i < pokemon.getAbilities().size(); i++) {
                abilities += "\uD83D\uDC49 " + pokemon.getAbilities().get(i) + "\n";
            }

            for (int i = 0; i < pokemon.getMoves().size(); i++) {
                moves += "\uD83D\uDC49 " + pokemon.getMoves().get(i) + "\n";
            }


            Picasso.get().load(pokemon.getImage()).into(avatarImage);
            nameText.setText(pokemon.getName());
            typesText.setText(types);
            statsText.setText(stats);
            abilitiesText.setText(abilities);
            movesText.setText(moves);
        }
    }
}