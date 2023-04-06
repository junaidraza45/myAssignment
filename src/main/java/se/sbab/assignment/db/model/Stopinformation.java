package se.sbab.assignment.db.model;

import lombok.Data;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
public class Stopinformation implements Serializable {
    @Id
    public Integer stopid;
    @NotNull
    @NotEmpty
    public String stopname;
    @NotNull
    @NotEmpty
    public String stopareanumber;

}
