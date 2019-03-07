#!/usr/bin/env python
# -*- coding: utf-8 -*-
import urllib, json, urllib2, datetime
from urllib2 import HTTPError

def request_get(request_url):
    return request(request_url, "GET", "", {'Content-Type': 'application/json'})

def request(request_url, request_type="GET", data="", header={}):

    opener = urllib2.build_opener(urllib2.HTTPHandler)
    request_get = urllib2.Request(request_url, data, header)
    request_get.get_method = lambda: request_type

    response = opener.open(request_get)

    response_info = response.info()
    response_body = response.read()
    json_obj = json.loads(response_body)

    print(json.dumps(json_obj, sort_keys=True, indent=4, separators=(',', ': ')))

def main():
    rma_url = "http://<RMA주소>:<RMA포트>/ws/v1/cluster/metrics"
    request_get(rma_url)

if __name__ == '__main__':
    main()