package com.yogusoft.heroesunite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yogusoft.heroesunite.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView urlDisplay;
    TextView heroesResult;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public class heroQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String heroSearchResults = null;

            try {
                heroSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return heroSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            heroesResult.setText(s);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int launcherId = item.getItemId();

        if (launcherId == R.id.launcher) {
            heroesResult.setText("");
            Log.i("MainActivity", "El usuario a pulsado launcher");

            URL superhero_Url = NetworkUtils.buildUrlAllSuperHeroes();
            urlDisplay.setText(superhero_Url.toString());

            new heroQueryTask().execute(superhero_Url);
        }

        if (launcherId == R.id.clear) {
            heroesResult.setText("");
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlDisplay = (TextView) findViewById(R.id.urlDisplay);
        heroesResult = (TextView) findViewById(R.id.superheroe_searchResult);
    }

}