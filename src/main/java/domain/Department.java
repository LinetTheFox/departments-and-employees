package domain;

import java.io.Serializable;

/**
 * Department domain class
 * @author linet
 */
public class Department implements Serializable {
    private Long id;
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
