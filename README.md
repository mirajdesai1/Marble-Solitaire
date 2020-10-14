# Marble-Solitiare

Marble Solitaire game that can be played through the console.

## Description of Game
A game played on a board of a given shape consisting of a number of holes of which all but one are initially filled with marbles. The goal is to remove all marbles but one by jumping marbles from one side of an occupied marble hole to an empty space, removing the marble which was jumped over.

## To Play
In the MarbleSolitaire main function pass in:
 - One of: "english", "triangle", "european" specifying the board type
 - "-size { positive odd int }" Default is "-size 3" if not specified
 - "-hole { row # } { col # }" Default is a hole position located in the middle of the board
 
To make a move, pass in the position of the marble to move and the position of the empty space to move to.

A move would look like this: { fromRow # } { fromCol # } { toRow # } { toCol # }

Enter 'q' to quit the game.
    
