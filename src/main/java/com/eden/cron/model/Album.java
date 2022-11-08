package com.eden.cron.model;

import com.eden.data.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

/**
 * Data entity for album.
 */
@Entity
@Getter
@Setter
@SQLDelete(sql = "update album set is_deleted = true, updated_at = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "is_deleted = false")
public class Album extends BaseModel {

    private String name;
}
