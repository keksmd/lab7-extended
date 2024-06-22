package com.alexkekiy.server.data.entities;

import com.alexkekiy.server.exceptions.IncorrectDataInput;
import com.alexkekiy.server.util.DBConnection;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "coordinates")
public class CoordinatesEntity {
    @Column
    @NotNull
    private Long x; //Максимальное значение поля: 625, Поле не может быть null
    @Column
    @NotNull
    private Float y; //Значение поля должно быть больше -354, Поле не может быть null
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public CoordinatesEntity(Long x, Float y) {
        this.setX(x);
        this.setY(y);
        this.id = DBConnection.getDBConnection().newId();
    }

    public CoordinatesEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoordinatesEntity that)) return false;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, id);
    }

    public void setX(Long x) {
        if (x >= 625L) {
            throw new IncorrectDataInput();
        }
        this.x = x;
    }

    public void setY(Float y) {
        if (y < -354F) {
            throw new IncorrectDataInput();
        }
        this.y = y;

    }

    @Override
    public String toString() {
        return x + "," + y;
    }

}
