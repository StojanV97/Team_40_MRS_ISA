package de.jonashackt.springbootvuejs.controller;


import de.jonashackt.springbootvuejs.domain.PriceBook;
import de.jonashackt.springbootvuejs.repository.PriceBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceBookController {

    @Autowired
    PriceBookRepository priceBookRepository;

    @GetMapping(value = "admin/price-book")
    public ResponseEntity<?> getAllPrices(){
        Collection<PriceBook> priceBook = (Collection<PriceBook>) priceBookRepository.findAll();
        return new ResponseEntity<Collection<PriceBook>>(priceBook, HttpStatus.OK);
    }
    @PostMapping(value = "admin/price-book-update")
    public ResponseEntity<?> updatePriceBookItem(@RequestBody PriceBook priceBookItem){
        Optional<PriceBook> priceBook = priceBookRepository.findById(priceBookItem.getId());
        if(priceBook.isPresent()){
            PriceBook pr = priceBook.get();
            pr.setPrice(priceBookItem.getPrice());
            priceBookRepository.save(pr);
        }
        return new ResponseEntity<String>("priceBook", HttpStatus.OK);
    }
}
