# [The Wrong-Way Cow](https://www.codewars.com/kata/57d7536d950d8474f6000a06)

Have you ever noticed that cows in a field are always facing in the same direction?

Reference: http://bfy.tw/7fgf

<hr>

Well.... not quite always.

One stubborn cow wants to be different from the rest of the herd - it's that damn *Wrong-Way Cow!*

<h2>Task</h2>

Given a field of cows find which one is the Wrong-Way Cow and return her position.

Notes:

* There are always at least 3 cows in a herd
* There is only 1 Wrong-Way Cow!
* Fields are rectangular
* The cow position is zero-based [x,y] of her head (i.e. the letter c)

<h2>Examples</h2>

Ex1
<pre style="margin-bottom:20px">
cow.cow.cow.cow.cow
cow.cow.cow.cow.cow
cow.<span style="background:red">woc</span>.cow.cow.cow
cow.cow.cow.cow.cow
</pre>

*Answer: [6,2]*

Ex2
<pre style="margin-bottom:20px">
c..........
o...c......
w...o.c....
....w.o....
......w.<span style="background:red">cow</span>
</pre>

*Answer: [8,4]*
