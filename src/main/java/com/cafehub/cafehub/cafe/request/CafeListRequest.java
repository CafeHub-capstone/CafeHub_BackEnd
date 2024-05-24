package com.cafehub.cafehub.cafe.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CafeListRequest {

    private String theme;

    private String sortedByType;

    private Integer currentPage;
}
