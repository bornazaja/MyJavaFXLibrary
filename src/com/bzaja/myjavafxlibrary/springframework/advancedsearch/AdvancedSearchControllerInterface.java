/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.springframework.advancedsearch;

import com.bzaja.myjavafxlibrary.util.ControllerInterface;
import com.bzaja.myjavafxlibrary.util.StageResult;
import com.bzaja.myjavalibrary.springframework.data.query.QueryCriteriaDto;

/**
 *
 * @author Borna
 */
public interface AdvancedSearchControllerInterface extends ControllerInterface<AdvancedSearchDto> {

    void loadInstructionsHtmlFileInWebView();

    QueryCriteriaDto getQueryCriteria();

    StageResult getStageResult();
}
