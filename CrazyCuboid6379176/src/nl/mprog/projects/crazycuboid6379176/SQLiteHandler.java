package nl.mprog.projects.crazycuboid6379176;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "highscoresDatabase";
    private static final String TABLE_HIGHSCORES = "highscores";
    private static final String KEY_NAME = "name";
    private static final String KEY_HIGHSCORE = "score";
 
    
    
    public SQLiteHandler(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
 
    
    
    //maak de tabel
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //CREATE TABLE highscores(name TEXT,score DOUBLE)
        String CREATE_HIGHSCORE_TABLE = "CREATE TABLE " 
                + TABLE_HIGHSCORES + "("
                + KEY_NAME + " TEXT,"
                + KEY_HIGHSCORE + " DOUBLE" + ")";
        
        database.execSQL(CREATE_HIGHSCORE_TABLE);
    }
 
    
    
    //voor nieuwe database, upgrade
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        //query om tabel te verwijderen 
        //DROP TABLE IF EXISTS highscores
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);
        
        //maak nieuwe tabel
        onCreate(database);
    }
    


    //voeg nieuwe highscore toe aan tabel
    void addHighscore(Highscore highscore)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        //pak naam en highscore uit class highscore
        values.put(KEY_NAME, highscore.getName());
        values.put(KEY_HIGHSCORE, highscore.getHighscore());
 
        //zet in tabel
        database.insert(TABLE_HIGHSCORES, null, values);
        database.close();
    }
 

    
    //pak hele gesorteerde highscore tabel
    public List<Highscore> getAllHighscores()
    {
        //maak arraylist van classes
        List<Highscore> highscoreList = new ArrayList<Highscore>();
        
        //query waarbij je alles pakt desc gesorteerd
        //SELECT  * FROM highscores ORDER BY score DESC
        String sortQuery = "SELECT * FROM " + TABLE_HIGHSCORES + " ORDER BY " + KEY_HIGHSCORE + " DESC";
        SQLiteDatabase database = this.getWritableDatabase();
        
        Cursor cursor = database.rawQuery(sortQuery, null);
        //pak elke entry
        if (cursor.moveToFirst())
        {
            do
            {
                //voor elke entry, initialiseer highscore classe en zet de naam/highscore
                Highscore highscore = new Highscore("init", 0);
                highscore.setName(cursor.getString(0));
                highscore.setHighscore(cursor.getDouble(1));
                
                //zet entry in list
                highscoreList.add(highscore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return highscoreList;
    }
}
