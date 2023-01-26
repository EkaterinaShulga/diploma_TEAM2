package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
public class ResponseWrapperAdsDto {
    private long count;
    private AdsDto[] results;
}
