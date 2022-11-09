package com.eden.cron.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * DTO for album.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumVM extends BaseVM {

    private String name;
    private String thumbnail;
    private String url;
    private String tags;

    private Long publisherId;

    private List<ModelVM> models;
}
