package com.eden.cron.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModelVM extends BaseVM {

    private String name;
    private String nativeName;

    private List<NicknameVM> nicks;
}
