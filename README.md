Testing Add file in GIT
Jack Caldwell

Command to Compile Files:
javac Board.java Roll.java PlayerData.java Checker.java

if(tray[current_player].getNumCheckers() == 15) {
                                    Board.displayBoard(spikes, tray, player[current_player], bar);

                                    if (tray[(current_player+1) % 2].getNumCheckers() == 0 && bar.hasCheckersOfColor(player[(current_player+1) % 2].getPlayerColour())) {

                                    } else if (tray[(current_player+1) % 2].getNumCheckers() == 0) {

                                    } else {
                                        System.out.println(player[current_player].getName() + " Won this Match and got a single");
                                        player[current_player].increasematchPoints(1);
                                        matchNum ++;
                                    }

                                    if (player[current_player].getmatchPoints() >= pointsToPlay) {
                                        System.out.println(player[current_player].getName() + " has " + player[current_player].getmatchPoints());
                                        System.out.println(player[current_player].getName() + " Won The whole Game");
                                        System.exit(0);
                                    }