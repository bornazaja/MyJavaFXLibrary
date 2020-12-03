/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.springframework.advancedsearch;

import com.bzaja.myjavalibrary.springframework.data.query.QueryCriteriaDto;
import com.bzaja.myjavalibrary.util.BeanClassInterface;
import com.bzaja.myjavalibrary.util.PropertyInfoDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Borna
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdvancedSearchDto {

    private BeanClassInterface beanClassInterface;
    private List<PropertyInfoDto> propertiesInfo;
    private QueryCriteriaDto queryCriteria;
}
