package dev.cwute.cwuteify.repository;

import dev.cwute.cwuteify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
  @Query(
      "SELECT s FROM Song s WHERE lower(s.title) LIKE lower(concat('%', :query, '%')) OR"
          + " lower(s.artist) LIKE lower(concat('%', :query, '%'))")
  List<Song> searchQuery(String query);
}
