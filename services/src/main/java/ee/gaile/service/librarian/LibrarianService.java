package ee.gaile.service.librarian;

import ee.gaile.entity.librarian.Books;
import ee.gaile.entity.models.FilterWrapper;
import ee.gaile.entity.models.SelectedFilter;
import ee.gaile.service.librarian.search.date.DateSearchList;
import ee.gaile.service.librarian.search.date.DateSearchRepository;
import ee.gaile.service.librarian.search.text.SearchByAuthorOrTitle;
import ee.gaile.service.repository.librarian.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LibrarianService {
    private final LibrarianRepository booksRepository;

    public LibrarianService(LibrarianRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    /**
     * Performs filtering based on user selected conditions.
     *
     * @return a list of books based on all filters.
     * @throws ParseException
     */
    public List<Books> filterOut(List<SelectedFilter> selectedFilterList, String condition) throws ParseException {
        List<Books> booksList = booksRepository.findAll();
      //  List<SelectedFilter> selectedFilterList = filterWrapper.getFilters();

        if (selectedFilterList.isEmpty()) {
            booksList.clear();
            return booksList;
        }

        if (condition.equals("allConditions") || condition.equals("noneOfTheCondition")) {
            List<Books> tempList = new ArrayList<>(booksList);

            for (SelectedFilter selectedFilter : selectedFilterList) {
                switch (selectedFilter.getSearchArea()) {
                    case "Author":
                    case "Title":
                        booksList = new SearchByAuthorOrTitle(booksList, selectedFilter).search();
                        break;
                    case "Date":
                        booksList = new DateSearchList(booksList, selectedFilter).search();
                        break;
                }
            }

            if (condition.equals("noneOfTheCondition")) {
                tempList.removeAll(booksList);
                booksList.clear();
                booksList.addAll(tempList);
            }
        } else {
            Set<Books> temp = new HashSet<>();

            for (SelectedFilter selectedFilter : selectedFilterList) {
                switch (selectedFilter.getSearchArea()) {
                    case "Author":
                    case "Title":
                        booksList = new SearchByAuthorOrTitle(booksList, selectedFilter).search();
                        break;
                    case "Date":
                        booksList = new DateSearchRepository(booksRepository, selectedFilter).search();
                        break;
                }
                temp.addAll(booksList);
            }
            booksList.clear();
            booksList.addAll(temp);
        }
        return booksList;
    }

    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }
}
