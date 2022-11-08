package com.eden.cron.model;


import com.eden.data.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Data entity for model nickname.
 */
@Getter
@Setter
@Entity
@Where(clause = "is_deleted = false")
public class Nickname extends BaseModel {

    private String nick;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
}
