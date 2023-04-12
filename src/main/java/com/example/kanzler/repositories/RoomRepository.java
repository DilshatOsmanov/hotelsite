package com.example.kanzler.repositories;

import com.example.kanzler.models.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query(value = "SELECT * FROM room LIMIT 3", nativeQuery = true)
    List<Room> getLastThreeRooms();

    @Query(value = "SELECT * FROM room WHERE room.category LIKE :category", nativeQuery = true)
    List<Room> findByCategory( @Param("category") String category);
}
