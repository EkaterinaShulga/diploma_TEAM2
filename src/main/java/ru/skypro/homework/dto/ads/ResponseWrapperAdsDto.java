package ru.skypro.homework.dto.ads;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsDto {
    private long count;
    private List<AdsDto> results;
}
