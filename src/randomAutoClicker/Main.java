package randomAutoClicker;

public class Main
{
    public static final int WIDTH = 640;
    public static final int HEIGHT = 75;

    public static final boolean DEBUG = true;

    public static void main(String[] args)
    {
        Clicker c = new Clicker();
        View view = new View(c);
    }
}
