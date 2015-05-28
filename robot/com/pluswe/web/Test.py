print 'hello world'
x =11
print x

tuple = ('abc',123,1.2)
print tuple
list = ['abc',123,1.2]
list.append("test")
print list
dict = {1:"abc","b":"string"}
print dict.get("b")

def show():
    for i in range(0,5,1):
        print i
    
    return "true"
#show();
hello = "world"
print hello[0:4]

f=file("d:\hello.txt",'w')
f.write("context");
f.close()

class FirstClass:
    def __init__(self):
        print "this init"
    def __del__(self):
        print "this del"
    def method(self):
        print "class method"
        
cl = FirstClass()
cl.method()

print "abc" 'aa'
        
        
        