package ettolab.haberini;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lVNews;
    private NewsListAdapter adapter;
    private List<News> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lVNews = (ListView)findViewById(R.id.ListViewNews);
        mNewsList = new ArrayList<>();
        mNewsList.add(new News(1,"11 Ekim Perşembe","TEST Başlık",getBitmapFromURL("http://i.hurimg.com/i/hurriyet/75/590x332/594a541ac9de3d0fb0de00fb.jpg") , "TEST İÇERİK"));

        adapter = new NewsListAdapter(getApplicationContext(),mNewsList);
        lVNews.setAdapter(adapter);
    }

    public static Bitmap
    getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
