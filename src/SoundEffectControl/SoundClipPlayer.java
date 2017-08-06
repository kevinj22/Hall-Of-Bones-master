package SoundEffectControl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.*;

/**
 * Handles sound playing
 * 
 * @author alanyork
 *
 */
public class SoundClipPlayer {
	private static ArrayList<Clip> CLIPS = new ArrayList<Clip>();

	// HELPER METHODS
	// URL parsing
	private static URL parsePath(String path) {
		return SoundClipPlayer.class.getResource(path);
	}
	
	// Clip Loading
	private static Clip loadAndOpenSound(String soundFilePath) {
		Clip clip = null;
		try {
			// Open audio stream
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(parsePath(soundFilePath));
			// Get the clip from the AudioSystem
			clip = AudioSystem.getClip();
			// Add the clip to the list of clips
			SoundClipPlayer.CLIPS.add(clip);
			// Open and play the clip
			clip.open(audioStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return clip;
	}

	// PUBLIC METHODS
	/**
	 * Play the sound at the specified location
	 * 
	 * @param soundFile
	 * The path of the sound to be played
	 */
	public static void playSound(String soundFilePath) {
			Clip clip = loadAndOpenSound(soundFilePath);
			clip.start();
	}
	
	/**
	 * Play the sound at the specified location, looping it
	 * 
	 * @param soundFile
	 * The path of the sound to be played
	 */
	public static void playLoopingSound(String soundFile) {
		Clip clip = loadAndOpenSound(soundFile);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stop all currently playing sounds
	 */
	public static void shutUp() {
		// Prevent ArrayIndexOutofBoundsException
		if (CLIPS.size() > 0) {
			Clip[] removeables = new Clip[CLIPS.size()];
			int index = 0;
			
			// Stop the sound and add it to the list of clips to be removed
			for (Clip x : CLIPS) {
				x.stop();
				removeables[index] = x;
				index += 1;
			}
			// Remove the clips from the list of clips
			for (Clip x : removeables){
				CLIPS.remove(x);
			}
		}
	}
}