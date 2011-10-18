To run the game, simply type:
	ant run
in your terminal's prompt line.

NOTE: the old connect6 project's svn history has been converted
to git style by git-svn. Also the historical log is linked with
the current project's tree by git-replace. However, `git clone'
and simple `git pull' won't download the old history. To get it,
add this line into origin section in the file `.git/config':
	fetch = +refs/replace/*:refs/replace/*
Then run `git pull'. Hopefully you can see the longer history.
