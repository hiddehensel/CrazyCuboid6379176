package nl.mprog.projects.crazycuboid6379176;

public class Cuboid
{
    int cuboidX;
    int cuboidY; 
    int cuboidW;
    int cuboidH;
    
    
    public Cuboid(int cuboidW, int cuboidH, int cuboidX, int cuboidY)
    {
        super();
        this.cuboidX = cuboidX;
        this.cuboidY = cuboidY;
        this.cuboidW = cuboidW;
        this.cuboidH = cuboidH;
    }
    
    
    
    public int getCuboidX()
    {
        return cuboidX;
    }

    
    
    public void setCuboidX(int cuboidX)
    {
        this.cuboidX = cuboidX;
    }

    
    
    public int getCuboidY()
    {
        return cuboidY;
    }

    
    
    public void setCuboidY(int cuboidY)
    {
        this.cuboidY = cuboidY;
    }
    
    

    public int getCuboidW()
    {
        return cuboidW;
    }
    
    

    public void setCuboidW(int cuboidW)
    {
        this.cuboidW = cuboidW;
    }

    
    
    public int getCuboidH()
    {
        return cuboidH;
    }

    
    
    public void setCuboidH(int cuboidH)
    {
        this.cuboidH = cuboidH;
    }
}
