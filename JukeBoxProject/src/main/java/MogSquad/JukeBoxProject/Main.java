package MogSquad.JukeBoxProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	static JList<Song> list = new JList<Song>();
	static DefaultListModel<Song> model = new DefaultListModel<Song>();
	static Scanner fileScanner;
	static JFileChooser chosenpath;
	static ArrayList<Song> songList = new ArrayList<Song>();

	// creating logger for this class
	private static final Logger logger = LogManager.getLogger(Main.class.getName()); // check log4j2.xml for details

	public static void main(String[] args) {
		
		list.setModel(model);
		new GUI();// Opens JukeBox window
		chosenpath = new JFileChooser("C:\\");
		chosenpath.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chosenpath.showOpenDialog(null);
		if (chosenpath.getSelectedFile() == null) {
			System.exit(1);
		}
		String folderpath = chosenpath.getSelectedFile().getAbsolutePath();
		File folder = new File(folderpath);
		// Checks if input if folder
		if (folder.isDirectory()) {
			// Checks if folder is empty
			if (folder.list().length > 0) {
				File[] files = folder.listFiles();
				int count = 0;
				for (File file : files) {
					if (file.isFile()) {
						// Checks if are mp3 files in the folder
						if (file.toString().endsWith(".mp3")) {
							songList.add(new Song(file.getName(), file.getPath()));
							count++;

						}
					}

				}
				if (count == 0) {
					logger.fatal("No mp3 files in folder");
					logger.info("Exiting...");
					System.exit(1);
				}
			} else {
				logger.fatal("Folder is empty");
				System.exit(1);
			}
			// Checks if input is an m3u file
		} else if (folder.toString().endsWith(".m3u")) {
			try {

				File m3ufile = new File(folderpath);
				fileScanner = new Scanner(m3ufile);
				// While loop reads the content of the m3u file
				while (fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					// if relative path it converts is to absolute only for mp3 files in the same
					// folder as the project
					File filet = new File(line);
					String result;
					// Checks if line is blank or starts with #
					if (line.startsWith("#") || line.isBlank()) {
						result = "error";
						// Checks if line is an absolute path
					} else if (new File(line).isAbsolute()) {
						result = line;
						// Gives line the folder of the project
					} else {
						result = new File(line).getAbsolutePath().toString();
					}
					if (!(new File(result).exists())) {
						result = "error";
					}

					if (line.startsWith("#") || line.isBlank()) {
						continue;
					} else if (result.endsWith(".mp3") && filet.exists()) {
						songList.add(new Song(filet.getName(), filet.toString()));
					} else {
					}
				}
			} catch (FileNotFoundException e) {
				logger.fatal("File " + folderpath + " not found.");
			} finally {
				fileScanner.close();
			}
		} else {
			System.out.println();
			logger.fatal("Please give correct input : folder that contains mp3 files or m3u file");
			logger.info("Exiting..");
			System.exit(1);
		}
		// Sorts songList
		Collections.sort(songList, new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				return Integer.valueOf(o1.getName().compareTo(o1.getName()));
			}
		});
		// Puts content of songList to Jlist
		for (int i = 0; i < songList.size(); i++) {
			model.addElement(new Song(songList.get(i).getName(), songList.get(i).getPath()));
			logger.info(songList.get(i).getName() + " is added to JList");
		}
	}
}