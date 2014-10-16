package nl.mprog.projects.crazycuboid6379176;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HighscoresActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        getHighscores();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.start_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    //haalt highscores op uit database en geeft de top 5 mee aan setTableText
    public void getHighscores()
    {
        int count = 0;
        SQLiteHandler database = new SQLiteHandler(HighscoresActivity.this);
        List<Highscore> highscores = database.getAllHighscores();       
        
        for (Highscore h : highscores) 
        {
            if(count < 5)
            {
                setTableText(h.getName(), h.getHighscore(), (count+1));
            }
            else if(count >= 5)
            {
                break;
            }
            count += 1;
        }
    }
    
    
    //zet de top5 verkregen highscore waardes in de juiste textviews
    public void setTableText(String name, double highscore, int count)
    {
        //maak id's
        String nameTextId = "textNaam" + count;
        String scoreTextId = "textScore" + count;

        int nameTextID = getResources().getIdentifier(nameTextId, "id", HighscoresActivity.this.getPackageName());
        int scoreTextID = getResources().getIdentifier(scoreTextId, "id", HighscoresActivity.this.getPackageName());
        
        //zet de naam + score in juiste textviews
        TextView nameTextView = (TextView) findViewById(nameTextID);
        nameTextView.setText(name);
        TextView scoreTextView = (TextView) findViewById(scoreTextID);
        scoreTextView.setText(String.valueOf(highscore));
    }
    
    
    //voor de Terug Button 
    public void toStart(View view)
    {
        Intent intent = new Intent(this, StartMenuActivity.class);
        startActivity(intent);
        finish();
    }
}