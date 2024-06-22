package com.alexkekiy.server.data.entities;

import com.alexkekiy.server.util.DBConnection;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "chapters")

public class ChapterEntity {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private String world; //Поле не может быть null
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ChapterEntity(String n, String w) {
        world = w;
        name = n;
        this.parentLegion = "";
        this.id = DBConnection.getDBConnection().newId();
    }

    public ChapterEntity() {
    }

    @Override
    public boolean equals(Object obj) {
        boolean answ = true;
        if (this.getClass() == obj.getClass()) {
            ChapterEntity c1 = (ChapterEntity) obj;
            if (!c1.name.equals(this.name) || !c1.world.equals(this.world) || !c1.parentLegion.equals(this.parentLegion)) {
                answ = false;
            }
        } else {
            answ = false;
        }
        return answ && super.equals(obj);
    }

    @Override
    public String toString() {
        return name + "," + parentLegion + "," + world;
    }

}
