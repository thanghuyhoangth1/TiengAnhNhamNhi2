package hoandeptraicompany.com.tienganhnhamnhi.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hoandeptraicompany.com.tienganhnhamnhi.Database.CreateDatabase;
import hoandeptraicompany.com.tienganhnhamnhi.ObjectClass.EnglishClass;

/**
 * Created by Hoang on 8/13/2017.
 */

public class QueryEnglisTable {
    SQLiteDatabase database;
    CreateDatabase createDatabase;

    public QueryEnglisTable(Context context) {
        createDatabase = new CreateDatabase(context);
        try {
            createDatabase.createDataBase();
            createDatabase.openDataBase();
            database = createDatabase.getWritableDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<EnglishClass> getData() {
        List<EnglishClass> listEnglish = new ArrayList<>();
        String rawQuery = "SELECT * FROM ENGLISH";
        Cursor cursor = database.rawQuery(rawQuery, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String english = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_ENGLISHPRANK_ENGLISHWORD));
            int id = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_ENGLISHPRANK_ID));
            String englishComplete = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_ENGLISHPRANK_ENGLISHWORKCOMPLETE));
            String vietnamese = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_ENGLISHPRANK_VIETNAMESE));
            String explain = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_ENGLISHPRANK_EXPLAIN));
            EnglishClass englishClass = new EnglishClass(id, english, englishComplete, vietnamese, explain);
            listEnglish.add(englishClass);
            cursor.moveToNext();

        }

        return listEnglish;

    }
}
