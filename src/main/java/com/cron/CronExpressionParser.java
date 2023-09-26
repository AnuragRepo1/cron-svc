package com.cron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CronExpressionParser {
    ConcurrentHashMap<String, Integer>  mymap = new ConcurrentHashMap<String,  Integer>();
    public static Map<String, Integer>monthMapping = new HashMap<>();

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CronParser \"*/15 0 1,15 * 1-5 /usr/bin/find\"");
            return;
        }
        evaluateCronExpression(args[0]);
    }


    private static List<String> retrieveCronFieldValue(String field, int minValue, int maxValue) {
        List<String> values = new ArrayList<>();
        if (field.equals("*")) {
            for (int i = minValue; i <= maxValue; i++) {
                values.add(Integer.toString(i));
            }
        } else if (field.contains("/")) {
            String[] parts = field.split("/");
            int interval = Integer.parseInt(parts[1]);
            for (int i = minValue; i <= maxValue; i += interval) {
                values.add(Integer.toString(i));
            }
        } else if (field.contains(",")) {
            String[] parts = field.split(",");
            for (String part : parts) {
                if (part.contains("-")) {
                    String[] range = part.split("-");
                    int start = -1;
                    int end =-1;
                    if(range[0].equals("jan")) {
                        start = monthMapping.get("jan");

                    }else if(range[1].equals("march")) {
                        end = monthMapping.get("jan");
                    }else {
                        start =Integer.parseInt(range[0]);
                        end = Integer.parseInt(range[1]);
                    }
                    for (int i = start; i <= end; i++) {
                        values.add(Integer.toString(i));
                    }
                } else {
                    values.add(part);
                }
            }
        } else if (field.contains("-")) {
            String[] range = field.split("-");
            int start = -1;
            int end =-1;
            if(range[0].equals("jan")) {
                start = monthMapping.get("jan");

            }else if(range[1].equals("march")) {
               end = monthMapping.get("jan");
            }else {
                start =Integer.parseInt(range[0]);
                end = Integer.parseInt(range[1]);
            }
            for (int i = start; i <= end; i++) {
                values.add(Integer.toString(i));
            }
        } else {
            values.add(field);
        }
        return values;
    }

    public static Map<String, List<String>> evaluateCronExpression(String args) throws Exception {
        monthMapping.put("jan", 1);
        monthMapping.put("march", 3);
        if (args == null || args.isEmpty()) {
            System.out.println("Usage: java CronParser \"*/15 0 1,15 * 1-5 /usr/bin/find\"");
            throw new Exception("argument not present in input");
        }
        Map<String, List<String>> parsedValue = new HashMap<>();
        String cronExpression = args;
        String[] fields = cronExpression.split(" ");
        List<String> minutes = retrieveCronFieldValue(fields[0], 0, 59);
        List<String> hours = retrieveCronFieldValue(fields[1], 0, 23);
        List<String> daysOfMonth = retrieveCronFieldValue(fields[2], 1, 31);
        List<String> months = retrieveCronFieldValue(fields[3], 1, 12);
        List<String> daysOfWeek = retrieveCronFieldValue(fields[4], 0, 6);
        List<String>commands = new ArrayList<>();
        commands.add(fields[5]);
        for(int i = 6; i<fields.length; i++) {
            commands.add(fields[i]);
        }
        parsedValue.put("minute", minutes);
        parsedValue.put("hour", hours);
        parsedValue.put("dayofmonth", daysOfMonth);
        parsedValue.put("month", months);
        parsedValue.put("dayofweek", daysOfWeek);
        parsedValue.put("command", commands) ;

        System.out.println("minute " + String.join(" ", minutes));
        System.out.println("hour " + String.join(" ", hours));
        System.out.println("day of month " + String.join(" ", daysOfMonth));
        System.out.println("month " + String.join(" ", months));
        System.out.println("day of week " + String.join(" ", daysOfWeek));
        System.out.print("command " + fields[5]+" ");
         System.out.println();
        return parsedValue;
    }
}
