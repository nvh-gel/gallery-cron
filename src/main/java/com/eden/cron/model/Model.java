package com.eden.cron.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model", cascade = CascadeType.ALL)
    private List<Nickname> nicks;
}
