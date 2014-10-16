package nl.mprog.projects.crazycuboid6379176;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends Activity
{
    public double timecount = 0;
    public int maxtime = 30000;
    public boolean endtime = false;
    public int touchx = 0;
    public int touchy = 0;
    public int originx = 0;
    public int originy = 0;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //start timer
        initTimer();
        setOrigin();
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
    
    
    
    //maak countdowntimer aan en zet deze in een textview
    public void initTimer()
    {
        new CountDownTimer(maxtime, 100)
        {

            public void onTick(long milisecondes)
            {
                TextView timerview = (TextView) findViewById(R.id.textTimer);
                //geef per seconde weer
                timerview.setText("tijd: " + milisecondes / 1000);
                timecount = (milisecondes );
            }

            public void onFinish()
            {
                if(!endtime)
                {
                    TextView timerview = (TextView) findViewById(R.id.textTimer);
                    timerview.setText("einde");
                    
                    //als tijd is verlopen, ga naar EndLostActivity
                    Intent intent = new Intent(GameActivity.this, EndLostActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
         }.start();
    }
    
    public void setOrigin()
    {
        double scaleDisplayMetrics = 0.9;
        int reqWidth = (int) (getResources().getDisplayMetrics().widthPixels * scaleDisplayMetrics);
        int reqHeight = (int) (getResources().getDisplayMetrics().heightPixels * scaleDisplayMetrics);
        originx = reqWidth/2;
        originy = reqHeight/2;
        System.out.println("X"+touchx);
        System.out.println("Y" +touchy); 
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        //System.out.println("x="+event.getX()+" y="+event.getY());
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
        {
            touchx = (int)event.getX();
            touchy = (int)event.getY();
            System.out.println("setting target to "+touchx+","+touchy);
            
        }
        else
            return super.onTouchEvent(event);

        return true;
    }
    
    
    //---------Debug--------//
    
    
    //Voor (debug) Lost Button
    public void toLost(View view)
    {
        Intent intent = new Intent(this, EndLostActivity.class);
        startActivity(intent);
        finish();
    }
    
    
    
    //Voor (debug) Won Button
    public void toWon(View view)
    {
        double maxtimeInSec = maxtime / 1000;
        double timecountInSec = timecount / 1000;
        endtime = true;
        //geef timer mee aan endwon om highscore op te slaan
        Intent intent = new Intent(this, EndWonActivity.class);
        intent.putExtra("timecount", timecountInSec);
        intent.putExtra("maxtime", maxtimeInSec);
        startActivity(intent);
        finish();
    }
    
    
    
    //Voor (debug) Start Button
    public void toStart(View view)
    {
        Intent intent = new Intent(this, StartMenuActivity.class);
        startActivity(intent);
        finish();
    }
    //---------Debug--------//
}