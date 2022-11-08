package com.eden.cron.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * View model of publisher data.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PublisherVM extends BaseVM {

    private String name;
}
