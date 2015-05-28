def show():
    for i in range(0,5,1):
        print i
    
    return "true"
show();

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

        