package com.yogusoft.heroesunite.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String SUPERHERO_BASE_URL = "https://akabab.github.io/superhero-api/api";
    final static String ALL_SUPERHEROES_URL = "/all.json";

    static int id;
    static String SEARCH_SUPERHERO_URS = "/id/" + id + ".json";

    public static URL buildUrlAllSuperHeroes() {
        Uri builtUri = Uri.parse(SUPERHERO_BASE_URL).buildUpon().appendPath(ALL_SUPERHEROES_URL).build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e("NetworkUtils", "Error, en la generación de la url");
            e.printStackTrace();
        }

        return url;
    }

    // Este metodo no se utiliza
    public static URL buildUrlSearchSuperHero(int heroId) {
        id = heroId;
        Uri builtUri = Uri.parse(SUPERHERO_BASE_URL).buildUpon().appendPath(SEARCH_SUPERHERO_URS).build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e("NetworkUtils", "Error, en la generación de la url");
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        Scanner read = new Scanner(in);
        read.useDelimiter("\\A");

        try {
            boolean hasInput = read.hasNext();

            if (hasInput) {
                return read.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
