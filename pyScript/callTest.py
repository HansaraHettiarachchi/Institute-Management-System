import zmq
import numpy as np
import cv2
import sys
import threading
from recognize1_1 import Recognize
from killConnection import ProcessManager

r = " "

while r != "No process found using port": 
    print(r)
    r = ProcessManager(5555).terminate_process()

context = zmq.Context()
socket = context.socket(zmq.PULL)
socket.bind(sys.argv[1])
print(f"Listening on port {sys.argv[1]}", flush=True)

recognizer = Recognize()

def call(frame):
    result = recognizer.setData(frame)
    print(result, flush=True)

running = True

while running:
    try:
        message = socket.recv()
        if message == b"shutdown":
            running = False
            print("Shutdown signal received. Exiting...", flush=True)
            break

        nparr = np.frombuffer(message, np.uint8)
        frame = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

        t = threading.Thread(target=call, args=(frame,))
        t.start()

    except zmq.ZMQError as e:
        print(f"ZMQ Error: {e}", flush=True)
        running = False

# Cleanly close the socket and context after exiting the loop
socket.close()
context.term()
print("Socket and context terminated cleanly.", flush=True)

from multiprocessing import Pool, cpu_count
