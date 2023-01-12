package dev.cwute.cwuteify.service.impl;

import dev.cwute.cwuteify.model.Playlist;
import dev.cwute.cwuteify.repository.PlaylistRepository;
import dev.cwute.cwuteify.repository.SongRepository;
import dev.cwute.cwuteify.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired private PlaylistRepository playlistRepository;
  @Autowired SongRepository songRepository;

  @Override
  public void savePlaylist(Playlist playlist) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    playlist.setCreatedBy(authentication.getName());
    playlist.setCreated(LocalDate.now());
    playlist.setLastEdited(LocalDate.now());

    playlistRepository.save(playlist);
  }

  @Override
  public ResponseEntity<Playlist> getPlaylistById(Long id) {
    return ResponseEntity.of(playlistRepository.findById(id));
  }

  @Override
  public ResponseEntity<String> addSongToPlaylist(Long playlistId, Long songId) {
    Playlist playlist = playlistRepository.findById(playlistId).get();
    if (playlist.getSongs().contains(songRepository.findById(songId).get())) {
      return ResponseEntity.badRequest().body("Song already in playlist");
    }
    playlist.addSong(songRepository.findById(songId).get());
    playlist.setLastEdited(LocalDate.now());
    playlistRepository.save(playlist);
    return ResponseEntity.ok().body("Song added to playlist");
  }

  @Override
  public void removeSongFromPlaylist(Long playlistId, Long songId) {
    Playlist playlist = playlistRepository.findById(playlistId).get();
    if (!playlist.getSongs().contains(songRepository.findById(songId).get())) {
      return;
    }
    playlist.setSongs(
        playlist.getSongs().stream()
            .filter(song -> !song.getId().equals(songId))
            .collect(Collectors.toList()));
    playlist.setLastEdited(LocalDate.now());
    playlistRepository.save(playlist);
  }

  @Override
  public ResponseEntity<List<Playlist>> listAllPlaylists() {
    return ResponseEntity.of(
        Optional.of(
            (playlistRepository.findAll().stream()
                .filter(
                    playlist ->
                        playlist
                            .getCreatedBy()
                            .equals(
                                SecurityContextHolder.getContext().getAuthentication().getName()))
                .collect(Collectors.toList()))));
  }

  @Override
  public ResponseEntity<String> deletePlaylist(Long id) {
    if (playlistRepository.findById(id).isPresent()) {
      if (playlistRepository
          .findById(id)
          .get()
          .getCreatedBy()
          .equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
        playlistRepository.deleteById(id);
        return ResponseEntity.ok().body("Playlist deleted");
      }
      return ResponseEntity.status(403).body("You do not have permission to delete this playlist");
    }
    return ResponseEntity.notFound().build();
  }
}
