package com.nbsaw.miaohu.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class TagMap implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long correlation;

    @Column(nullable = false)
    Long tagId;

    String type;
}
