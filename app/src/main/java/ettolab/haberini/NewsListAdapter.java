package ettolab.haberini;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Code on 10.12.2017.
 */
public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<News> mNewsList;

    public NewsListAdapter(Context mContext, List<News> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_new_list, null);
        TextView tVNewDate = (TextView)v.findViewById(R.id.TVNewDate);
        TextView tVNewTitle = (TextView)v.findViewById(R.id.TVNewHead);
        ImageView tVNewImage = (ImageView) v.findViewById(R.id.TVNewImage);
        TextView tVNewDescription = (TextView)v.findViewById(R.id.TVNewDescription);

        tVNewDate.setText(mNewsList.get(position).getDate().toString());
        tVNewTitle.setText(mNewsList.get(position).getTitle());
        tVNewImage.setImageBitmap(mNewsList.get(position).getImage());
        tVNewDescription.setText(mNewsList.get(position).getDescription());

        v.setTag(mNewsList.get(position).getId());
        return v;
    }
}
