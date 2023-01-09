This jar file allows for automatic rotating and mirroring of individual network RULs.

Usage:
Save your RUL lines for one intersection in a text file, e.g. testRuls.txt.

As a first line add the rotations and mirrorings you need, e.g. in this form
0,0 2,1
to generate the same as your input (0,0) and a situation rotated by 2 and mirorred (2,1).
Note the separation by one space character.

So your file could look like this:

0,0 1,0 2,0 3,0 0,1 1,1 2,1 3,1
1,2,0,2,0
2,1,0,0,2,0
2,2,2,0,2,0
2,3,2,0,0,0
2,5,2,0,0,2
2,6,2,2,0,2
2,7,0,0,2,0
2,10,2,0,2,0
2,20,?,2,?,?
3,10,0x5f06cf00,0,0
3,2,0x5f06d000,0,0
3,1,0x5f06d100,0,0
3,3,0x5f06d200,0,0
3,0,0x5f06d300,0,0
3,7,0x5f06d400,0,0
3,5,0x5f06d500,0,0
3,6,0x5f06d600,0,0

(an example of the FAR)

Now you have to start the Terminal and navigate to your directory, e.g.
cd Desktop
if the jar file is located on the Desktop.
Now run the jar, you have to pass the path of your text file. The command could look like this

java -jar GenerateINRULs.jar testRuls.txt > testRuls2.txt

The generated lines are then saved to the file testRuls2.txt.

Please not, that I did not test this class very much, but I did not notice any mistakes so far,
as long as one strictly sticks to the format. Comments are not supported.

-memo
