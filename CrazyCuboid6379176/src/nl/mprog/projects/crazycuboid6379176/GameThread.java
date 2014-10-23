package nl.mprog.projects.crazycuboid6379176;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
class GameThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean run = false;
    
    public GameThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean run)
    {
        this.run = run;
    }

    public SurfaceHolder getSurfaceHolder()
    {
        return surfaceHolder;
    }
    
    @Override
    public void run()
    {
        Canvas c;
        while (run)
        {
            c = null;
            try
            {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder)
                {
                    gameView.onDraw(c);
                }
            } finally
            {
                if (c != null)
                {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
