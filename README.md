# 3D_TicTacToe
A console game of 3D_TicTacToe made with Java.

3D TicTacToe is basically a 3x3 TicTacToe plate stacked. (reference image included)
- 27! total cases

The regular minimax algorithm for 3x3 TicTacToe does not work in this version, because there are too many cases. 
Optimized it through:
- immediate win case detection (if there is a immediate win case, just put there, no recursion happening)
- alpha/beta pruning (do not calculate unnecessary cases)
- depth heuristics (limit the amount of recursion execution, and make a basic assumption at that point.)
