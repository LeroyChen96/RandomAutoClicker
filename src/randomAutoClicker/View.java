package randomAutoClicker;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame
{
    private JPanel panel;
    private Clicker clicker;

    public View(Clicker c)
    {
        super();
        clicker = c;

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
                    setMinimumSize(new Dimension(Main.WIDTH, Main.HEIGHT));
                    setResizable(false);

                    panel = new JPanel();
                    panel.add(new Screen(c));

                    add(panel);
                    setContentPane(panel);
                    setVisible(true);
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
