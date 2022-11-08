package com.eden.cron.model;

import com.eden.data.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Data entity for model.
 */
@Getter
@Setter
@Entity
public class Model extends BaseModel {

    private String name;
    private String nativeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    private List<Nickname> nicks;
}
