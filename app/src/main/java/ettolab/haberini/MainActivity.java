package ettolab.haberini;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

  

        List<String> titleList;
        List<String> linkList;

        // rss başlıklarını tutacak list
        titleList = new ArrayList<String>();
        // rss linklerini tutacak list
        linkList = new ArrayList<String>();

        try {
            // rss url'imiz
            InputStream stream = new URL("https://www.ahaber.com.tr/rss/magazin.xml").openConnection().getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(stream,null);
            boolean insideItem = false;


            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (parser.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            titleList.add(parser.nextText());
                    } else if (parser.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            linkList.add(parser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG &&
                        parser.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }
                eventType = parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lVNews = (ListView)findViewById(R.id.ListViewNews);
        mNewsList = new ArrayList<>();
        for (String title: titleList) {
            mNewsList.add(new News(1,"11 Ekim Perşembe",title,getBitmapFromURL("http://i.hurimg.com/i/hurriyet/75/590x332/594a541ac9de3d0fb0de00fb.jpg") , "TEST İÇERİK"));
        }
        adapter = new NewsListAdapter(getApplicationContext(),mNewsList);
        lVNews.setAdapter(adapter);

        /*
        final String PUB_DATE = "pubDate";
        final String DESCRIPTION = "description";
        final String CHANNEL = "channel";
        final String LINK = "link";
        final String TITLE = "title";
        final String ITEM = "item";


        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            stream = new URL("http://www.aksam.com.tr/cache/rss.xml").openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                        } else {
                            if (name.equalsIgnoreCase(LINK)) {
                                Log.i("Attribute", "setLink");
                                //item.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "description");
                                //item.setDescription(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                Log.i("Attribute", "date");
                               // item.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "title");
                                //item.setTitle(parser.nextText().trim());
                                mNewsList.add(new News(1,"11 Ekim Perşembe",parser.nextText().trim(),getBitmapFromURL("http://i.hurimg.com/i/hurriyet/75/590x332/594a541ac9de3d0fb0de00fb.jpg") , "TEST İÇERİK"));

                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM)) {
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/


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
