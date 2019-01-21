/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * SoundHelper
 * plays specific sounds for different things in the game
 */

package com.aidenlauris.render;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundHelper {

	public static void makeSound(String name) {
		// open the sound file as a Java input stream
		File pew = new File(".\\Assets\\"+ name);
//		try {
//			AudioInputStream stream = AudioSystem.getAudioInputStream(pew);
//			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
//			Clip clip = (Clip) AudioSystem.getLine(info);
//			clip.addLineListener(new SoundListener());
//			clip.open(stream);
//			clip.start();
//			
//			
//		} catch (UnsupportedAudioFileException e) {
//
//			e.printStackTrace();
//		} catch (IOException | LineUnavailableException a) {
//
//			a.printStackTrace();
//		}
	}




static class SoundListener implements LineListener {
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
          event.getLine().close(); 
        }
      }
    
}

}

