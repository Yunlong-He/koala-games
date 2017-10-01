package com.koala.media;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;


public class SoundPlayer implements Runnable{
    Thread mThread;
    Sequencer sequencer;
    Object currentSound;

	public SoundPlayer(Object soundFile) {
//		open();
//		loadSound(soundFile);
//
	}


	private void open() {
		try {
			sequencer = MidiSystem.getSequencer();
		} catch (Exception ex) { ex.printStackTrace(); return; }
	}

	private void close() {
		currentSound = null;
		if (sequencer != null) {
			sequencer.close();
		}
	}

	private boolean loadSound(Object object) {
		if (object instanceof URL) {
			try {
				currentSound = AudioSystem.getAudioInputStream((URL) object);
			} catch(Exception e) {
				try {
					currentSound = MidiSystem.getSequence((URL) object);
				} catch (InvalidMidiDataException imde) {
					System.out.println("Unsupported audio file.");
					return false;
				} catch (Exception ex) {
					ex.printStackTrace();
					currentSound = null;
					return false;
				}
			}
		} else if (object instanceof File) {
			try {
				currentSound = AudioSystem.getAudioInputStream((File) object);
			} catch(Exception e1) {
				try {
					FileInputStream is = new FileInputStream((File) object);
					currentSound = new BufferedInputStream(is, 1024);
				} catch (Exception e3) {
					e3.printStackTrace();
					currentSound = null;
					return false;
				}
			}
		}

		if (currentSound instanceof AudioInputStream) {
			try {
				AudioInputStream stream = (AudioInputStream) currentSound;
				AudioFormat format = stream.getFormat();

				/**
				* we can't yet open the device for ALAW/ULAW playback,
				* convert ALAW/ULAW to PCM
				*/
				if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
						(format.getEncoding() == AudioFormat.Encoding.ALAW)){
					AudioFormat tmp = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED,
							format.getSampleRate(),
							format.getSampleSizeInBits() * 2,
							format.getChannels(),
							format.getFrameSize() * 2,
							format.getFrameRate(),
							true);
					stream = AudioSystem.getAudioInputStream(tmp, stream);
					format = tmp;
				}
				DataLine.Info info = new DataLine.Info(
						Clip.class,
						stream.getFormat(),
						((int) stream.getFrameLength() *
						format.getFrameSize()));

				Clip clip = (Clip) AudioSystem.getLine(info);
				clip.open(stream);
				currentSound = clip;
			} catch (Exception ex) {
				ex.printStackTrace();
				currentSound = null;
				return false;
			}
		} else if (currentSound instanceof Sequence || currentSound instanceof BufferedInputStream) {
			try {
				sequencer.open();
				if (currentSound instanceof Sequence) {
					sequencer.setSequence((Sequence) currentSound);
				} else {
					sequencer.setSequence((BufferedInputStream) currentSound);
				}
			} catch (InvalidMidiDataException imde) {
				System.out.println("Unsupported audio file.");
				currentSound = null;
				return false;
			} catch (Exception ex) {
				ex.printStackTrace();
				currentSound = null;
				return false;
			}
		}

		return true;
	}

	private void playSound() {
		if (currentSound instanceof Sequence || currentSound instanceof BufferedInputStream && mThread != null) {
			sequencer.start();
			while (mThread != null) {
				try { mThread.sleep(99); } catch (Exception e) {break;}
			}
			sequencer.stop();
			sequencer.close();
		} else if (currentSound instanceof Clip && mThread != null) {
			Clip clip = (Clip) currentSound;
			clip.start();
			try { mThread.sleep(99); } catch (Exception e) { }
			while (clip.isActive() && mThread != null) {
				try { mThread.sleep(99); } catch (Exception e) {break;}
			}
			clip.stop();
		}
	}

	public void play() {
//		mThread = new Thread(this);
//		mThread.setName("Juke");
//		mThread.start();

		try {
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(new File("resources/go/putstone.wav"));
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		}
		catch (Exception e) {
			//whatevers
		}
	}

	private void stop() {
		if (mThread != null) {
			mThread.interrupt();
		}
		mThread = null;
	}

	public void run() {
		playSound();
		stop();



	}
}
