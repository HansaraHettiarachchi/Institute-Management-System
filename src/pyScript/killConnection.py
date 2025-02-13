import os
import signal
import psutil

class ProcessManager:
    def __init__(self, port):
        self.port = port
    
    def find_process_using_port(self):
        for proc in psutil.process_iter(['pid', 'name']):
            try:
                connections = proc.net_connections()
                for conn in connections:
                    if conn.laddr.port == self.port:
                        return proc.info['pid']
            except (psutil.AccessDenied, psutil.NoSuchProcess):
                continue
        return None

    def terminate_process(self):
        pid = self.find_process_using_port()
        if pid:
            os.kill(pid, signal.SIGTERM)
            return (f"Terminated the process using port {self.port} (PID: {pid})")
        else:
            return ("No process found using port")

# if __name__ == "__main__":
#     port = 5555
#     manager = ProcessManager(port)
#     manager.terminate_process()
