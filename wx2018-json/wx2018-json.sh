#/bin/bash

do=$1

JAVA_HOME=/data/jdk1.8
APP_HOME=/data/App/wx2018-json
APP_NAME=wx2018-json
APP_MAINCLASS=com.jrj.wx.json.Wx2018JsonApplication
STAT_PID=$APP_HOME/$APP_NAME.pid

start()
{
	if test -e $STAT_PID
	then
		echo
		echo The $APP_NAME already Started!
		echo
	else
		echo
		echo Start The $APP_NAME ....
		echo
			 $JAVA_HOME/bin/java -jar $APP_HOME/com.jrj.wx.json-0.0.1-SNAPSHOT.jar -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
				-Xloggc:/data/App/wx2018-json/gc.log -XX:+UserConcMarkSweepGC -Xms1024m -Xmx1024m $APP_MAINCLASS >/dev/null 2>&1 &
		echo $APP_NAME is starting please waiting ......
		echo 
		sleep 5
		STATUS=`ps -p $!|grep java |awk '{print $1}'`
		if test $STATUS
			then
				echo $!
				echo $!>$STAT_PID
				echo The $APP_NAME Started!
				echo
			else
				echo $!
				echo The $APP_NAME Start Failed
				echo please Check the system
				echo
		fi
	fi
}
stop()
{
	if test -e $STAT_PID
	then
		echo
		echo Stop $APP_NAME....
		echo
		TPID=`cat $STAT_PID`
		kill $TPID
		sleep 1
		STATUS=`ps -p $TPID |grep java | awk '{print $1}'`
		if test $STATUS
			then
				echo $APP_NAME NOT Stoped!
				echo please Check the system
				echo
			else
				echo The $APP_NAME Stoped
				echo
				rm $STAT_PID
		fi
	else
		echo
		echo $APP_NAME already Stoped!
		echo
	fi
}
status()
{
	if test -e $STAT_PID
	then
		TPID=`cat $STAT_PID`
		STATUS=`ps -p $TPID|grep java | awk '{print $1}'`
		if test $STATUS
			then
				echo "The $APP_NAME Running($TPID)!"
				echo
			else
				echo The $APP_NAME NOT Running!
				rm $STAT_PID
				echo
		fi
	else
		echo
		echo The $APP_NAME NOT Running!
		echo
	fi
}

case "$1" in
'start')
		start
	;;
'stop')
		stop
	;;
'restart')
		stop
		start
	;;
'status')
		status
	;;
*)
	echo
	echo
	echo "Usage: $0 {status | start | stop }"
	echo
	echo Status of $APP_NAME ......
	status
	;;
esac
exit 0
