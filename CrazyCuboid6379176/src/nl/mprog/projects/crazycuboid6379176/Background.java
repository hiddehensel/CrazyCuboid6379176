package nl.mprog.projects.crazycuboid6379176;

public class Background
{
    private int backgroundx, backgroundy, speedx, speedy;
    
    public Background (int x, int y)
    {
        backgroundx = x;
        backgroundy = y;
        speedx = 0;
        speedx = 0;
     }
    
    public int getBackgroundx()
    {
        return backgroundx;
    }

    public void setBackgroundx(int backgroundx)
    {
        this.backgroundx = backgroundx;
    }

    public int getBackgroundy()
    {
        return backgroundy;
    }

    public void setBackgroundy(int backgroundy)
    {
        this.backgroundy = backgroundy;
    }

    public int getSpeedx()
    {
        return speedx;
    }

    public void setSpeedx(int speedx)
    {
        this.speedx = speedx;
    }

    public int getSpeedy()
    {
        return speedy;
    }

    public void setSpeedy(int speedy)
    {
        this.speedy = speedy;
    }

    public void updateBackground()
    {
        backgroundx += speedx;
        backgroundy += speedy;
        
        //if (backgroundx <= -maxwidth)
        //{
        //    backgroundx += maxwidth*2;
        //}
     }
}



