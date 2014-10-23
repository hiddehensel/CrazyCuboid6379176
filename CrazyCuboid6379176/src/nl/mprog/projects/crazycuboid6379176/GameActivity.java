package nl.mprog.projects.crazycuboid6379176;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity
{
    boolean start = false; 
    GameView game;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        game = new GameView(this);
        setContentView(game);

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
    
    
    @Override
    protected void onResume()
    {
        super.onResume();
       // ball.resume();
        //if(start = true)
       // {
        //    Intent intent = new Intent(this, StartMenuActivity.class);
        //    startActivity(intent);
        //    finish();
        //}
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        game.pause();
        finish();
        start = false;

    }
}