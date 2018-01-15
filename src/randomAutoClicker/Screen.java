package randomAutoClicker;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
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

        btnStart = new JButton("Start (F12)");
        JTextField  tfRate = new JTextField(Integer.toString(Clicker.DEFAULT_RATE), 5);
        JTextField  tfVariance = new JTextField(Integer.toString(Clicker.DEFAULT_VAR), 5);
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
                        rate = Clicker.DEFAULT_RATE;
                        var = Clicker.DEFAULT_VAR;
                        tfRate.setText(Integer.toString(Clicker.DEFAULT_RATE));
                        tfVariance.setText(Integer.toString(Clicker.DEFAULT_VAR));
                    }
                    finally
                    {
                        tfRate.setEnabled(false);
                        tfVariance.setEnabled(false);
                        btnStart.setText("Stop (F12)");
                        c.setVals(rate, var);
                        c.start();
                    }
                }
                else
                {
                    c.stop();
                    btnStart.setText("Start (F12)");
                    tfRate.setEnabled(true);
                    tfVariance.setEnabled(true);
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
        if (e.getKeyCode() == NativeKeyEvent.VC_F12)
            btnStart.doClick();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
