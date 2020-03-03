package uk.kissgergely.managementservice.data.entities;

import javax.persistence.*;

@Entity
@Table(name = JpaConstants.TAG)
public class TagEntity {

    @Id
    @GeneratedValue
    @Column(name = JpaConstants.ID, nullable = false)
    private Integer id;

    @Column(name = JpaConstants.NAME)
    private String name;

    @Column(name = JpaConstants.DESCRIPTION)
    private String description;

    @Column(name = JpaConstants.COLOR)
    private String color;

    public TagEntity() {
    }
}
