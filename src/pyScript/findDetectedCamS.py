from pygrabber.dshow_graph import FilterGraph

def list_cameras():
    graph = FilterGraph()
    devices = graph.get_input_devices()
    return devices

if __name__ == "__main__":
    cameras = list_cameras()
    for camera in cameras:
        print(camera,flush=True)
