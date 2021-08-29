package net.novatech.novaLibGDX.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class MusicManager {
	public static float DEFAULT_VOLUME = 10;
	private static Array<Music> music = new Array<>();
	private static ObjectMap<String, Music> map = new ObjectMap<>();
	private static ObjectMap<String, MusicList> tracks = new ObjectMap<>();
	private static Music playing;
	private static MusicList current;
	private static float volume = 1f;
	private static float fadeTime = 100f;
	private static float fading = 0f;

	public static void load(String... names) {

		for (String s : names) {
			String name = s.split("\\.")[0];
			music.add(Gdx.audio.newMusic(Gdx.files.internal("music/" + s)));
			map.put(name, music.peek());

			music.peek().setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(Music other) {
					if (!MathUtils.isEqual(fading,0)) {
						return;
					}
					Music music = current.select(other);
					playing = music;
					updateVolume();
					music.play();
				}
			});
		}
	}

	public static void setVolume(float vol) {
		volume = vol;
	}

	public static Music getPlaying() {
		return playing;
	}

	public static void updateVolume() {
		if (playing == null) {
			return;
		}
		float vol = DEFAULT_VOLUME / 10f * volume;
		playing.setVolume(vol);
	}

	public static float baseVolume() {
		return DEFAULT_VOLUME / 10f * volume;
	}

	public static void shuffleAll() {
		Music[] out = new Music[music.size];
		for (int i = 0; i < music.size; i++) {
			out[i] = music.get(i);
		}
		
		MusicList list = new MusicList(out);
		playList(list);
	}

	public static Music get(String name) {
		if (!map.containsKey(name))
			throw new IllegalArgumentException("The music \"" + name + "\" does not exist!");

		return map.get(name);
	}

	public static void setFadeTime(float fadetime) {
		fadeTime = fadetime;
	}

	public static void playList(String name) {
		playList(tracks.get(name));
	}

	private static void playList(MusicList list) {
		if (current == list) {
			return;
		}
		
		Music select = list.select(playing);
		current = list;

		if (current == null || playing == null) {
			playing = select;
			updateVolume();
			select.play();
		} else {
			fading = 0f;
			select.play();

			//TO DO: correct music player
		}
	}

	public static void createTracks(String name, String... names) {
		MusicList list = new MusicList(names);
		tracks.put(name, list);
	}

	private static MusicList getTracks(String name) {
		if (!tracks.containsKey(name)) {
			throw new IllegalArgumentException("The tracks \"" + name + "\" do not exist!");
		}
		
		return tracks.get(name);
	}

	static void dispose() {
		music = new Array<>();
		tracks = new ObjectMap<>();
		playing = null;
		current = null;
		volume = 1f;
		fading = 100f;

		for (Music music : map.values()) {
			music.dispose();
		}
	}
}