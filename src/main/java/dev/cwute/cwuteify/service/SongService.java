package dev.cwute.cwuteify.service;

import dev.cwute.cwuteify.model.Song;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface SongService {

  public Song saveSong(Song song);

  public ResponseEntity<Song> getSongById(Long id);

  ResponseEntity<List<Song>> listSongs();

  ResponseEntity<byte[]> getTrackById(Long id, HttpServletResponse response) throws IOException;

  ResponseEntity<byte[]> getImageById(Long id, HttpServletResponse response) throws IOException;

  ResponseEntity<String> deleteSong(Long id);

  ResponseEntity<List<Song>> searchSongs(String query);
}
