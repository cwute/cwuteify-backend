package dev.cwute.cwuteify.service;

import dev.cwute.cwuteify.model.Playlist;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlaylistService {

  void savePlaylist(Playlist playlist);

  ResponseEntity<Playlist> getPlaylistById(Long id);

  ResponseEntity<String> addSongToPlaylist(Long playlistId, Long songId);

  void removeSongFromPlaylist(Long playlistId, Long songId);

  ResponseEntity<List<Playlist>> listAllPlaylists();

  ResponseEntity<String> deletePlaylist(Long id);
}
