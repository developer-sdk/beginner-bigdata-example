#!/usr/bin/python
# -*- coding: utf-8 -*-
import re, json, sys, time

def readFile():
    with sys.stdin as lines:
        str_list = []

        for line in lines:
            # DATA 시작하면 출력
            if line.startswith("DATA") and len(str_list) != 0:
                print "\t".join(str_list)
                del str_list[:]
                str_list.append(line.strip())
            else:
                str_list.append(line.strip())

        # 마지막 데이터 출력 
        print "\t".join(str_list)

if __name__ == "__main__":
    readFile()