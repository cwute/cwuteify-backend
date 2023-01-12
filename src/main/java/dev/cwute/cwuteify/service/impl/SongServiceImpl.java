package dev.cwute.cwuteify.service.impl;

import dev.cwute.cwuteify.model.Song;
import dev.cwute.cwuteify.repository.SongRepository;
import dev.cwute.cwuteify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

  @Autowired private SongRepository songRepository;

  @Override
  public Song saveSong(Song song) {
    return songRepository.save(song);
  }

  @Override
  public ResponseEntity<Song> getSongById(Long id) {
    return ResponseEntity.of(songRepository.findById(id));
  }

  @Override
  public ResponseEntity<List<Song>> listSongs() {
    return ResponseEntity.of(Optional.of(songRepository.findAll()));
  }

  @Override
  public ResponseEntity<byte[]> getTrackById(Long id, HttpServletResponse response)
      throws IOException {
    Path path =
        Paths.get("songs-uploaded/" + id + "/" + songRepository.findById(id).get().getPathToSong());
    response.setContentType("audio/mpeg");
    response.setContentLength((int) Files.size(path));
    Files.copy(path, response.getOutputStream());
    response.flushBuffer();
    return null;
  }

  @Override
  public ResponseEntity<byte[]> getImageById(Long id, HttpServletResponse response)
      throws IOException {
    Path path =
        Paths.get("songs-uploaded/" + id + "/" + songRepository.findById(id).get().getCoverArt());
    response.setContentType("image/jpeg");
    response.setContentLength((int) Files.size(path));
    Files.copy(path, response.getOutputStream());
    response.flushBuffer();
    return null;
  }

  @Override
  public ResponseEntity<String> deleteSong(Long id) {
    if (songRepository.existsById(id)) {
      songRepository.deleteById(id);
      return ResponseEntity.ok("Song deleted");
    }
    return ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<Song>> searchSongs(String query) {
    return ResponseEntity.ok(songRepository.searchQuery(query));
  }
}
