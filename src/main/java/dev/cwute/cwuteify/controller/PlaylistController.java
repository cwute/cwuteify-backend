package dev.cwute.cwuteify.controller;

import dev.cwute.cwuteify.model.Playlist;
import dev.cwute.cwuteify.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
  @Autowired private PlaylistService playlistService;

  @PostMapping("/create")
  public ResponseEntity<String> createPlaylist(@RequestBody Playlist playlist) {
    playlist.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    playlistService.savePlaylist(playlist);
    return ResponseEntity.ok().body("Playlist created");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
    return playlistService.getPlaylistById(id);
  }

  @GetMapping("/{idPlaylist}/add/{idSong}")
  public ResponseEntity<String> addSongToPlaylist(
      @PathVariable Long idPlaylist, @PathVariable Long idSong) {
    if (!SecurityContextHolder.getContext()
        .getAuthentication()
        .getName()
        .equals(playlistService.getPlaylistById(idPlaylist).getBody().getCreatedBy())) {
      return ResponseEntity.status(403).body("You are not the owner of this playlist");
    }
    return playlistService.addSongToPlaylist(idPlaylist, idSong);
  }

  @GetMapping("/{idPlaylist}/remove/{idSong}")
  public ResponseEntity<String> removeSongFromPlaylist(
      @PathVariable Long idPlaylist, @PathVariable Long idSong) {
    if (!SecurityContextHolder.getContext()
        .getAuthentication()
        .getName()
        .equals(playlistService.getPlaylistById(idPlaylist).getBody().getCreatedBy())) {
      return ResponseEntity.badRequest().body("You are not the owner of this playlist");
    }
    playlistService.removeSongFromPlaylist(idPlaylist, idSong);
    return ResponseEntity.ok("Removed song from playlist");
  }

  @GetMapping("/list")
  public ResponseEntity<List<Playlist>> getAllPlaylists() {
    return playlistService.listAllPlaylists();
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
    return playlistService.deletePlaylist(id);
  }
}
