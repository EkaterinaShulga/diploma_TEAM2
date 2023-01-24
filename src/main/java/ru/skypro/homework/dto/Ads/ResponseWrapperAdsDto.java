package ru.skypro.homework.dto.Ads;

import lombok.Data;

@Data
public class ResponseWrapperAdsDto {
    private long count;
    private AdsDto[] results;
}
