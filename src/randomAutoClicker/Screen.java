package randomAutoClicker;

import jdk.nashorn.internal.objects.Global;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Screen extends JPanel implements NativeKeyListener
{
    private Clicker c;
    private JButton btnStart;
    public Screen(Clicker c)
    {
        super();
        this.c = c;

        try
        {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
        }
        catch (NativeHookException e)
        {
            e.printStackTrace();
        }

        btnStart = new JButton("Start (F1)");
        JTextField  tfRate = new JTextField("500");
        JTextField  tfVariance = new JTextField("100");
        JLabel      labelRate = new JLabel("Click rate (in ms): ");
        JLabel      labelVariance = new JLabel("Randomness (in ms): ");

        setLayout(new FlowLayout());

        btnStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!c.isRunning())
                {
                    int rate = 0;
                    int var = 0;

                    try
                    {
                        rate = Integer.parseInt(tfRate.getText());
                        var = Integer.parseInt(tfVariance.getText());
                    }
                    catch (NumberFormatException e2)
                    {
                        rate = 500;
                        var = 100;
                        tfRate.setText("500");
                        tfVariance.setText("100");
                    }
                    finally
                    {
                        tfRate.setEditable(false);
                        tfVariance.setEditable(false);
                        btnStart.setText("Stop (F1)");
                        c.setVals(rate, var);
                        c.start();
                    }
                }
                else
                {
                    c.stop();
                    btnStart.setText("Start (F1)");
                    tfRate.setEditable(true);
                    tfVariance.setEditable(true);
                }
            }
        });

        add(labelRate);
        add(tfRate);
        add(labelVariance);
        add(tfVariance);
        add(btnStart);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_F1)
            btnStart.doClick();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
