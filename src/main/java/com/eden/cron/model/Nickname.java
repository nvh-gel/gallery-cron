package com.eden.cron.model;


import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Data entity for model nickname.
 */
@Getter
@Setter
@Entity
public class Nickname extends BaseModel {

    private String nick;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
}
