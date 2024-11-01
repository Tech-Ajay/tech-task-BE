'll break down all the changes and explain their purpose:
1. Entity Changes
New Author Entity:

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();
    // ...
}


Created a new JPA entity to represent authors
Uses @OneToMany to establish a one-to-many relationship with books
mappedBy = "author" indicates that the Book entity owns the relationship
Uses Set<Book> to prevent duplicate books and improve performance
Modified Book Entity:


@Entity
public class Book {
    // ...
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;
    // ...
}



}
Changed author from a String to an Author entity reference
@ManyToOne establishes many-to-one relationship with Author
FetchType.EAGER ensures author data is loaded with the book
@JoinColumn specifies the foreign key column name


2. Repository Changes

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPublishedDateBetween(LocalDate start, LocalDate end);
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
    // ...
}


Added new query methods:
findByTitleContainingIgnoreCase: Case-insensitive partial title search
findByPublishedDateBetween: Date range filtering
findAllByOrderByTitleAsc/Desc: Sorting by title
findAllByOrderByPublishedDateAsc/Desc: Sorting by date
3. GraphQL Schema Changes

type Query {
    findBooksByDateRange(startDate: String!, endDate: String!): [Book!]!
    findBooksByTitleContaining(title: String!): [Book!]!
    findAllBooksSortedByTitle(ascending: Boolean!): [Book!]!
    findAllBooksSortedByDate(ascending: Boolean!): [Book!]!
    getAllBookTitles: [String!]!
    // ...
}

type Author {
    id: ID!
    name: String!
    books: [Book!]!
}


- Added new query operations for filtering and sorting
Created Author type with its fields and relationship to Book
Modified Book type to reference Author type instead of string


4. Controller Changes
@Controller
public class BookController {
    @QueryMapping
    public List<Book> findBooksByDateRange(@Argument String startDate, @Argument String endDate) {
        return bookService.findByDateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
    
    @QueryMapping
    public List<String> getAllBookTitles() {
        return bookService.findAll().stream()
            .map(Book::getTitle)
            .collect(Collectors.toList());
    }
    // ...
}


Added new @QueryMapping methods to handle GraphQL queries
Each method corresponds to a query in the GraphQL schema
Methods delegate to service layer for business logic
5. Test Changes

@DataJpaTest
public class BookRepositoryTest {
    @Test
    void testFindByTitleContaining() {
        List<Book> books = repo.findByTitleContainingIgnoreCase("god");
        assertEquals(2, books.size());
    }
    
    @Test
    void testFindByDateRange() {
        List<Book> books = repo.findByPublishedDateBetween(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 12, 31)
        );
        assertEquals(1, books.size());
    }
    // ...
}


Added comprehensive tests for new repository methods
Tests verify filtering, sorting, and relationship behavior
Uses @DataJpaTest for repository layer testing
Includes setup data with related Author and Book entities
Key Benefits of Changes:
1. Better Data Modeling: Separate Author entity allows for proper relationship management
Improved Querying: New methods support flexible searching and sorting
Type Safety: Using entity relationships instead of strings reduces errors
Performance: JPA relationships can be optimized for better performance
Maintainability: Clear separation of concerns and proper entity relationships
These changes create a more robust and flexible system that better represents the domain model and provides more powerful query capabilities through GraphQL.




1. Find All Books
query {
  findAllBooks {
    id
    title
    publishedDate
    author {
      id
      name
    }
  }
}


2. Find Book by ID
query {
  findBookById(id: "1") {
    id
    title
    publishedDate
    author {
      name
      books {
        title
      }
    }
  }
}

3. Find Books by Date Range
query {
  findBooksByDateRange(
    startDate: "2021-01-01", 
    endDate: "2022-12-31"
  ) {
    id
    title
    publishedDate
    author {
      name
    }
  }
}


4. Find Books by Title (Contains)
query {
  findBooksByTitleContaining(title: "God") {
    id
    title
    publishedDate
    author {
      name
    }
  }
}

5. Get All Books Sorted by Title
query {
  findAllBooksSortedByTitle(ascending: true) {
    id
    title
    publishedDate
    author {
      name
    }
  }
}


6. Get All Books Sorted by Date
query {
  findAllBooksSortedByDate(ascending: false) {
    id
    title
    publishedDate
    author {
      name
    }
  }
}


7. Get All Book Titles
query {
  getAllBookTitles
}


8. Create New Book
mutation {
  createBook(
    title: "The Great Gatsby"
    authorId: "1"
    publishedDate: "1925-04-10"
  ) {
    id
    title
    publishedDate
    author {
      name
    }
  }
}


9. Delete Book
mutation {
  deleteBook(id: "1")
}


10. Complex Query Example (Getting Books with Author Details)
query {
  findAllBooks {
    id
    title
    publishedDate
    author {
      id
      name
      books {
        id
        title
        publishedDate
      }
    }
  }
}