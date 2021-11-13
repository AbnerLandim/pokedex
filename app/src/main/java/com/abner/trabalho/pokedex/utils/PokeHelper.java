package com.abner.trabalho.pokedex.utils;

public class PokeHelper {

    public String formatType(String type) {
        String emoji = "";
        switch(type) {
            case "normal":
                emoji = "⚪";
                break;
            case "fighting":
                emoji = "⚔";
                break;
            case "flying":
                emoji = "\uD83E\uDD85";
                break;
            case "poison":
                emoji = "☠";
                break;
            case "ground":
                emoji = "\uD83D\uDFE4";
                break;
            case "rock":
                emoji = "\uD83E\uDEA8";
                break;
            case "bug":
                emoji = "\uD83E\uDEB2";
                break;
            case "ghost":
                emoji = "\uD83D\uDC7B";
                break;
            case "steel":
                emoji = "⚙";
                break;
            case "fire":
                emoji = "\uD83D\uDD25";
                break;
            case "water":
                emoji = "\uD83D\uDCA7";
                break;
            case "grass":
                emoji = "\uD83C\uDF31";
                break;
            case "electric":
                emoji = "⚡";
                break;
            case "psychic":
                emoji = "\uD83E\uDDE0";
                break;
            case "ice":
                emoji = "\uD83E\uDDCA";
                break;
            case "dragon":
                emoji = "\uD83D\uDC32";
                break;
            case "dark":
                emoji = "\uD83C\uDF18";
                break;
            case "fairy":
                emoji = "\uD83E\uDE84";
                break;
            default:
                break;
        }
        return emoji + " " + type;
    }
}
