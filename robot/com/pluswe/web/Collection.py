# -*- coding: UTF-8 -*-
tuple = ('abc',123,1.2)
print tuple
list = ['abc',123,1.2]
list.append("test")
print list
dict = {1:"abc","b":"string"}
print dict.get("b")
print 'b' in dict
print max(list[1:])

i = 1
for key,value in dict.items():
    print key,value
    i +=1
print i
