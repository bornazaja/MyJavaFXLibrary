/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bzaja.myjavafxlibrary.test.bean;

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
public class Person {

    private String firstName;
    private String lastName;
    private String email;
}
