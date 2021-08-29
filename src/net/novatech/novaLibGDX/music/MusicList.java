package net.novatech.novaLibGDX.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

public class MusicList {
	public Music[] tracks;

	MusicList(Music[] tracks){
		this.tracks = tracks;
	}

	MusicList(String[] tracknames){
		tracks = new Music[tracknames.length];
		
		for(int i = 0; i < tracks.length; i ++){
			tracks[i] = MusicManager.get(tracknames[i]);
		}
	}

	public Music select(Music previous) {
		Music select = previous;
		if (!(tracks.length == 1 && tracks[0] == previous)) {
			while (select == previous) {
				select = tracks[MathUtils.random(0, tracks.length - 1)];
			}
		}
		return select;
	}
}