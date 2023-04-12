package com.example.kanzler.controllers;

import com.example.kanzler.models.Blog;
import com.example.kanzler.models.Room;
import com.example.kanzler.repositories.RoomRepository;
import com.example.kanzler.services.BlogService;
import com.example.kanzler.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;

    @Autowired
    private BlogService blogService;

    @RequestMapping(path = "/")
    public String home(Model model) {
        List<Room> list = roomService.getLastThreeRooms();
        model.addAttribute("rooms", list);

        List<Blog> blogList = blogService.getLastNews();
        model.addAttribute("news", blogList);

        return "index";
    }

    @RequestMapping(path = "/contacts")
    public String contacts(Model model) {
        return "contact";
    }

    @RequestMapping(path = "/about")
    public String about(Model model) {
        return "about";
    }

    @RequestMapping(path = "/discount")
    public String getDiscount(Model model) {
        return "discount";
    }

    @RequestMapping(path = "/rooms")
    public String getRooms(String category, String sorting, Model model) {
        List<Room> list;

        if (category != null) {
            list = roomService.getByCategory(category);
            model.addAttribute("category", category);
        } else {
            list = roomService.getByCategory("double");
            model.addAttribute("category", "double");
        }

        if (Objects.equals(sorting, "price_asc")) {
            Collections.sort(list, Room.SortByPriceAsc);
            model.addAttribute("sorting", "price_asc");
        } else if (Objects.equals(sorting, "price_desc")) {
            Collections.sort(list, Room.SortByPriceDesc);
            model.addAttribute("sorting", "price_desc");
        } else {
            Collections.sort(list, Room.SortByRating);
            model.addAttribute("sorting", "popularity");
        }

        model.addAttribute("rooms", list);
        return "rooms";
    }

    @GetMapping(path = "/rooms/{id}")
    public String getRoom(@PathVariable(value = "id") long id, Model model) {
        if (!roomRepository.existsById(id)) {
            return "redirect:/rooms";
        }

        Room room = roomService.getRoomById(id);
        model.addAttribute("rooms", room);
        model.addAttribute("images", room.getImages());

        return "room";
    }



    @GetMapping("/admin/rooms")
    public String getRoomList(Model model) {
        List<Room> list = roomService.getAllRooms();
        model.addAttribute("rooms", list);

        return "admin-rooms-list";
    }

    @GetMapping("/admin/room/create")
    public String createRoom(Model model) {
        return "create-room";
    }

    @PostMapping("/admin/room/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, @RequestParam("file4") MultipartFile file4,
                                @RequestParam("file5") MultipartFile file5, @RequestParam("file6") MultipartFile file6, Room room) throws IOException {
        roomService.saveRoom(room, file1, file2, file3, file4, file5, file6);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/room/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        if (!roomRepository.existsById(id)) {
            return "redirect:/admin/rooms";
        }

        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("images", room.getImages());

        return "edit-room";
    }

    @PostMapping(path = "/admin/room/edit/{id}")
    public String editPostRoom(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String subtitle, @RequestParam String description,
                               @RequestParam String price, @RequestParam String size, @RequestParam String rating,  @RequestParam String category, @RequestParam String isSmoke,
                               @RequestParam String isParking, Model model) throws IOException
    {
        if (title.isEmpty() || subtitle.isEmpty() || description.isEmpty() || size.isEmpty() || rating.isEmpty() ||
                category.isEmpty() || isSmoke.isEmpty() || isParking.isEmpty())
        {
            return "redirect:/admin/rooms";
        }

        Room room = roomRepository.findById(id).orElseThrow();
        room.setTitle(title);
        room.setSubtitle(subtitle);
        room.setDescription(description);
        room.setSize(Integer.parseInt(size));
        room.setPrice(Integer.parseInt(price));
        room.setRating(Integer.parseInt(rating));
        room.setCategory(category);
        room.setIsSmoke(Integer.parseInt(isSmoke));
        room.setIsParking(Integer.parseInt(isParking));
        roomRepository.save(room);

        return "redirect:/admin/rooms";
    }

    @PostMapping("/admin/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/rooms";
    }

    @PostMapping("/admin/image/delete/{id}")
    public String deleteImage(@RequestParam String roomId, @PathVariable Long id, Model model) {
        roomService.deleteImageById(id, Long.valueOf(roomId));

        Room room = roomService.getRoomById(Long.valueOf(roomId));
        model.addAttribute("room", room);
        model.addAttribute("images", room.getImages());

        return "edit-room";
    }

    @PostMapping("/admin/image/add")
    public String addImage(@RequestParam String roomId,  @RequestParam("newImage") MultipartFile newImage, Model model) throws IOException {
        Room room = roomRepository.findById(Long.valueOf(roomId)).orElse(null);

        roomService.saveOneRoom(room, newImage);
        model.addAttribute("room", room);
        model.addAttribute("images", room.getImages());

        return "edit-room";
    }
}
