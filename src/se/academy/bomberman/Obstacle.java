package se.academy.bomberman;

/**
 *
 *      Superklass mur till spelplanen, oförstörbara hinder i kartan, samt förstörbara tegelstenar
 *
 */
public class Obstacle {
    int size;
    int x;
    int y;
    boolean walkable;
}

class Wall extends Obstacle{

    int[][] perimeter;



}

class Block extends Obstacle{

}

class Brick extends Obstacle implements Breakable{

}

interface Breakable{

}


/*

    final Väderstreck 0 till 7
    sätt mark till ej walkable


 */