import bcrypt

password = b'123456'

file_r = open("../rockyou-samples.bcrypt.txt")
line = file_r.readline()
hash_code = line.encode()
index_list = []
index = 1
found = 0

while found < 5:
    print(index)
    print(found)
    if(bcrypt.checkpw(password, hash_code[:-1])):
         found = found + 1
         index_list.append(index)
    line = file_r.readline()
    hash_code = line.encode()
    index = index+1

file_r.close()

file_w = open("bcrypt-lines.txt","w")

for x in index_list:
    file_w.write(str(x)+"\n")

file_w.close() 