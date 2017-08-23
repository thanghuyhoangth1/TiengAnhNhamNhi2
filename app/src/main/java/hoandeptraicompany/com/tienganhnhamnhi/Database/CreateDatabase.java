package hoandeptraicompany.com.tienganhnhamnhi.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hoang on 8/13/2017.
 */

public class CreateDatabase extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_ENGLISHPRANK = "EnglishPrank";

    public static final String TABLE_ENGLISHPRANK_VIETNAMESE = "VIETNAMESE";
    public static final String TABLE_ENGLISH = "ENGLISH";
    public static final String TABLE_ENGLISHPRANK_ENGLISHWORKCOMPLETE = "ENGLISHWORKCOMPLETE";
    public static final String TABLE_ENGLISHPRANK_EXPLAIN = "EXPLAIN";
    public static final String TABLE_ENGLISHPRANK_ID = "ID";
    public static final String TABLE_ENGLISHPRANK_ENGLISHWORD = "ENGLISHWORD";
    private String DB_PATH;
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public CreateDatabase(Context context) {
        super(context, DATABASE_ENGLISHPRANK, null, 1);// 1? Its database Version
        this.mContext = context;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_ENGLISHPRANK);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DATABASE_ENGLISHPRANK);
        String outFileName = DB_PATH + DATABASE_ENGLISHPRANK;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DATABASE_ENGLISHPRANK;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
