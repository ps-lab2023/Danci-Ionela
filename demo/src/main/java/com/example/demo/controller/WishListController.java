package com.example.demo.controller;
import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.WishListDto;
import com.example.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin("http://localhost:3000")
public class WishListController {
    @Autowired
    private WishListService wishListService;


    @DeleteMapping("/delete_from_wishlist")
    public void deleteWishList(@RequestParam String email,@RequestParam Long productId){
        wishListService.delete_from_wishlist(email,productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WishListDto> createNewWishList_whenPostWishList(@RequestParam String email,@RequestParam Long productId) {
        WishListDto createdWislist = wishListService.createWishList(email,productId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdWislist)
                .toUri();
        return ResponseEntity.created(uri).body(createdWislist);
    }

    @GetMapping("/get_wishlist")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> listWishlistForUser(@RequestParam String email) {
        return wishListService.getWishList(email);
    }

    @GetMapping("/get_wishlist_by_id{id}")
    public ResponseEntity findWishListById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(wishListService.findById(id));
    }


}
