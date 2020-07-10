package ee.gaile.controller.librarian;

import ee.gaile.entity.librarian.Books;
import ee.gaile.entity.models.FilterWrapper;
import ee.gaile.entity.models.SelectedFilter;
import ee.gaile.service.librarian.LibrarianService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static ee.gaile.service.security.SecurityConfig.API_V1_PREFIX;

@Slf4j
@RestController
@RequestMapping(API_V1_PREFIX + "/librarian")
@AllArgsConstructor
public class LibrarianController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianController.class);

    private final LibrarianService searchService;

    @GetMapping("/find-all")
    public List<Books> findAll() {
        return searchService.getAllBooks();
    }

    @PostMapping( path = "/{condition}")
    public List<Books> getBooksByFilter(@PathVariable(value = "condition") String condition,@RequestBody ArrayList<SelectedFilter> selectedFilters) throws java.text.ParseException {
        LOGGER.info("Getting list of network client invoices");
        return searchService.filterOut(selectedFilters, condition);
    }
}
