package MusicTimer;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class TimerThread2 	extends Thread {
	private int nom;
	private JTextArea area;
	private JButton btn;
	private Clip clip = null;
	private AudioInputStream audioInputStream;
	private long et = 0;
	private int bmn = -1;
	private String input_a = ".\\Music\\";
	private String input_b = ".wav";
	private String input = "";
	private int length_timer;
	private long music_length;
	private JButton btn2;

	private static ArrayList<String> music_list = new ArrayList<String>();
	private static boolean stop_flag = false;
	private static boolean stop_flag2 = true;
	private static boolean restart_flag = false;
	private static boolean endflag = false;

	TimerThread2(int length_timer, JTextArea area, JButton btn, JButton btn2) {
		this.length_timer = length_timer * 60 * 1000;
		this.area = area;
		this.btn = btn;
		this.btn2 = btn2;

		this.nom = SetMusic(this.input_a);
	}

	private static int SetMusic(String input) {
		File dir = new File(input);
		File[] files = dir.listFiles();
		int c = files.length;

		for (int i = 0; i < c; ++i) {
			String x = files[i].getName();
			int xn = x.indexOf(".");
			x = x.substring(0, xn);
			music_list.add(x);
		}

		return c;
	}

	public void run() {
		int count = 0;
		long start = 0;
		this.music_length = 0;
		boolean music_flag = true;
		boolean loop_flag = true;
		boolean finish_flag = false;

		long start_interval = 0;
		while(loop_flag) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (restart_flag) {
				this.clip.start();
				long interval = System.currentTimeMillis() - start_interval;
				this.music_length += interval;
				stop_flag = false;
				stop_flag2 = true;
				restart_flag = false;
			}

			if (stop_flag) {
				this.clip.stop();
				if (stop_flag2) {
					start_interval = System.currentTimeMillis();
					stop_flag2 = false;
				}
			} else {
				if (music_flag) {
					finish_flag = false;

					int music_number = SelectMusic();
					this.input = (this.input_a + music_list.get(music_number) + this.input_b);
					try {
						File soundFile = new File(this.input);
						this.audioInputStream = AudioSystem.getAudioInputStream(soundFile);
						AudioFormat audioFormat = this.audioInputStream.getFormat();
						DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
						this.clip = ((Clip) AudioSystem.getLine(info));
						this.clip.open(this.audioInputStream);

						TimerThread2.this.area.append((count + 1) + " : " + music_list.get(music_number) + "\n");
						this.clip.start();

						start = System.currentTimeMillis();
						music_flag = false;

						this.music_length = (long) (this.clip.getMicrosecondLength() / 1000);
						this.et += this.music_length;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if(!music_flag) {
					long end = System.currentTimeMillis();

					if (end - start >= this.music_length) {
						try {
							Thread.sleep(3500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.clip.stop();
						this.clip.close();
						music_flag = true;
						finish_flag = true;
						count++;
					}
				}
			}

			if ((this.et >= this.length_timer)&&(finish_flag)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.btn.setEnabled(true);
				this.btn2.setEnabled(false);
				this.endflag = true;
				this.area.append("End music \n");
				break;
			}

		}
	}

	public void StopMusic() {
		stop_flag = true;
	}

	public void Restart() {
		restart_flag = true;
	}

	public boolean getEndflag() {
		return this.endflag;
	}

	private int SelectMusic() {
		int number;
		Random rnd = new Random();
		while(true) {
			number = rnd.nextInt(this.nom);
			if(number != this.bmn || this.bmn == -1) {
				break;
			}
		}
		this.bmn = number;

		return number;
	}
}
