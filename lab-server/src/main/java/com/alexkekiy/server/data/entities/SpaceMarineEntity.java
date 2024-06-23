package com.alexkekiy.server.data.entities;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.alexkekiy.common.utilites.CheckingReader.readValidType;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@Builder
@Table(name = "spacemarines")
@DynamicInsert

/**
 * основной хранимый entity-дата-класс
 */
public class SpaceMarineEntity implements Comparable<SpaceMarineEntity>, Serializable {

    /**
     * id {@link SpaceMarineEntity}Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator")
    @SequenceGenerator(name = "my_sequence_generator", sequenceName = "my_sequence_name", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity owner;

    /**
     * Имя {@link SpaceMarineEntity}.
     * Поле не может быть null и не может быть пустым.
     */
    @Column(name = "name")
    @NotNull
    private String name;

    /**
     * Координаты {@link SpaceMarineEntity}.
     * Поле не может быть null.
     */
    @OneToOne
    @JoinColumn(name = "Coordinate_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    @NotNull
    private CoordinatesEntity coordinatesEntity;

    /**
     * Дата создания {@link SpaceMarineEntity}.
     * Поле не может быть null и его значение должно генерироваться автоматически.
     * Формат даты: "dd-MM-yyyy HH:mm"
     */
    @NotNull
    @Column
    private LocalDateTime creationDate;

    /**
     * Уровень здоровья {@link SpaceMarineEntity}.
     * Значение поля должно быть больше 0.
     */
    @Column(name = "health")
    private long health;

    /**
     * Флаг лояльности {@link SpaceMarineEntity}.
     * Поле может быть null.
     */
    @NotNull
    @Column
    private Boolean loyal;

    /**
     * Рост {@link SpaceMarineEntity}.
     */
    @Column
    private float height;

    /**
     * Вид оружия {@link SpaceMarineEntity}.
     * Поле может быть null.
     */
    @Column
    @NotNull
    private Weapon weaponType;

    /**
     * Глава {@link SpaceMarineEntity}.
     * Поле не может быть null.
     */

    @OneToOne
    @JoinColumn(name = "Chapter_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    @NotNull
    private ChapterEntity chapterEntity;

    SpaceMarineEntity(AccountEntity acc, java.lang.String name, CoordinatesEntity coordinatesEntity, java.time.LocalDateTime creationDate, long health, java.lang.Boolean loyal, float height, com.alexkekiy.server.data.entities.Weapon weaponType, ChapterEntity chapterEntity) {
        this.health = health;
        this.chapterEntity = chapterEntity;
        this.loyal = loyal;
        this.coordinatesEntity = coordinatesEntity;
        this.owner = acc;
        this.weaponType = weaponType;
        this.name = name;
        this.height = height;
        this.creationDate = now();


    }

    SpaceMarineEntity(long id, AccountEntity owner, String name, CoordinatesEntity coordinatesEntity, LocalDateTime localDateTime, long health, Boolean loyal, float height, Weapon weaponType, ChapterEntity chapterEntity) {


        this.id = id;
        this.health = health;
        this.chapterEntity = chapterEntity;
        this.loyal = loyal;
        this.coordinatesEntity = coordinatesEntity;
        this.owner = owner;
        this.weaponType = weaponType;
        this.name = name;
        this.height = height;
        this.creationDate = localDateTime;


    }

    public SpaceMarineEntity(String n, CoordinatesEntity c, long h, Boolean l, float height, Weapon gun, ChapterEntity ch) {
        super();
        this.name = n;
        this.health = h;
        this.coordinatesEntity = c;
        this.loyal = l;
        this.weaponType = gun;
        this.chapterEntity = ch;
        this.height = height;
    }

    public SpaceMarineEntity() {
        this.creationDate = now();
    }

    public static SpaceMarineEntity newInstance(String[] args) {


        SpaceMarineEntityBuilder spmBuilder = SpaceMarineEntity.builder();
        spmBuilder.name = ((String) readValidType("s", args[0]));
        spmBuilder.coordinatesEntity = (new CoordinatesEntity(
                (Long) readValidType("l", args[1]),
                (Float) readValidType("f", args[2])));

        spmBuilder.health = ((Long) readValidType("l", args[3]));
        spmBuilder.loyal = ((Boolean) readValidType("b", args[4]));
        spmBuilder.height = ((Float) readValidType("f", args[5]));
        spmBuilder.weaponType = (Weapon.choose((String) readValidType("s", args[6])));
        spmBuilder.chapterEntity = (new ChapterEntity(
                (String) readValidType("s", args[7]),
                (String) readValidType("s", args[8])));
        //spmBuilder.id = Math.toIntExact(DBConnection.getDBConnection().newId());
        return spmBuilder.build();
    }

    public SpaceMarineEntity update(String[] args) {
        return newInstance(args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceMarineEntity that)) return false;
        return id == that.id && health == that.health && Float.compare(height, that.height) == 0 && Objects.equals(name, that.name) && Objects.equals(coordinatesEntity, that.coordinatesEntity) && Objects.equals(creationDate, that.creationDate) && Objects.equals(loyal, that.loyal) && weaponType == that.weaponType && Objects.equals(chapterEntity, that.chapterEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinatesEntity, creationDate, health, loyal, height, weaponType, chapterEntity);
    }

    @Override
    public String toString() {

        return "***** " + this.getClass() + " Details *****\n" +
                "ID=" + getId() + "\n" +
                "Name=" + getName() + "\n" +
                "health=" + getHealth() + "\n" +
                "Coordinates=" + getCoordinatesEntity() + "\n" +
                "loyal=" + getLoyal() + "\n" +
                "chapter=" + getChapterEntity() + "\n" +
                "weapoonType=" + getWeaponType() + "\n" +
                "height=" + getHeight() + "\n" +
                "creationDate=" + getCreationDate() + "\n" +
                "*****************************";
    }

    @Override
    public int compareTo(SpaceMarineEntity other) {
        return this.name.compareTo(other.name);
    }


}
