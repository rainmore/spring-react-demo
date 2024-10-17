package au.com.rainmore.centus.domains.books;

import au.com.rainmore.centus.domains.CreateableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity(name = "booksAuthor")
@Table(name = "BOOK_AUTHORS")
public class Author extends CreateableModel {

    public Long   id;
    public String firstname;
    public String middleName;
    public String lastname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NotEmpty
    @Size(max = 150)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    @Column
    @Size(max = 150)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(nullable = false)
    @NotEmpty
    @Size(max = 150)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }
}
