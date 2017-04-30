package edu.virginia.engine.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//import javafx.scene.media.MediaPlayer;

public class SoundManager implements LineListener {
	HashMap<String, Clip> clipMap = new HashMap<String, Clip>();
	boolean playCompleted;


	public void LoadSoundEffect(String id, String filename){	
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("resources" + File.separator + filename)));
			clipMap.put(id, clip);
			//clipMap.get("zelda").start();
			//clip.start();
		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		} catch(Exception ex) { 
			System.out.println("Error loading file!");
		}
	}
	public void PlaySoundEffect(String id){	
		clipMap.get(id).start();
	}
	public void LoadMusic(String id, String filename){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("resources" + File.separator + filename)));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clipMap.put(id, clip);
		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		} catch(Exception ex) { 
			System.out.println("Error loading file!");
		}
	}
	public void PlayMusic(String id){
//			clipMap.get(id).start();
	}
	public void StopMusic(String id){
			clipMap.get(id).stop();
	}
	
	public boolean contains(String id) {
		return clipMap.containsKey(id);
	}

	/**
	 * Listens to the START and STOP events of the audio line.
	 */
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();

		if (type == LineEvent.Type.START) {
			System.out.println("Playback started.");

		} else if (type == LineEvent.Type.STOP) {
			playCompleted = true;
			System.out.println("Playback completed.");
		}

	}
}
