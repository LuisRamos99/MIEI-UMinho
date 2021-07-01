package Main.ui;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.player.events.MediaPlayerEventType;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Player {
    public static JButton jb = new JButton("PLAY/PAUSE");


        public Player(String file) {
            JFrame f = new JFrame();
            f.setLocation(100, 100);

            f.setVisible(true);
            f.add(jb, BorderLayout.SOUTH);
            Canvas c = new Canvas();
            c.setBackground(Color.black);
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());

            f.setSize(600, 600);
            f.setLocationRelativeTo(null);
            p.add(c);
            f.add(p);


            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC");
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

            MediaPlayerFactory mpf = new MediaPlayerFactory();
            EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
            emp.setVideoSurface(mpf.newVideoSurface(c));
            emp.isPlayable();
            emp.menuDown();
            emp.prepareMedia(file);
            emp.play();

            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    emp.pause();
                }
            });

            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                   emp.stop();
                   e.getWindow().dispose();
                }
            });
        }

}
