package nl.mprog.projects.crazycuboid6379176;

public class Highscore
{
    String name;
    double highscore;
     
    public Highscore(String name, double highscore)
    {
        this.name = name;
        this.highscore = highscore;
    }

    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public double getHighscore()
    {
        return this.highscore;
    }
    
    public void setHighscore(double highscore)
    {
        this.highscore = highscore;
    }
}
