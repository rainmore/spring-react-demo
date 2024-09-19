package au.com.rainmore.centus.domains.users;

import au.com.rainmore.centus.domains.CreateableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity(name = "usersPermissions")
@Table(name = "USER_PERMISSIONS")
public class Permission extends CreateableModel {

    private Long       id;
    private String     name;
    private Permission parent;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NotEmpty
    @Size(max = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    public Permission getParent() {
        return parent;
    }

    public void setParent(Permission parent) {
        this.parent = parent;
    }

}
