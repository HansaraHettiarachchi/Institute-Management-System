/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hansara
 */
public class Test {
    public static void main(String[] args) {
        String r = "Match found- 6 with distance: 0.8633835243194133".split(":")[0].split(":")[0].split("-")[1].split(" with distance")[0].replaceAll("\\s", "");
        System.out.println(r);
    }
}
