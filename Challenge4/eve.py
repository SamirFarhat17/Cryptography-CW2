import sys
import os
from common import *
from const import *

# Retrieve flag and set print object
dialog = Dialog('print')
flag = sys.argv[1]

# Create sockets to masquerade as bob and alice 
socket_alice, aes_alice = setup('alice', BUFFER_DIR, BUFFER_FILE_NAME)
os.rename(BUFFER_DIR+BUFFER_FILE_NAME, BUFFER_DIR+"BOB_BUFFER")
socket_bob, aes_bob = setup('bob', BUFFER_DIR, BUFFER_FILE_NAME)

# Pretend to be alice
received = receive_and_decrypt(aes_bob, socket_bob)
dialog.chat('Alice said: "{}"'.format(received))

# Flag alice's message
if flag == "--relay":
    to_send = received

if flag == "--custom":
    print("Input custom message please...")
    to_send = input()


if flag == "--break-heart":
    to_send = BAD_MSG['alice']
    
# Send message and declare as received
encrypt_and_send(to_send, aes_alice, socket_alice)
dialog.info('Message sent! Waiting for reply...')
received = receive_and_decrypt(aes_alice, socket_alice)
dialog.chat('Bob said: "{}"'.format(received))

# flag changes Bob's response
if flag == "--custom":
    print("Input custom message please...")
    to_send = input()
else:
    # Bob knows what to say for break heart and relay 
     to_send = received

# send response
encrypt_and_send(to_send, aes_bob, socket_bob)

#deactivate sockets
tear_down(socket_alice, BUFFER_DIR, "BOB_BUFFER")
tear_down(socket_bob, BUFFER_DIR, BUFFER_FILE_NAME)