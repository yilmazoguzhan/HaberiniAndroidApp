package ettolab.haberini;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Code on 9.12.2017.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String DATABASE_NAME = "RssDB";
    public final static String DATABASE_PATH = "/data/data/com.ettolab.haberini/databases/";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    //Uygulama telefona yüklendiğinde veritabanının olup olmadığını kontrol eder.
    private boolean CheckDataBase()
    {
        boolean checkDB = false;
        String myPath = DATABASE_PATH + DATABASE_NAME;
        File dBFile = new File(myPath);
        checkDB = dBFile.exists();
        return checkDB;
    }

    //Bu metod istenildiği taktirde asset klasörü içerisindeki veritabanını telefona yazar.
    private void CopyDataBase() throws IOException
    {
        InputStream myInputStream = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutputStream = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2024];
        int myLength;
        while ((myLength = myInputStream.read(mBuffer)) > 0)
        {
            myOutputStream.write(mBuffer, 0, myLength);
        }
        myOutputStream.flush();
        myOutputStream.close();
        myInputStream.close();
    }

    //Bu metod telefon üzerinde veritabanı mevcut ise siler.
    private void DeleteDataBase()
    {
        File dBFile = new File(DATABASE_PATH + DATABASE_NAME);
        if(dBFile.exists())
        {
            dBFile.delete();
        }
    }

    //Veritabanını açar.
    public void OpenDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    //Veritabanını kapatır.
    public synchronized void CloseDataBase()throws SQLException
    {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            DeleteDataBase();
        }
    }
}

