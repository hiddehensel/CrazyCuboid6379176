package nl.mprog.projects.crazycuboid6379176;

import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



@SuppressLint("DrawAllocation")
class GameView extends SurfaceView implements SurfaceHolder.Callback 
{
    GameThread thread;
    int screenW, screenH; 
    int cuboidX, cuboidY; 
    int cuboidW, cuboidH;
    int enemyBallW, enemyBallH;
    int enemyBallX, enemyBallY;
    int enemyCuboidW, enemyCuboidH;
    int enemyCuboidX, enemyCuboidY;
    int backgroundW, backgroundH;
    int touchX, touchY;
    int angle;
    int backgroundScroll;
    int backgroundScrollSpeed; //Background scroll speed.
    Bitmap cuboid, background, enemyBall, enemyCuboid;
    double enemyBallRadius;
    Context contextSaved;
    long timerNow, timer;
    Paint timerPaint = new Paint();
    EnemyBall enemyBallObject;
    EnemyCuboid enemyCuboidObject;
    Cuboid cuboidObject;
    
    
    public GameView(Context context)
    {
        super(context);
        this.contextSaved = context;
        
        setVariables();

        //zet thread
        getHolder().addCallback(this);
        setFocusable(true);
    }

    
    
    
    public void setVariables()
    {
        //grootte van balk/cuboid instellen
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        
        //bitmaps images inladen
        cuboid = BitmapFactory.decodeResource(getResources(),R.drawable.cuboid, options);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        
        options.inSampleSize = 2;
        enemyBall = BitmapFactory.decodeResource(getResources(),R.drawable.enemy_ball, options);
        enemyCuboid = BitmapFactory.decodeResource(getResources(),R.drawable.enemy_cuboid);
        
        // vars
        cuboidW = cuboid.getWidth();
        cuboidH = cuboid.getHeight();
        enemyBallW = enemyBall.getWidth();
        enemyBallH = enemyBall.getHeight();
        enemyBallRadius = enemyBall.getWidth()/2;
        enemyCuboidW = enemyCuboid.getWidth();
        enemyCuboidH = enemyCuboid.getHeight();
        
        //timer
        timer=System.currentTimeMillis();

        angle = 0; 
        backgroundScroll = 0;  
        backgroundScrollSpeed = 5;

        screenW = (int) (getResources().getDisplayMetrics().widthPixels);
        screenH = (int) (getResources().getDisplayMetrics().heightPixels);
        
        //zet startpositie balk
        cuboidX = (int) (screenW /2) - (cuboidW / 2) ; 
        cuboidY = (int) (screenH /2) - (cuboidH / 2); 

        //zet startpositie enemies
        enemyBallX = (int) (screenW /2) - (cuboidW / 2);
        enemyBallY = 0;

        enemyCuboidX = (int) (screenW /2) -100;
        enemyCuboidY = -600;
        
        //maak objecten aan
        cuboidObject = new Cuboid(cuboidW,cuboidH,cuboidX,cuboidY);
        enemyBallObject = new EnemyBall(enemyBallW,enemyBallH,enemyBallX,enemyBallY, enemyBallRadius);
        enemyCuboidObject = new EnemyCuboid(enemyCuboidW,enemyCuboidH,enemyCuboidX,enemyCuboidY);
        
        timerPaint.setTextSize(100);
    }
    
    
    
    
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        background = Bitmap.createScaledBitmap(background, w, h, true);
        backgroundW = background.getWidth();
        backgroundH = background.getHeight();
    }
    
    
    
    
    @Override
    public synchronized boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            //scherm aanraken
            case MotionEvent.ACTION_DOWN:
            {
                double length = Math.sqrt((( event.getX() -cuboidX  ) * ( event.getX() -cuboidX  )) + (( event.getY() -cuboidY  ) * ( event.getY() -cuboidY  )));
                touchX = (int) ((( event.getX() -(cuboidX +(cuboidW/2)) )/length) *30);
                touchY = (int) (( (event.getY() -(cuboidY +(cuboidH/2))   )/length) *30);
                break;
            }
            //bewegen over scherm
            case MotionEvent.ACTION_MOVE:
            {
                double length = Math.sqrt((( event.getX() -cuboidX  ) * ( event.getX() -cuboidX  )) + (( event.getY() -cuboidY  ) * ( event.getY() -cuboidY  )));
                touchX = (int) ((( event.getX() -(cuboidX +(cuboidW/2)) )/length) *30);
                touchY = (int) (( (event.getY() -(cuboidY +(cuboidH/2))   )/length) *30);
                cuboidX = cuboidX + touchX;
                cuboidY = cuboidY + touchY;
                break;
            }
            //scherm loslaten
            case MotionEvent.ACTION_UP:
                touchX = 0;
                touchY = 0;
                break;
            }
        return true;
    }

    
    
    
    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //bewegende achtergrond (maak vierkanten)
        Rect fromRect1 = new Rect(0, 0, backgroundW , backgroundH- backgroundScroll);
        Rect toRect1 = new Rect(0, backgroundScroll, backgroundW, backgroundH);

        Rect fromRect2 = new Rect(0 , backgroundH - backgroundScroll, backgroundW, backgroundH);
        Rect toRect2 = new Rect(0, 0, backgroundW, backgroundScroll);
        
        canvas.drawBitmap(background, fromRect2, toRect2, null);
        canvas.drawBitmap(background, fromRect1, toRect1, null);

        //volgende scroll
        if ( (backgroundScroll += backgroundScrollSpeed) >= backgroundH)
        {
            backgroundScroll = 0;
        }

        angle += 1;
        if (angle++ >180)
           angle =0;
        
        double middelpuntX = cuboidX + (cuboidW/2);
        double middelpuntY = cuboidY + (cuboidH/2);
        
        //volgorde topLX, topLY, topRX, topRY, botRX, botRY, botLX, botLY
        double []cuboidPointArrayRotated = new double[8];
        cuboidPointArrayRotated[0] =  cuboidX - middelpuntX;
        cuboidPointArrayRotated[1] =  (cuboidY - middelpuntY);
        cuboidPointArrayRotated[2] =  cuboidX + (cuboidW) - middelpuntX;
        cuboidPointArrayRotated[3] =  (cuboidY - middelpuntY);
        cuboidPointArrayRotated[4] =  cuboidX + (cuboidW) - middelpuntX;
        cuboidPointArrayRotated[5] =  (cuboidY + (cuboidH) - middelpuntY);
        cuboidPointArrayRotated[6] = cuboidX - middelpuntX;
        cuboidPointArrayRotated[7] = (cuboidY + (cuboidH) - middelpuntY);
        
        //y' = y*cos(a) - x*sin(a)
        //x' = y*sin(a) + x*cos(a)
        //met x en y afstand tussen center of rotation
        double a = -angle * Math.PI / 180;
        double []cuboidPointArrayRotated2 = new double[8];
        cuboidPointArrayRotated2[0] = (double) ((cuboidPointArrayRotated[1] * Math.sin(a)) + (cuboidPointArrayRotated[0] * Math.cos(a)));
        cuboidPointArrayRotated2[1] = (double) ((cuboidPointArrayRotated[1] * Math.cos(a)) - (cuboidPointArrayRotated[0] * Math.sin(a)));
        cuboidPointArrayRotated2[2] = (double) ((cuboidPointArrayRotated[3] * Math.sin(a)) + (cuboidPointArrayRotated[2] * Math.cos(a)));
        cuboidPointArrayRotated2[3] = (double) ((cuboidPointArrayRotated[3] * Math.cos(a)) - (cuboidPointArrayRotated[2] * Math.sin(a)));
        cuboidPointArrayRotated2[4] = (double) ((cuboidPointArrayRotated[5] * Math.sin(a)) + (cuboidPointArrayRotated[4] * Math.cos(a)));
        cuboidPointArrayRotated2[5] = (double) ((cuboidPointArrayRotated[5] * Math.cos(a)) - (cuboidPointArrayRotated[4] * Math.sin(a)));
        cuboidPointArrayRotated2[6] = (double) ((cuboidPointArrayRotated[7] * Math.sin(a)) + (cuboidPointArrayRotated[6] * Math.cos(a)));
        cuboidPointArrayRotated2[7] = (double) ((cuboidPointArrayRotated[7] * Math.cos(a)) - (cuboidPointArrayRotated[6] * Math.sin(a)));
        
        double []cuboidPointArrayRotated3 = new double[8];
        cuboidPointArrayRotated3[0] = cuboidPointArrayRotated2[0] + middelpuntX;
        cuboidPointArrayRotated3[1] = cuboidPointArrayRotated2[1] + middelpuntY;
        cuboidPointArrayRotated3[2] = cuboidPointArrayRotated2[2] + middelpuntX;
        cuboidPointArrayRotated3[3] = cuboidPointArrayRotated2[3] + middelpuntY;
        cuboidPointArrayRotated3[4] = cuboidPointArrayRotated2[4] + middelpuntX;
        cuboidPointArrayRotated3[5] = cuboidPointArrayRotated2[5] + middelpuntY;
        cuboidPointArrayRotated3[6] = cuboidPointArrayRotated2[6] + middelpuntX;
        cuboidPointArrayRotated3[7] = cuboidPointArrayRotated2[7] + middelpuntY;
        
        cuboidX = cuboidX + touchX;
        cuboidY = cuboidY + touchY;

        if (enemyBallY > screenH)
        {
            enemyBallY = -30;
            enemyBallX = randInt(0, (screenW-enemyBallW));
        }
        enemyBallY += 5;
            
        if (enemyCuboidY > screenH)
        {
            enemyCuboidY = -30;
            enemyCuboidX = randInt(0,screenW-enemyCuboidW);
        }
        enemyCuboidY += 5;
        
        //draai en translate balk met matrix
        Matrix matrix = new Matrix();
        matrix.postRotate(angle, (cuboidW / 2), (cuboidH / 2)); 
        matrix.postTranslate(cuboidX, cuboidY); 
        //draw cuboid met rotatiematrix
        canvas.drawBitmap(cuboid, matrix, null); 
        
        //draw vijanden
        canvas.drawBitmap(enemyCuboid, enemyCuboidX, enemyCuboidY, null);
        canvas.drawBitmap(enemyBall, enemyBallX, enemyBallY, null);
        
        for(int i = 0; i < 8; i++)
        {
           if(cuboidPointArrayRotated3[i] < 0)
            {
                toWon();
            }
           //voor Y
           if (i % 2 != 0 || i == 0)
           {
               //y mag niet groter zijn dan screenH
               if(cuboidPointArrayRotated3[i] > (screenH-200))
               {
                   toWon();
               }
           }
           else
           {
               //x mag niet groter zijn dan screenW
               if(cuboidPointArrayRotated3[i] > screenW)
               {
                   toWon();
               }
           }
        }
        
        //coordinaat met x en y zit in bal als: 
        //(x-center_x)^2+(y-center_y)^2 <radius^2 
        double []checkEnemyPunt = new double [5];
        checkEnemyPunt[0] = Math.pow((cuboidPointArrayRotated3[0]- (enemyBallX + (enemyBallW / 2))),2) + Math.pow((cuboidPointArrayRotated3[1]- (enemyBallY+ (enemyBallH / 2))),2); 
        checkEnemyPunt[1] = Math.pow((cuboidPointArrayRotated3[2]- (enemyBallX + (enemyBallW / 2))),2) + Math.pow((cuboidPointArrayRotated3[3]- (enemyBallY+ (enemyBallH / 2))),2); 
        checkEnemyPunt[2] = Math.pow((cuboidPointArrayRotated3[4]- (enemyBallX + (enemyBallW / 2))),2) + Math.pow((cuboidPointArrayRotated3[5]- (enemyBallY+ (enemyBallH / 2))),2); 
        checkEnemyPunt[3] = Math.pow((cuboidPointArrayRotated3[6]- (enemyBallX + (enemyBallW / 2))),2) + Math.pow((cuboidPointArrayRotated3[7]- (enemyBallY+ (enemyBallH / 2))),2);
        checkEnemyPunt[4] = Math.pow(((cuboidX+ (cuboidW/2)) - (enemyBallX + (enemyBallW / 2))),2) + Math.pow((cuboidY + (cuboidH/2 )- (enemyBallY+ (enemyBallH / 2))),2);
        double enemyRadiusCheck = Math.pow(enemyBallRadius,2);
        
        //check of cuboid bal raakt
        if(     checkEnemyPunt[0] < enemyRadiusCheck || 
                checkEnemyPunt[1] < enemyRadiusCheck || 
                checkEnemyPunt[2] < enemyRadiusCheck || 
                checkEnemyPunt[3] < enemyRadiusCheck ||
                checkEnemyPunt[4] < enemyRadiusCheck)
        {
            System.out.println("enemyBall");
            toWon();
            
        }
        
        //check of cuboid enemycuboid raakt
        if( (cuboidPointArrayRotated3[0] > enemyCuboidX &&  cuboidPointArrayRotated3[0] < (enemyCuboidX + enemyCuboidW)) && (cuboidPointArrayRotated3[1] > enemyCuboidY &&  cuboidPointArrayRotated3[1] < (enemyCuboidY + enemyCuboidH)) || 
                (cuboidPointArrayRotated3[2] > enemyCuboidX &&  cuboidPointArrayRotated3[2] < (enemyCuboidX + enemyCuboidW)) && (cuboidPointArrayRotated3[3] > enemyCuboidY &&  cuboidPointArrayRotated3[3] < (enemyCuboidY + enemyCuboidH)) ||
                (cuboidPointArrayRotated3[4] > enemyCuboidX &&  cuboidPointArrayRotated3[4] < (enemyCuboidX + enemyCuboidW)) && (cuboidPointArrayRotated3[5] > enemyCuboidY &&  cuboidPointArrayRotated3[5] < (enemyCuboidY + enemyCuboidH)) ||
                (cuboidPointArrayRotated3[6] > enemyCuboidX &&  cuboidPointArrayRotated3[6] < (enemyCuboidX + enemyCuboidW)) &&(cuboidPointArrayRotated3[7] > enemyCuboidY &&  cuboidPointArrayRotated3[7] < (enemyCuboidY + enemyCuboidH)) ||
                (middelpuntX > enemyCuboidX &&  middelpuntX < (enemyCuboidX + enemyCuboidW)) && (middelpuntY > enemyCuboidY &&  middelpuntY < (enemyCuboidY + enemyCuboidH)))
        { 
            toWon();
            System.out.println("enemyCuboid");
        
        }
        
         //timer
         timerNow = (System.currentTimeMillis() - timer);
         long timerNowSeconds = (System.currentTimeMillis() - timer) / 1000;
         long timerNowDecimals =  (System.currentTimeMillis() - timer)- (timerNowSeconds*1000);
         canvas.drawText(timerNowSeconds+ "." + timerNowDecimals, ((screenW/2)-100), 150, timerPaint);
    }
    
    
    
    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    
    
    public void toWon()
    {
        //vibrate
        Vibrator vibe = (Vibrator) contextSaved.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(50);
        
        //denk aan thread
        thread.interrupt();
        
        //geef timer now mee aan endwon om highscore op te slaan
        Intent intent = new Intent(contextSaved, EndWonActivity.class);
        intent.putExtra("timecount", timerNow);
        
        contextSaved.startActivity(intent);
        ((Activity) contextSaved).finish();
    }
    
    
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    
    
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        resume();
    }

    
    
    public void resume()
    {
        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    
    
    
    public void pause()
    {
        boolean retry = true;
        thread.setRunning(false);
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            } catch (InterruptedException e)
            {

            }
        }
    }

    
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        pause();
    }
}
