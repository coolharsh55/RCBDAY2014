#!/usr/bin/python
# -*- coding: utf-8 -*-

import sqlite3
import sys

def readImage(imgName):
	try:
		fin = open(imgName,"rb")
		img = fin.read()
		binary = sqlite3.Binary(img)
		return binary
	except IOError as e:
		print(e.args[0])
		sys.exit(1)
	finally:
		if fin:
			fin.close()

con = sqlite3.connect('test.db')

with con:
	cur = con.cursor()
	filebasename = "10"
	for i in range(0,2):
		filename = filebasename + str(i) + ".jpg"
		img = readImage(filename);
		message = "Message" + str(i)
		cur.execute("INSERT INTO MESSAGES(MESSAGE,SENDER,IMAGE) VALUES(?,?,?)",(message,"Sender",img))
	con.commit()