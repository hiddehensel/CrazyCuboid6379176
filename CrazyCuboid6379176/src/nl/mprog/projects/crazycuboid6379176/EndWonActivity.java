package nl.mprog.projects.crazycuboid6379176;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class EndWonActivity extends Activity
{
    public String playerName = "";
    public double playerHighscore = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_won);
        setTime();
        storeName();

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
    
    
    
    //zet naam + highscore in database als textfield is ingevuld
    public void storeName()
    {
        EditText editText = (EditText) findViewById(R.id.editName);
        editText.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    //haal naam uit textfield (textview v)
                    playerName = v.getText().toString();
                    
                    //zet naam + highscore in database
                    SQLiteHandler database = new SQLiteHandler(EndWonActivity.this);
                    database.addHighscore(new Highscore(playerName, playerHighscore)); 
                    
                    //spel is voltooid, ga naar startactivity
                    Intent intent = new Intent(EndWonActivity.this, StartMenuActivity.class);
                    startActivity(intent);
                    finish();
                    
                    handled = true;
                }
                return handled;
            }
        });
    }
    
    

    //bereken de tijd waarin de speler het spel heeft voltooid
    public void setTime()
    {
        double timecount = getIntent().getExtras().getDouble("timecount");
        double maxtime = getIntent().getExtras().getDouble("maxtime");

        playerHighscore = maxtime-timecount;
        playerHighscore = toDecimals(playerHighscore, 2);
        
        TextView timerview = (TextView) findViewById(R.id.textTimer);
        timerview.setText("tijd: " + playerHighscore);
    }
    
    
    
    //zet double d naar aantal decimalen aantal
    public double toDecimals(double d, int aantal)  
    {   
       int daantal = (int)(d * Math.pow(10 , aantal));  
       double voltooid = ((double)daantal)/Math.pow(10 , aantal);
       
       return voltooid;  
    }
    
    
    
    //voor Terug Button
    public void toStart(View view)
    {
        Intent intent = new Intent(this, StartMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
