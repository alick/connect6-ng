To build and run the game from the source, simply type:
	ant run
in your terminal's prompt line.

If you get the jar file version, simply
	java -jar connect6-ng-XXX.jar
is enough, where XXX should be replaced by the real version.

NOTE: the old connect6 project's svn history has been converted
to git style by git-svn. Also the historical log is linked with
the current project's tree by git-replace. However, `git clone'
and simple `git pull' won't download the old history. To get it,
add this line into origin section in the file `.git/config':
	fetch = +refs/replace/*:refs/replace/*
Then run `git pull'. Hopefully you can see the longer history.
