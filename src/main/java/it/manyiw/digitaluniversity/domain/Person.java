package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
@Valid
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    // for Student.class is the studying major
    // for Professor.class is the teaching major
    private Major major;

    @NotBlank(message = "Enter a valid firstname")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "Enter a valid lastname")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Enter a valid personalEmail address")
    @NotBlank
    @Column(unique = true)
    private String personalEmail;

    @Email(message = "Enter a valid personalEmail address")
    @NotBlank
    @Column(unique = true, nullable = false)
    protected String institutionalMail;

    @Min(value = 0, message = "Age cannot be negative")
    private int age;

    @Transient
    protected String fullName;

    public Person() {}

    public int getId() {
        return id;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PostPersist
    @PostUpdate
    @PostLoad
    // after loading into the Persistence Context calculate the fullName
    public void calculateFields() {
        this.fullName = firstName + " " + lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setInstitutionalMail(String institutionalMail) {
        this.institutionalMail = institutionalMail;
    }

    public String getInstitutionalMail() {
        return institutionalMail;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id != 0 && id == person.id; // Compare IDs
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
