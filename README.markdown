# Introduction

This is connect6-ng, a fork of [connect6][connect6] project. The project
aims at improving the old project in several aspects.

Please refer to [www.connect6.org][connect6org] for general information on
the connect6 game.

# Howto Install and Run

The recommended way to use connect6-ng is using the source code.
Fist you should clone the repository by git:

    git clone https://code.google.com/p/connect6-ng/

Then cd into the directory connect6-ng on your local machine.
To build and run the game from the source, simply type:

    ant run

in your terminal's prompt line.

Alternatively, if you get the jar archive, simply type

    java -jar connect6-ng-XXX.jar

(where XXX should be replaced by the real version)
or double click its icon in your file manager. That should
be enough.

# Notes
The old connect6 project's svn history has been converted
to git style by git-svn. Also the historical log is linked with
the current project's tree by git-replace. However, `git clone'
and simple `git pull' won't download the old history. To get it,
add this line into origin section in the file `.git/config':
    fetch = +refs/replace/*:refs/replace/*
Then run `git pull'. Hopefully you can see the longer history.

[connect6]: http://code.google.com/p/connect6/
[connect6org]: http://www.connect6.org/web/index.php
