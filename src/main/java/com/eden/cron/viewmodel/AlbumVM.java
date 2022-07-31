package com.eden.cron.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumVM extends BaseVM {

    private String name;
    private String url;
    private List<String> tags;
    private List<String> downloads;
}
