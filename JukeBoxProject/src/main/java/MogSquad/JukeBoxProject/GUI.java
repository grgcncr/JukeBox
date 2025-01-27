package MogSquad.JukeBoxProject;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerEvent;
import gr.hua.dit.oop2.musicplayer.Player.Status;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
import gr.hua.dit.oop2.musicplayer.PlayerListener;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GUI {
	// creating logger for this class
	private static final Logger logger = LogManager.getLogger(GUI.class.getName());
	JButton playButton;
	JButton pauseButton;
	JButton closeButton;
	JButton resetButton;
	JButton loopButton;
	JButton suffleButton;
	JButton orderButton;
	JButton dukeButton;
	JButton nextButton;
	ImageIcon duke = new ImageIcon("java-duke-guitar.png");
	JLabel label;
	Player player = PlayerFactory.getPlayer();
	private int strategy = 0;
	private Player.Status previousStatus = null;
	private Player.Status currentStatus = null;
	Song song;
	private int currentsong;
	private int nextsong;
	InputStream songtoplay = null;
	private int closed = 1;
	private int closedStart = 1;
	Random random = new Random();

	// Constractor contains JukeBox window stracture
	public GUI() {
		player.addPlayerListener(new PlayerListener() {

			@Override
			public void statusUpdated(PlayerEvent arg0) {

				logger.info("Status changed to " + arg0.getStatus());
				currentStatus = arg0.getStatus();
				// Checks to see if the mp3 file has finished playing
				if (currentStatus == Status.IDLE && previousStatus == Status.PLAYING) {
					label.setText("SELECT A SONG TO PLAY");
					closed = 1;
				}
				previousStatus = currentStatus;
			}
		});
		// Code if Jlist item is selected
		Main.list.getSelectionModel().addListSelectionListener(e -> {
			closedStart = 0;
			song = Main.list.getSelectedValue();
			label.setText(song.getName() + " IS PLAYNG");
			// For loop for finding index number of the song playing and the next song to
			// play if strategy is order
			for (int i = 0; i < Main.model.getSize(); i++) {

				if (Main.list.getSelectedValue().equals(Main.model.get(i))) {
					nextsong = i + 1;
					currentsong = i;
				}
			}
			// Plays song
			try {
				songtoplay = new FileInputStream(song.getPath());
				player.stop();
				player.startPlaying(songtoplay);
				closed = 0;
			} catch (PlayerException e1) {
				logger.fatal("There is something wrong with the player");
				logger.info("Exiting...");
				System.exit(1);
			} catch (FileNotFoundException e1) {
				logger.fatal("File not found");
				logger.info("Exiting...");
				System.exit(1);
			}
		});

		// Components design
		JSplitPane splitPane = new JSplitPane();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(new JScrollPane(Main.list));
		splitPane.setRightComponent(panel);
		label = new JLabel("SELECT A SONG TO PLAY");
		label.setBounds(170, 240, 500, 200);
		playButton = new JButton("RESUME");
		pauseButton = new JButton("PAUSE");
		closeButton = new JButton("CLOSE");
		resetButton = new JButton("PLAY");
		dukeButton = new JButton();
		nextButton = new JButton("NEXT");
		nextButton.setBackground(Color.LIGHT_GRAY);
		dukeButton.setBackground(Color.LIGHT_GRAY);
		dukeButton.setBounds(110, 30, 327, 280);
		playButton.setBounds(25, 360, 100, 70);
		pauseButton.setBounds(125, 360, 100, 70);
		closeButton.setBounds(325, 360, 100, 70);
		resetButton.setBounds(225, 360, 100, 70);
		nextButton.setBounds(425, 360, 100, 70);
		playButton.setBackground(Color.LIGHT_GRAY);
		pauseButton.setBackground(Color.LIGHT_GRAY);
		closeButton.setBackground(Color.LIGHT_GRAY);
		resetButton.setBackground(Color.LIGHT_GRAY);
		closeButton.setFocusable(false);
		resetButton.setFocusable(false);
		pauseButton.setFocusable(false);
		playButton.setFocusable(false);
		nextButton.setFocusable(false);
		dukeButton.setIcon(duke);

		suffleButton = new JButton("SHUFFLE");
		loopButton = new JButton("LOOP");
		orderButton = new JButton("ORDER");

		orderButton.setBounds(140, 430, 90, 50);
		suffleButton.setBounds(230, 430, 90, 50);
		loopButton.setBounds(320, 430, 90, 50);

		orderButton.setBackground(Color.LIGHT_GRAY);
		suffleButton.setBackground(Color.LIGHT_GRAY);
		loopButton.setBackground(Color.LIGHT_GRAY);

		loopButton.setFocusable(false);
		orderButton.setFocusable(false);
		suffleButton.setFocusable(false);
		dukeButton.setFocusable(false);

		// Buttons' utilization

		// Pauses song playing
		pauseButton.addActionListener(e -> {
			if (closed == 0) {
				player.pause();
				label.setText(song.getName() + " IS PAUSED");
				logger.info("Status changed to " + player.getStatus());
			}
		});
		// Resumes paused song
		playButton.addActionListener(e -> {
			if (closed == 0) {
				player.resume();
				label.setText(song.getName() + " IS PLAYING");
				logger.info("Status changed to " + player.getStatus());
			}
		});
		// Closes song playing
		closeButton.addActionListener(e -> {
			if (closedStart == 0) {
				closed = 1;
				player.stop();
				label.setText("SELECT A SONG TO PLAY");
				logger.info("Status changed to " + player.getStatus());
			}
		});
		// Plays selected song from the start
		resetButton.addActionListener(e -> {
			if (closedStart == 0) {
				Song song = Main.list.getSelectedValue();
				label.setText(song.getName() + " IS PLAYNG");
				// For loop for finding index number of the song playing and the next song to
				// play if strategy is order
				for (int i = 0; i < Main.model.getSize(); i++) {

					if (Main.list.getSelectedValue().equals(Main.model.get(i))) {
						nextsong = i + 1;
						currentsong = i;
					}
				}
				try {
					songtoplay = new FileInputStream(song.getPath());
					player.stop();
					player.startPlaying(songtoplay);
					closed = 0;
				} catch (PlayerException e1) {
					logger.fatal("There is something wrong with the player");
					logger.info("Exiting...");
					System.exit(1);
				} catch (FileNotFoundException e1) {
					logger.fatal("File not found");
					logger.info("Exiting...");
					System.exit(1);
				}
			}
		});
		// Plays next song according to strategy
		nextButton.addActionListener(e -> {
			if (closedStart == 0) {
				if (strategy == 0) {
					if (nextsong < Main.model.getSize()) {
						song = Main.model.get(nextsong);
						label.setText(song.getName() + " IS PLAYNG");
						try {
							songtoplay = new FileInputStream(song.getPath());
							player.stop();
							player.startPlaying(songtoplay);
							closed = 0;
							currentsong = nextsong;
						} catch (FileNotFoundException e1) {
							logger.fatal("File not found");
							logger.info("Exiting...");
							System.exit(1);
						} catch (PlayerException e1) {
							logger.fatal("There is something wrong with the player");
							logger.info("Exiting...");
							System.exit(1);
						}
						nextsong++;
					} else {
						logger.info("End of list");
					}
				} else if (strategy == 1) {
					currentsong = random.nextInt(Main.model.getSize());
					song = Main.model.get(currentsong);
					label.setText(song.getName() + " IS PLAYNG");
					// For loop for finding index number of the next song to play if strategy is
					// order
					for (int i = 0; i < Main.model.getSize(); i++) {

						if (Main.model.get(currentsong).equals(Main.model.get(i))) {
							nextsong = i + 1;
						}
					}
					try {
						songtoplay = new FileInputStream(song.getPath());
						player.stop();
						player.startPlaying(songtoplay);
						closed = 0;
					} catch (FileNotFoundException e1) {
						logger.fatal("File not found");
						logger.info("Exiting...");
						System.exit(1);
					} catch (PlayerException e1) {
						logger.fatal("There is something wrong with the player");
						logger.info("Exiting...");
						System.exit(1);
					}
				} else if (strategy == 2) {

					song = Main.model.get(currentsong);
					label.setText(song.getName() + " IS PLAYNG");
					// For loop for finding index number of the next song to play if strategy is
					// order
					for (int i = 0; i < Main.model.getSize(); i++) {

						if (Main.model.get(currentsong).equals(Main.model.get(i))) {
							nextsong = i + 1;
						}
					}
					try {
						songtoplay = new FileInputStream(song.getPath());
						player.stop();
						player.startPlaying(songtoplay);
						closed = 0;
					} catch (PlayerException e1) {
						logger.fatal("There is something wrong with the player");
						logger.info("Exiting...");
						System.exit(1);
					} catch (FileNotFoundException e1) {
						logger.fatal("File not found");
						logger.info("Exiting...");
						System.exit(1);
					}
				}
			}
		});
		// Strategy buttons
		orderButton.addActionListener(e -> {
			if (strategy == 0) {
			} else {
				strategy = 0;
				logger.info("Strategy changed to Order");
			}
		});
		suffleButton.addActionListener(e -> {
			if (strategy == 1) {
			} else {
				strategy = 1;
				logger.info("Strategy changed to Shuffle");
			}
		});
		loopButton.addActionListener(e -> {
			if (strategy == 2) {
			} else {
				strategy = 2;
				logger.info("Strategy changed to Loop");
			}
		});
		// Duke's dialog button
		dukeButton.addActionListener(e -> {
			int dukedialog = random.nextInt(3);
			if (dukedialog == 0) {
				logger.info("HELLO!!!");
			} else if (dukedialog == 1) {
				logger.info("LET'S ROCK!!!!");
			} else {
				logger.info("YAHHOOOO!!!!!!");
			}
		});
		panel.setBackground(Color.gray);
		panel.setLayout(null);
		panel.add(playButton);
		panel.add(pauseButton);
		panel.add(closeButton);
		panel.add(resetButton);
		panel.add(orderButton);
		panel.add(suffleButton);
		panel.add(loopButton);
		panel.add(resetButton);
		panel.add(nextButton);
		panel.add(dukeButton);
		panel.add(label);
		frame.add(splitPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JukeBox");
		frame.pack();
		frame.setBounds(350, 150, 830, 540);
		frame.setVisible(true);
	}

}
