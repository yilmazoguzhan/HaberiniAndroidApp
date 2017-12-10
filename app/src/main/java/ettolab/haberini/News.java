package ettolab.haberini;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

/**
 * Created by Code on 10.12.2017.
 */
public class News {
    private int id;
    private String date;
    private String title;
    private Bitmap image;
    private String description;

    public News(int id, String date, String title, Bitmap image, String description) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
