package nl.mprog.projects.crazycuboid6379176;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StartMenuActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
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
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    //voor Start Button
    public void toGame(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
    
    //Voor Highscores Button
    public void toHighscores(View view)
    {
        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
        finish();
    }
    
    
    //Voor Exit Button
    public void toExit(View view)
    {
        finish();
    }
}
