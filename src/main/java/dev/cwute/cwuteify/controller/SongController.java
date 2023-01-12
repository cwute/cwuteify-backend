package dev.cwute.cwuteify.controller;

import dev.cwute.cwuteify.model.Song;
import dev.cwute.cwuteify.service.SongService;
import dev.cwute.cwuteify.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/song")
public class SongController {

  @Autowired private SongService songService;

  @PostMapping("/add")
  public ResponseEntity<String> addSong(
      @RequestParam("title") String title,
      @RequestParam("artist") String artist,
      @RequestParam("song") MultipartFile songFile,
      @RequestParam("image") MultipartFile coverArtFile)
      throws IOException {
    Song song = new Song();

    song.setTitle(title);
    song.setArtist(artist);
    // i hate this part i need to find a better way to do this frfr
    String coverArt =
        StringUtils.cleanPath(Objects.requireNonNull(coverArtFile.getOriginalFilename()));
    String songPath = StringUtils.cleanPath(Objects.requireNonNull(songFile.getOriginalFilename()));

    song.setPathToSong(songPath);
    song.setCoverArt(coverArt);

    Song saved = songService.saveSong(song);

    String uploadDir = "songs-uploaded/" + saved.getId();

    FileUploadUtil.saveFile(uploadDir, coverArt, coverArtFile);
    FileUploadUtil.saveFile(uploadDir, songPath, songFile);

    return ResponseEntity.ok().body("Song added");
  }

  @GetMapping("/{id}/get")
  public ResponseEntity<Song> getSongById(@PathVariable Long id) {
    return songService.getSongById(id);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Song>> getSongs() {
    return songService.listSongs();
  }

  @GetMapping("/{id}/get-image")
  public ResponseEntity<byte[]> getCoverArt(@PathVariable Long id, HttpServletResponse response)
      throws IOException {
    return songService.getImageById(id, response);
  }

  @GetMapping("/{id}/get-track")
  public ResponseEntity<byte[]> getTrackById(@PathVariable Long id, HttpServletResponse response)
      throws IOException {
    return songService.getTrackById(id, response);
  }

  @GetMapping("/search/{query}")
  public ResponseEntity<List<Song>> searchSongs(@PathVariable String query) {
    return songService.searchSongs(query);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<String> deleteSong(@PathVariable Long id) {
    return songService.deleteSong(id);
  }
}
