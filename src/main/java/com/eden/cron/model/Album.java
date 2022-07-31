package com.eden.cron.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Album extends BaseModel {

    private String name;
    private String url;
    private String[] tags;
    private String[] downloads;
}
