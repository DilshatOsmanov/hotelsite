package com.example.kanzler.services;

import com.example.kanzler.models.Image;
import com.example.kanzler.models.Room;
import com.example.kanzler.repositories.ImageRepository;
import com.example.kanzler.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    public List<Room> getAllRooms(){
        List<Room> list = (List<Room>)repository.findAll();
        return list;
    }

    public List<Room> getByCategory(String category){
        List<Room> list = (List<Room>)repository.findByCategory(category);
        return list;
    }

    public List<Room> getLastThreeRooms(){
        List<Room> list = (List<Room>)repository.getLastThreeRooms();
        return list;
    }

    public void addRoom(Room room){
        repository.save(room);
        return;
    }

    public void saveRoom(Room room, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4, MultipartFile file5, MultipartFile file6) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        Image image4;
        Image image5;
        Image image6;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            room.addImageToRoom(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            room.addImageToRoom(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            room.addImageToRoom(image3);
        }
        if (file4.getSize() != 0) {
            image4 = toImageEntity(file4);
            room.addImageToRoom(image4);
        }
        if (file5.getSize() != 0) {
            image5 = toImageEntity(file5);
            room.addImageToRoom(image5);
        }
        if (file6.getSize() != 0) {
            image6 = toImageEntity(file6);
            room.addImageToRoom(image6);
        }

        Room roomFromDb = repository.save(room);
        roomFromDb.setPreviewImageId(roomFromDb.getImages().get(0).getId());
        repository.save(room);
    }

    public void saveOneRoom(Room room, MultipartFile newImage) throws IOException {
        Image image1;

        if (newImage.getSize() != 0) {
            image1 = toImageEntity(newImage);
            room.addImageToRoom(image1);
        }

        Room roomFromDb = repository.save(room);
        roomFromDb.setPreviewImageId(roomFromDb.getImages().get(0).getId());
        repository.save(room);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public Room getRoomById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteRoom(Long id) {
        repository.deleteById(id);
    }

    public void deleteImageById(Long id, Long roomId) {
        imageRepository.deleteById(id);
        Room room = repository.findById(roomId).orElse(null);

        if(room.getImages().isEmpty()) {
            room.setPreviewImageId(null);
        } else {
            room.setPreviewImageId(room.getImages().get(0).getId());
        }
        repository.save(room);
    }
}
