## Introduction

Yet another implementation of Connect6, a fun game with very simple rules:

- Black plays first, putting one stone on one intersection.
- Then White and Black put two stones each time in turn.
- The one who is the first to put six or more stones in a row (horizontally, vertically, or diagonally) wins.

Currently there is a desktop client version which can run on Windows, Linux, and Mac OS X, as well as an Android version. Please refer to the README files in their respective directories for guide about installation and development.

`connect6-ng` is a continous improvement of the [connect6][connect6] project from the Software Engineering course in Tsinghua University. Refer to [this page][history] (in Chinese) for an overview of the project history.

## Notes on Cloning the Repo

The current repo is a merge of several projects with the help of git-svn and git-replace. An initial `git clone` will not give you the whole line of development. To get the whole history, please add the following line into the `origin` section in the file `.git/config` after you have cloned the repo:

    fetch = +refs/replace/*:refs/replace/*

Then run `git remote update`. Then if everything goes well, you can see the complete history.

## References

For more information about the git-replace hack, you can refer to [this page][repo-history-zh] (in Chinese) or [this page][repo-history-en] (in English).

Please refer to [www.connect6.org][connect6org] for more general information on the Connect6 game.

[connect6]: http://code.google.com/p/connect6/
[connect6org]: http://www.connect6.org/web/index.php
[history]: https://github.com/alick9188/connect6-ng/wiki/%E5%85%AD%E5%AD%90%E6%A3%8B%E9%A1%B9%E7%9B%AE%E5%8E%86%E5%8F%B2
[repo-history-zh]: https://wp-awesome.rhcloud.com/2012/02/03/connect6-ng-join-svn-git-history-summary/
[repo-history-en]: http://stackoverflow.com/a/1547490/1166587
