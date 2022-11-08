package com.eden.cron.model;


import com.eden.data.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Data entity for model nickname.
 */
@Getter
@Setter
@Entity
public class Nickname extends BaseModel {

    private String nick;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
}
