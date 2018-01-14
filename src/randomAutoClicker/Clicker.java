package randomAutoClicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Random;
import javax.swing.Timer;

public class Clicker
{
    private int rate = 500;     // Arbitrary default values.
    private int variance = 100;
    private int actual;
    private boolean running = false;
    private Robot bot;
    private Timer timer;
    private Random random;

    public Clicker()
    {
        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Point pos = MouseInfo.getPointerInfo().getLocation();
                bot.mouseMove(pos.x, pos.y);
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                if (Main.DEBUG)
                    System.out.println("Clicked with " + actual + "ms delay");
                updateTimer();
            }
        };

        try
        {
            bot = new Robot();
            timer = new Timer(rate, al);
            random = new Random();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }

    public void setVals(int rate, int variance)
    {
        this.rate = rate;
        this.variance = variance;
    }

    public void start()
    {
        running = true;
        updateTimer();
        timer.start();
    }

    public void stop()
    {
        running = false;
        timer.stop();
    }

    private void updateTimer()
    {
        actual = rate + random.nextInt(variance * 2) - variance;
        timer.setDelay(actual);
    }

    public boolean isRunning()
    {
        return running;
    }
}
