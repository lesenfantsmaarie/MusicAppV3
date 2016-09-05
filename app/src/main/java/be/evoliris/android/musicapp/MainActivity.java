package be.evoliris.android.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import be.evoliris.android.musicapp.db.dao.AlbumDAO;
import be.evoliris.android.musicapp.model.Album;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Album a = new Album();
        a.setTitle("The Old Album");
        Calendar c = Calendar.getInstance();
        c.set(1965, Calendar.NOVEMBER, 17);
        a.setOutDate(c.getTime());
        a.setImageUrl("https://upload.wikimedia.org/wikipedia/en/8/80/TheOffspringSmashalbumcover.jpg");
        a.setGenre("Pop-Punk");
        a.setFavorite(false);
        a.setRating(0.5f);

        AlbumDAO albumDAO = new AlbumDAO(MainActivity.this);
        albumDAO.openWritable();
        if (albumDAO.create(a)) {
            Toast.makeText(MainActivity.this, "Album créé !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Erreur :'(", Toast.LENGTH_SHORT).show();
        }

        Album a2 = albumDAO.readById(a.getId());
        if (a2 != null) {
            Toast.makeText(MainActivity.this, "Album : " + a2.getTitle(), Toast.LENGTH_SHORT).show();
            Log.i("Album", a2.getTitle() + " " + a2.getOutDate() + " " + a2.getRating());
        }

        List<Album> albums = albumDAO.readAll();
        for (Album album : albums) {
            Log.i("Albums", album.getTitle());
        }

        albumDAO.close();
    }
}
