package com.eden.cron.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for album.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumVM extends BaseVM {

    private String name;

    private Long publisherId;
}
