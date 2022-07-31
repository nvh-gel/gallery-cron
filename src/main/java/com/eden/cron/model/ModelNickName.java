package com.eden.cron.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class ModelNickName extends BaseModel {

    private long modelId;
    private String nick;
}
