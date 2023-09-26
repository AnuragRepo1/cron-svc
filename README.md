cron-svc
This command line utility decodes a cron expression and reveals the schedule for its execution by expanding each component field.

We exclusively examine the classic cron configuration, encompassing five temporal parameters (minute, hour, day of month, month, and day of the week) along with a command. The input should be confined to a single line, with the cron string provided as a solitary argument to the application

~$ your-program "*/15 0 1,15 * 1-5 /usr/bin/find"

Instructions to run the program
$ java com.cron.CronExpressionParser "*/15 0 1,15 * 1-5 /usr/bin/find"

For example, the following input argument:

java com.cron.CronExpressionParser "*/15 0 1,15 * 1-5 /usr/bin/find"

Yields the following output:

minute 0 15 30 45
hour 0
day of month 1 15
month 1 2 3 4 5 6 7 8 9 10 11 12
day of week 1 2 3 4 5
command /usr/bin/find

Tests
The tests are present in the test/ directory. They do cover basic cases of Parsers and over tests of an expression to the required output.