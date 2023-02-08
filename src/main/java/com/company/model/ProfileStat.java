package com.company.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileStat {
    private int progress;
    private int worseThanMe;
    private int betterThanMe;
}
