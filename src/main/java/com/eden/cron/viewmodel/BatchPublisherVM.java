package com.eden.cron.viewmodel;

import lombok.Data;

import java.util.List;

/**
 * Dto for batch creating publishers.
 */
@Data
public class BatchPublisherVM {

    List<String> names;
}
