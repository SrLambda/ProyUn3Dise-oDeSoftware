package cl.utalca.utaleats.ratingsservice;

import cl.utalca.utaleats.ratingsservice.Rating;
import cl.utalca.utaleats.ratingsservice.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = "*")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<Rating> getAllRatings(@RequestParam(required = false) Long storeId) {
        if (storeId != null) {
            return ratingService.getRatingsByStoreId(storeId);
        }
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rating createRating(@Valid @RequestBody Rating rating) {
        return ratingService.createRating(rating);
    }
}