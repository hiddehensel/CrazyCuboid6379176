package nl.mprog.projects.crazycuboid6379176;

public class EnemyBall
{
    int enemyBallW;
    int enemyBallH;
    int enemyBallX; 
    int enemyBallY;
    double enemyBallRadius;
    
    
    
    public EnemyBall(int enemyBallW, int enemyBallH, int enemyBallX,
            int enemyBallY, double enemyBallRadius)
    {
        super();
        this.enemyBallW = enemyBallW;
        this.enemyBallH = enemyBallH;
        this.enemyBallX = enemyBallX;
        this.enemyBallY = enemyBallY;
        this.enemyBallRadius = enemyBallRadius;
    }



    //getters/setters
    public int getEnemyBallW()
    {
        return enemyBallW;
    }
    
    
    
    public void setEnemyBallW(int enemyBallW)
    {
        this.enemyBallW = enemyBallW;
    }
    
    
    
    public int getEnemyBallH()
    {
        return enemyBallH;
    }
    
    
    
    public void setEnemyBallH(int enemyBallH)
    {
        this.enemyBallH = enemyBallH;
    }
    
    
    
    public int getEnemyBallX()
    {
        return enemyBallX;
    }
    
    
    
    public void setEnemyBallX(int enemyBallX)
    {
        this.enemyBallX = enemyBallX;
    }
    
    
    
    public int getEnemyBallY()
    {
        return enemyBallY;
    }
    
    
    
    public void setEnemyBallY(int enemyBallY)
    {
        this.enemyBallY = enemyBallY;
    }
    
    
    
    public double getEnemyBallRadius()
    {
        return enemyBallRadius;
    }
    
    
    
    public void setEnemyBallRadius(double enemyBallRadius)
    {
        this.enemyBallRadius = enemyBallRadius;
    }

}
